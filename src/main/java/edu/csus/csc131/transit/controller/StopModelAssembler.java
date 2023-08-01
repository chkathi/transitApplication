package edu.csus.csc131.transit.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.transit.data.Stop;

/* Generates the hyperlinks needed for the GET requests by calling the 
   StopController Class
*/

@Component
public class StopModelAssembler implements RepresentationModelAssembler<Stop, EntityModel<Stop>> {

  @Override
  public EntityModel<Stop> toModel(Stop stop) {
    return EntityModel.of(stop,
        linkTo(methodOn(StopController.class).getStop(stop.getId())).withSelfRel(),
        linkTo(methodOn(StopController.class).getAllStops()).withRel("stops"));
  }
}