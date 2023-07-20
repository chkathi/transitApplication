package edu.csus.csc131.transit.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import edu.csus.csc131.transit.model.TripPlan;
import edu.csus.csc131.transit.model.TripStep;

@Service
public class TripPlanServiceImpl implements TripPlanService {

	@Override
	// TODO: return a plan with the earliest arrival time
	public TripPlan getPlan(String fromStopId, String toStopId, ZonedDateTime departTime) {
		TripPlan tripPlan = new TripPlan();
		tripPlan.setFromStopId(fromStopId);
		tripPlan.setToStopId(toStopId);
		tripPlan.setDepartTime(departTime);
		tripPlan.setTripSteps(new ArrayList<TripStep>());
		return tripPlan;
	}
	

}
