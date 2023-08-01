package edu.csus.csc131.transit.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.transit.data.Transfer;

@Component
public class TransferModelAssembler implements RepresentationModelAssembler<Transfer, EntityModel<Transfer>> {

  @Override
  public EntityModel<Transfer> toModel(Transfer Transfer) {
    return EntityModel.of(Transfer, //
        linkTo(methodOn(TransferController.class).getTransfer(Transfer.getId())).withSelfRel(),
        linkTo(methodOn(TransferController.class).getAllTransfers(null, null)).withRel("transfer"));
  }

}
