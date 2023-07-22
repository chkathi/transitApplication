package edu.csus.csc131.transit.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.Trip;

public interface TripRepository extends MongoRepository<Trip, String> {
	List<Trip> findByRouteId(String routeId);
}
