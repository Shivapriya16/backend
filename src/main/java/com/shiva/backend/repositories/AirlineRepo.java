package com.shiva.backend.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.shiva.backend.models.AirLine;

public interface AirlineRepo extends ReactiveMongoRepository<AirLine, String> {

}
