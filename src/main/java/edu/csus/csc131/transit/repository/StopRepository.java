package edu.csus.csc131.transit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.Stop;

public interface StopRepository extends MongoRepository<Stop, String> {
    //Nothing needed
}