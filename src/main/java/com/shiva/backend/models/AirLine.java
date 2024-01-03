package com.shiva.backend.models;

import java.util.Date;
import java.util.List;

import com.shiva.backend.payloads.AirlineReq;

public class AirLine {
    public String name;
    public Integer flightNo;
    public List<Integer> seatNo;
    public Integer totalSeats;
    public Date date;
    public FlightClass flightClass;

    public AirLine() {

    }

    public AirLine(AirlineReq req) {
        if (req.number == null) {
            throw new RuntimeException("flight number is invalid");
        }
        this.flightNo = req.number;
        this.name = req.name;
        this.totalSeats = req.totalSeats;
        this.date = req.date;
        this.flightClass = FlightClass.valueOf(req.flightClass);
    }
}
