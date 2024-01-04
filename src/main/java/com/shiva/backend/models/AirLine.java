package com.shiva.backend.models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shiva.backend.payloads.AirlineReq;

@Document(collection = "airLine")
public class AirLine {
    public String name;
    @Id
    public String id;
    public List<Integer> seatNo;
    public Integer totalSeats;
    public Date date;
    public Map<String, SeatCount> flightClass;

    public AirLine() {

    }

    public AirLine(AirlineReq req) {
        if (req.id == null) {
            throw new RuntimeException("flight number is invalid");
        }
        this.id = req.id;
        this.name = req.name;
        this.totalSeats = req.totalSeats;
        this.date = req.date;
        this.flightClass = new HashMap<>();
        SeatCount seats;
        if (req.firstClassSeat != 0) {
            seats = new SeatCount(req.firstClassSeat, 1);// (total firstclass seat, starting seat)
            this.flightClass.put("firstClassSeat", seats);
        }
        if (req.economyClassSeat != 0) {
            seats = new SeatCount(req.economyClassSeat, (req.firstClassSeat + 1));
            this.flightClass.put("economyClassSeat", seats);
        }

    }
}
