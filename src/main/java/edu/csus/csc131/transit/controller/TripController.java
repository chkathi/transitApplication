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
import edu.csus.csc131.transit.data.Trip;
import edu.csus.csc131.transit.repository.TripRepository;

@RestController
@RequestMapping(value = "/trips")
public class TripController {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private TripRepository TripRepository;
  private TripModelAssembler TripAssembler;

  public TripController(TripRepository TripRepository, TripModelAssembler TripModelAssembler) {
    this.TripRepository = TripRepository;
    this.TripAssembler = TripModelAssembler;
  }

  @GetMapping
  public CollectionModel<EntityModel<Trip>> getAllTrips(
      @RequestParam(required = false) String tripId, @RequestParam(required = false) String routeId) {
    log.info("Getting all trips.");

    List<Trip> Trips = null;
    if ((tripId == null || tripId.isBlank()) && (routeId == null || routeId.isBlank())) {
      Trips = TripRepository.findAll(); // If no tripId & routeId specificed, find all
    } else {
      Trips = TripRepository.findByRouteId(routeId); // If no tripId, find via routeId
    }
    log.info("Returning {} trips.", Trips.size());

    // Java Aggregate Operations Tutorial:
    // https://docs.oracle.com/javase/tutorial/collections/streams/index.html
    List<EntityModel<Trip>> TripList = Trips.stream() //
        .map(TripAssembler::toModel) //
        .toList();
    return CollectionModel.of(TripList,
        linkTo(methodOn(TripController.class).getAllTrips(tripId, routeId)).withSelfRel());
  }

  @GetMapping(value = "/{id}")
  public EntityModel<Trip> getTrip(@PathVariable String id) {
    log.info("Getting a trip by Id: {}.", id);
    Trip Trip = TripRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Trip", id));
    return TripAssembler.toModel(Trip);
  }

}
