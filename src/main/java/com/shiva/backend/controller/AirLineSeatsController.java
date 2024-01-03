package com.shiva.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiva.backend.payloads.AirlineReq;
import com.shiva.backend.repositories.AirlineRepo;
import com.shiva.backend.service.AirlineService;

@RestController
@RequestMapping(produces = "application/json")
public class AirLineSeatsController {

    @Autowired
    AirlineRepo airlineRepo;

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

    // select seat by seatNumber
    // Book Random seats
}
