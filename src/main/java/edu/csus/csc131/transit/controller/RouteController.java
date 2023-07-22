package edu.csus.csc131.transit.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.csus.csc131.transit.controller.common.ResourceNotFoundException;
import edu.csus.csc131.transit.data.Route;
import edu.csus.csc131.transit.repository.RouteRepository;

@RestController
@RequestMapping(value = "/routes")
public class RouteController {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private RouteRepository routeRepository;
  private RouteModelAssembler routeAssembler;

  public RouteController(RouteRepository routeRepository, RouteModelAssembler routeModelAssembler) {
    this.routeRepository = routeRepository;
    this.routeAssembler = routeModelAssembler;
  }

  @GetMapping
  public CollectionModel<EntityModel<Route>> getAllRoutes(
      @RequestParam(required = false) String shortName, @RequestParam(required = false) String longName) {
    log.info("Getting all routes.");

    List<Route> routes = null;
    if ((shortName == null || shortName.isBlank()) && (longName == null || longName.isBlank())) {
      routes = routeRepository.findAll();
    }

    log.info("Returning {} routes.", routes.size());

    // Java Aggregate Operations Tutorial:
    // https://docs.oracle.com/javase/tutorial/collections/streams/index.html
    List<EntityModel<Route>> routeList = routes.stream() //
        .map(routeAssembler::toModel) //
        .toList();
    return CollectionModel.of(routeList,
        linkTo(methodOn(RouteController.class).getAllRoutes(shortName, longName)).withSelfRel());
  }

  @GetMapping(value = "/{id}")
  public EntityModel<Route> getRoute(@PathVariable String id) {
    log.info("Getting a route by Id: {}.", id);
    Route route = routeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Route", id));
    return routeAssembler.toModel(route);
  }

}
