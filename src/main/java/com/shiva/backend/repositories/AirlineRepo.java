package com.shiva.backend.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.shiva.backend.models.AirLine;

@Repository
public interface AirlineRepo extends ReactiveMongoRepository<AirLine, String> {

}
