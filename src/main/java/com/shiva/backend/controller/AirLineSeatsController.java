package com.shiva.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiva.backend.models.AirLine;
import com.shiva.backend.payloads.AirlineReq;
import com.shiva.backend.repositories.AirlineRepo;
import com.shiva.backend.service.AirlineService;

@RestController
@RequestMapping(produces = "application/json")
public class AirLineSeatsController {

    @Autowired
    AirlineRepo airlineRepo;

    @Autowired
    ReactiveMongoTemplate mongoTemplate;

    // Initially Collect details of flights
    @PostMapping("/flightDetails")
    public ResponseEntity<?> collectDetails(@RequestBody AirlineReq request) {
        try {
            AirlineService service = new AirlineService(airlineRepo);
            service.SaveInitialData(request);
            return new ResponseEntity<String>(("success"), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
        }
    }

    // send Empty seats
    // get class enum
    @GetMapping("empty_seats/{id}")
    public ResponseEntity<?> sendEmptySeats(@PathVariable(name = "id") String id,
            @RequestParam(required = true, name = "flightClass") String flightClass) {
        try {

            AirLine response = airlineRepo.findById(id).blockOptional().orElse(null);

            if (response != null && response.flightClass != null
                    && response.flightClass.get(flightClass) != null) {
                return new ResponseEntity<Object>((response.flightClass.get(flightClass)), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(("Invalid data"), HttpStatus.OK);
            }

        } catch (Exception ex) {
            return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
        }
    }

    // select seat by seatNumber
    // Book Random seats
}
