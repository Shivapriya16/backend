package com.shiva.backend.service;

import org.springframework.stereotype.Service;

import com.shiva.backend.models.AirLine;
import com.shiva.backend.payloads.AirlineReq;
import com.shiva.backend.repositories.AirlineRepo;

@Service
public class AirlineService {

    AirlineRepo airlineRepo;

    public AirlineService(AirlineRepo airlineRepo) {
        this.airlineRepo = airlineRepo;
    }

    public void SaveInitialData(AirlineReq req) {
        new AirLine(req);
    }

}
