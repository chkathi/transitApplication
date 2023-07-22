package edu.csus.csc131.transit.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.Stop;

public interface StopRepository extends MongoRepository<Stop, String> {
    //Nothing needed
}