package edu.csus.csc131.transit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.Route;

public interface RouteRepository extends MongoRepository<Route, String> {
  // Nothing need
}