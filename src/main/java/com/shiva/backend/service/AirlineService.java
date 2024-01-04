package com.shiva.backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shiva.backend.models.AirLine;
import com.shiva.backend.models.SeatCount;
import com.shiva.backend.payloads.AirlineReq;
import com.shiva.backend.repositories.AirlineRepo;

@Service
public class AirlineService {

    AirlineRepo airlineRepo;

    ReactiveMongoTemplate mongoTemplate;

    public AirlineService() {

    }

    public AirlineService(AirlineRepo airlineRepo) {
        this.airlineRepo = airlineRepo;
    }

    public AirlineService(AirlineRepo airlineRepo, ReactiveMongoTemplate mongoTemplate) {
        this.airlineRepo = airlineRepo;
        this.mongoTemplate = mongoTemplate;
    }

    public void SaveInitialData(AirlineReq req) {
        airlineRepo.save(new AirLine(req)).subscribe();
    }

    public void selectSeats(String id, String flightClass, List<Integer> seatNumbers) {
        AirLine response = airlineRepo.findById(id).blockOptional().orElse(null);
        if (response != null && response.flightClass != null && response.flightClass.get(flightClass) != null) {
            // get the class block
            SeatCount seatCount = response.flightClass.get(flightClass);
            // check seats available
            if (!checkSeatsAvailability(seatNumbers, seatCount)) {
                bookSeats(id, seatNumbers, response, seatCount);
            } else {
                throw new RuntimeException("Some seat was already booked, try again");
            }
        } else {
            throw new RuntimeException("Invalid id");
        }
    }

    private void bookSeats(String id, List<Integer> seatNumbers, AirLine response, SeatCount seatCount) {
        seatCount.bookedSeats.addAll(seatNumbers);
        Collections.sort(seatCount.bookedSeats);
        seatCount.emptySeats.removeAll(seatNumbers);
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("flightClass", response.flightClass);
        mongoTemplate.findAndModify(query, update, AirLine.class).subscribe();
    }

    private Boolean checkSeatsAvailability(List<Integer> seatNumbers, SeatCount seatCount) {
        return seatCount.bookedSeats.stream().anyMatch(each -> seatNumbers.contains(each));
    }

    public void selectSeatsRandomly(String id, String flightClass, Integer noOfSeats) {
        AirLine response = airlineRepo.findById(id).blockOptional().orElse(null);
        if (response != null && response.flightClass != null && response.flightClass.get(flightClass) != null) {
            // get the class block
            SeatCount seatCount = response.flightClass.get(flightClass);
            // check number of seats available

            if (seatCount.emptySeats != null && seatCount.emptySeats.size() >= noOfSeats) {
                List<Integer> seatNumbers = new ArrayList<>();
                for (Integer i = 0; i < noOfSeats; i++) {
                    seatNumbers.add(seatCount.emptySeats.get(i));
                }
                bookSeats(id, seatNumbers, response, seatCount);
            } else {
                throw new RuntimeException("Some seat was already booked, try again");
            }
        } else {
            throw new RuntimeException("Invalid id");
        }
    }

    public void deleteSeats(String id, String flightClass, List<Integer> seatNumbers) {
        AirLine response = airlineRepo.findById(id).blockOptional().orElse(null);
        if (response != null && response.flightClass != null && response.flightClass.get(flightClass) != null) {
            // get the class block
            SeatCount seatCount = response.flightClass.get(flightClass);
            // check the seat numbers are booked
            if (!seatCount.emptySeats.stream().anyMatch(each -> seatNumbers.contains(each))) {
                deleteSeats(id, seatNumbers, response, seatCount);
            } else {
                throw new RuntimeException("Invalid seat number");
            }
        } else {
            throw new RuntimeException("Invalid id");
        }
    }

    private void deleteSeats(String id, List<Integer> seatNumbers, AirLine response, SeatCount seatCount) {
        seatCount.bookedSeats.removeAll(seatNumbers);
        Collections.sort(seatCount.bookedSeats);
        seatCount.emptySeats.addAll(seatNumbers);
        Collections.sort(seatCount.emptySeats);
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("flightClass", response.flightClass);
        mongoTemplate.findAndModify(query, update, AirLine.class).subscribe();
    }

}
