package edu.csus.csc131.transit.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import edu.csus.csc131.transit.model.TripPlan;
import edu.csus.csc131.transit.model.TripStep;

import edu.csus.csc131.transit.data.StopTime;
import edu.csus.csc131.transit.data.Route;
import edu.csus.csc131.transit.data.Trip;
import edu.csus.csc131.transit.data.Transfer;
import edu.csus.csc131.transit.repository.RouteRepository;
import edu.csus.csc131.transit.repository.StopTimeRepository;
import edu.csus.csc131.transit.repository.TripRepository;
import edu.csus.csc131.transit.repository.TransferRepository;

@Service
public class TripPlanServiceImpl implements TripPlanService {
  @Autowired
  private StopTimeRepository stopTimeRepo;

  @Autowired
  private TransferRepository transferRepo;

  @Autowired
  private RouteRepository routeRepo;

  @Autowired
  private TripRepository tripRepo;

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  // TODO: return a plan with the earliest arrival time
  public TripPlan getPlan(String fromStopId, String toStopId, ZonedDateTime departTime) {
    TripPlan tripPlan = new TripPlan();
    tripPlan.setFromStopId(fromStopId);
    tripPlan.setToStopId(toStopId);
    tripPlan.setDepartTime(departTime);

    ArrayList<TripStep> tripSteps = new ArrayList<TripStep>();
    TripStep tripStep = new TripStep();

    // Finding all stopTimes using fromStopId
    List<StopTime> stopTimes = stopTimeRepo.findByStopId(fromStopId);
    int stopSequence = -1;
    String realTripId = "";
    tripStep.setFromStopId(fromStopId);

    // Looping through all the stopTimes to get the right departureTime
    // and right type of day (Weekday, Sat, Sun)
    for (int i = 0; i < stopTimes.size(); i++) {
      StopTime stopTime = stopTimes.get(i);

      String departTimeString = departTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
      LocalTime departSchedule = LocalTime.parse(departTimeString);
      LocalTime stopDeparture = LocalTime.parse(stopTime.getDepartureTime());

      // Checking to see if stop is after or at the provided departTime
      if (stopDeparture.equals(departSchedule) || stopDeparture.isAfter(departSchedule)) {
        // Found first time that is after the departTime provided
        String tripId = stopTime.getTripId();

        Optional<Trip> tripO = tripRepo.findById(tripId);
        if (tripO.isPresent()) {
          Trip trip = tripO.get();

          // Checking if the day is a weekday, sat, Sun
          DayOfWeek dayOfWeek = departTime.getDayOfWeek();
          String typeOfDay = "Sunday";

          if (!(dayOfWeek == DayOfWeek.SATURDAY) && !(dayOfWeek == DayOfWeek.SUNDAY)) {
            typeOfDay = "Weekday";
          } else if (dayOfWeek == DayOfWeek.SATURDAY) {
            typeOfDay = "Saturday";
          }

          if (trip.getServiceId().contains(typeOfDay)) {
            // Found Trip based on WeekDay, Saturday, Sunday timing
            LocalDate date = departTime.toLocalDate();
            ZonedDateTime newDepart = date.atTime(stopDeparture).atZone(departTime.getZone());

            // log.info("CK ____ DepartTime Found: {}", newDepart.toString());

            tripStep.setDepartTime(newDepart);

            // log.info("CK ____ Contains {} TypeOfDay", typeOfDay);

            // Route Info
            realTripId = tripId;
            String routeId = trip.getRouteId();
            tripStep.setRouteId(routeId);

            Optional<Route> routeO = routeRepo.findById(routeId);
            if (routeO.isPresent()) { // Get Route ShortName
              Route route = routeO.get();
              tripStep.setRouteName(route.getShortName());
            } else {
              log.info("CK ____ TripPlanServiceImpl- RouteId not found");
            }

            stopSequence = stopTime.getStopSequence();
            break;

          } else {
            continue;
          }
        } else {
          // If TripId is not found then send error to terminal
          log.info("CK ____ TripPlanServiceImpl- TripId not found");
        }
      }
    }

    // Using the verified tripId and StopSequence we loop throught the
    // Stoptimes to find the stop we need to stop at
    stopTimes = stopTimeRepo.findByTripId(realTripId);
    for (int i = stopSequence; i < stopTimes.size(); i++) {
      StopTime stopTime = stopTimes.get(i);
      String stopId = stopTime.getStopId();

      // Found the final stop and set the arrival time at the stop
      if (stopId.equals(toStopId)) {
        tripStep.setToStopId(stopTime.getStopId());

        LocalDate date = departTime.toLocalDate();
        LocalTime desiredTime = LocalTime.parse(stopTime.getArrivalTime());
        ZonedDateTime newZonedDateTime = date.atTime(desiredTime).atZone(departTime.getZone());

        tripStep.setArrivalTime(newZonedDateTime);
      }
    }

    log.info("TripPlan is Created (from {} and to {})", fromStopId, toStopId);

    log.info("Departure time is {} and arrival time is {}", tripStep.getDepartTime(), tripStep.getArrivalTime());

    // Add TripStep to the TripSteps
    tripSteps.add(tripStep);
    tripPlan.setTripSteps(tripSteps);
    return tripPlan;
  }
}
