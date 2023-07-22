package edu.csus.csc131.transit.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.Route;

public interface RouteRepository extends MongoRepository<Route, String> {
  List<Route> findByShortName(String shortName);

  List<Route> findByLongName(String longName);

}