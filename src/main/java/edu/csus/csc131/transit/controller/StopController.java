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
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.csus.csc131.transit.controller.common.ResourceNotFoundException;
import edu.csus.csc131.transit.data.Stop;
import edu.csus.csc131.transit.repository.StopRepository;

@RestController
@RequestMapping(value = "/stops")
public class StopController {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private StopRepository stopRepository;
  private StopModelAssembler stopAssembler;

  public StopController(StopRepository stopRepository, StopModelAssembler stopModelAssembler) {
    this.stopRepository = stopRepository;
    this.stopAssembler = stopModelAssembler;
  }

  @GetMapping
  // No paramenters need to be passed because we are only searching all or by id
  public CollectionModel<EntityModel<Stop>> getAllStops() {
    // Logs to the terminal
    log.info("Getting all stops.");

    List<Stop> stops = null;
    /*
     * Since we are not looking for stops based on any other attribute other
     * than their id, we can just say findAll
     */
    stops = stopRepository.findAll();

    log.info("Returning {} stops.", stops.size());

    // After retriving all stops, a collection is sent out via the hyperlink
    // Java Aggregate Operations Tutorial:
    // https://docs.oracle.com/javase/tutorial/collections/streams/index.html
    List<EntityModel<Stop>> stopList = stops.stream() //
        .map(stopAssembler::toModel) //
        .toList();
    return CollectionModel.of(stopList,
        linkTo(methodOn(StopController.class).getAllStops()).withSelfRel());
  }

  // retrieves and sends the collection of stops
  @GetMapping(value = "/{id}")
  public EntityModel<Stop> getStop(@PathVariable String id) {
    log.info("Getting a stop by Id: {}.", id);
    Stop stop = stopRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Stop", id));
    return stopAssembler.toModel(stop);
  }

}