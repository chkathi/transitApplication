package edu.csus.csc131.transit.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.Transfer;

public interface TransferRepository extends MongoRepository<Transfer, String> {
	List<Transfer> findByFromStopIdAndToStopId(String fromStopId, String toStopId);
}