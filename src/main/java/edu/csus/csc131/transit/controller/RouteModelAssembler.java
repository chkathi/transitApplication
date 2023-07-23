package edu.csus.csc131.transit.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.transit.data.Route;

/* Generates the hyperlinks needed for the GET requests by calling the 
   RouteController Class
*/

@Component
public class RouteModelAssembler implements RepresentationModelAssembler<Route, EntityModel<Route>> {

  @Override
  public EntityModel<Route> toModel(Route route) {
    return EntityModel.of(route,
        linkTo(methodOn(RouteController.class).getRoute(route.getId())).withSelfRel(),
        linkTo(methodOn(RouteController.class).getAllRoutes()).withRel("routes"));
  }
}
