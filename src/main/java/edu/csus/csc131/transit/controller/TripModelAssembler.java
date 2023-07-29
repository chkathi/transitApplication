package edu.csus.csc131.transit.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.transit.data.Trip;

@Component
public class TripModelAssembler implements RepresentationModelAssembler<Trip, EntityModel<Trip>> {

  @Override
  public EntityModel<Trip> toModel(Trip Trip) {
    return EntityModel.of(Trip, //
        linkTo(methodOn(TripController.class).getTrip(Trip.getId())).withSelfRel(),
        linkTo(methodOn(TripController.class).getAllTrips(null, null)).withRel("trips"));
  }

}