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
import edu.csus.csc131.transit.data.Transfer;
import edu.csus.csc131.transit.repository.TransferRepository;

@RestController
@RequestMapping(value = "/transfers")
public class TransferController {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private TransferRepository transferRepository;
  private TransferModelAssembler transferAssembler;

  public TransferController(TransferRepository transferRepository, TransferModelAssembler transferModelAssembler) {
    this.transferRepository = transferRepository;
    this.transferAssembler = transferModelAssembler;
  }

  @GetMapping
  public CollectionModel<EntityModel<Transfer>> getAllTransfers(
      @RequestParam(required = false) String fromStopId, @RequestParam(required = false) String toStopId) {
    log.info("Getting all transfers.");

    List<Transfer> transfers = null;
    if ((fromStopId == null || fromStopId.isBlank()) && (toStopId == null || toStopId.isBlank())) {
      transfers = transferRepository.findAll();
    } else {
      transfers = transferRepository.findByFromStopIdAndToStopId(fromStopId, toStopId);
    }
    log.info("Returning {} transfers.", transfers.size());

    // Java Aggregate Operations Tutorial:
    // https://docs.oracle.com/javase/tutorial/collections/streams/index.html
    List<EntityModel<Transfer>> transferList = transfers.stream() //
        .map(transferAssembler::toModel) //
        .toList();
    return CollectionModel.of(transferList,
        linkTo(methodOn(TransferController.class).getAllTransfers(fromStopId, toStopId)).withSelfRel());
  }

  @GetMapping(value = "/{id}")
  public EntityModel<Transfer> getTransfer(@PathVariable String id) {
    log.info("Getting a transfer by Id: {}.", id);

    Transfer transfer = transferRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("transfer", id));
    return transferAssembler.toModel(transfer);
  }

}
