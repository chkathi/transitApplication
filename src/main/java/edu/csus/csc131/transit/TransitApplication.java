package edu.csus.csc131.transit;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.csus.csc131.transit.data.StopTime;
import edu.csus.csc131.transit.data.Route;
import edu.csus.csc131.transit.data.Stop;
import edu.csus.csc131.transit.data.Trip;
import edu.csus.csc131.transit.data.Transfer;
import edu.csus.csc131.transit.repository.RouteRepository;
import edu.csus.csc131.transit.repository.StopTimeRepository;
import edu.csus.csc131.transit.repository.TripRepository;
import edu.csus.csc131.transit.repository.TransferRepository;
import edu.csus.csc131.transit.repository.StopRepository;
@SpringBootApplication
public class TransitApplication implements CommandLineRunner {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Value("${dataRoot}")
  private String dataDir;

  @Autowired
  private StopTimeRepository stopTimeRepo;

  @Autowired
  private TransferRepository transferRepo;

  @Autowired
  private RouteRepository routeRepo;

  @Autowired
  private TripRepository tripRepo;

  @Autowired
  private StopRepository stopRepo;

  public static void main(String[] args) {
    SpringApplication.run(TransitApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    createStops();
    createRoutes();
    createTransfers();
    createTrips();
    createStopTimes();
  }

  // ToDo: read stops from "stops.txt" and save them to the database
  private void createStops() {
    stopRepo.deleteAll();
    log.info("Start creating stops");

    // Get Stop File
    Path path = FileSystems.getDefault().getPath(dataDir, "stops.txt");

    int count = 0;

    // Fetch the data from the file using BufferedReader
    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      String line = reader.readLine(); // skip the first line
    
      while ((line = reader.readLine()) != null) {
        // Read and split into array
        String[] tokens = line.split(",");
        String temp = "";

        Stop stop = new Stop();
        temp = tokens[0].substring(1, tokens[0].length() - 1);
        stop.setId(temp.trim());
        temp = tokens[1].substring(1, tokens[1].length()-1);
        stop.setName(temp.trim());
        temp = tokens[4].substring(1, tokens[4].length()-1);
        stop.setLat(Double.parseDouble(temp.trim()));
        temp = tokens[5].substring(1, tokens[5].length()-1);
        stop.setLon(Double.parseDouble(temp.trim()));

        // Save to database
        stop = stopRepo.save(stop);
        count++;
      }
    
    } catch (IOException e) {
      log.error("IOException: " + e.getMessage(), e);
    }

    log.info("Finished creating {} stops", count);
    
  }


  // ToDo: read routes from "routes.txt" and save them to the database
  private void createRoutes() {
    routeRepo.deleteAll();
    log.info("Start creating routes");

    // Get Path File
    Path path = FileSystems.getDefault().getPath(dataDir, "routes.txt");

    int count = 0;

    // Fetch the data from the file using BufferedReader
    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      String line = reader.readLine(); // skip the first line

      while ((line = reader.readLine()) != null) {
        // Read and split into array
        String[] tokens = line.split(",");

        Route route = new Route();
        route.setId(tokens[0].trim());
        route.setShortName(tokens[1].trim());
        route.setLongName(tokens[2].trim());

        // Save to database
        route = routeRepo.save(route);
        count++;
      }

    } catch (IOException e) {
      log.error("IOException: " + e.getMessage(), e);
    }

    log.info("Finished creating {} routes", count);
  }

  private void createTrips() {
    tripRepo.deleteAll();
    log.info("Start creating trips");
    Path path = FileSystems.getDefault().getPath(dataDir, "trips.txt");
    int count = 0;
    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      String line = reader.readLine(); // skip the first line
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split(",");

        Trip trip = new Trip();
        trip.setRouteId(tokens[0].trim());
        trip.setServiceId(tokens[1].trim());
        trip.setId(tokens[2].trim());

        int directionId = Integer.parseInt(tokens[4].trim());
        trip.setDirectionId(directionId);

        trip = tripRepo.save(trip);
        count++;
      }
    } catch (IOException x) {
      log.error("IOException: " + x.getMessage(), x);
    }
    log.info("Finished creating {} trips", count);
  }

  private void createStopTimes() {
    stopTimeRepo.deleteAll();
    log.info("Start creating stopTimes");
    Path path = FileSystems.getDefault().getPath(dataDir, "stop_times.txt");
    int count = 0;
    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      String line = reader.readLine(); // skip the first line
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split(",");
        int lastIndex = tokens.length - 1;

        StopTime stopTime = new StopTime();
        stopTime.setTripId(tokens[0].trim());
        stopTime.setArrivalTime(tokens[1].trim());
        stopTime.setDepartureTime(tokens[2].trim());
        stopTime.setStopId(tokens[3].trim());
        stopTime.setDistTraveled(Double.parseDouble(tokens[lastIndex]));

        stopTime = stopTimeRepo.save(stopTime);
        count++;
      }
    } catch (IOException x) {
      log.error("IOException: " + x.getMessage(), x);
    }
    log.info("Finished createing {} stopTimes", count);
  }

// ToDo: read transfers from "transfers.txt" and save them to the database
private void createTransfers() {
  transferRepo.deleteAll();
  log.info("Start creating transfers");
  Path path = FileSystems.getDefault().getPath(dataDir, "transfers.txt");
  int count = 0;
  try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
    String line = reader.readLine(); // skip the first line
    while ((line = reader.readLine()) != null) {
      String[] tokens = line.split(",");

      Transfer transfer = new Transfer();
      if (tokens.length > 4) {
        transfer.setToRouteId(tokens[5].trim());
        transfer.setFromRouteId(tokens[4].trim());
      } else {
        transfer.setFromRouteId("");
        transfer.setFromRouteId("");
      }

      transfer.setToStopId(tokens[1].trim());
      transfer.setFromStopId(tokens[0].trim());
      transfer.setMinTransferTime(Integer.parseInt(tokens[3].trim()));

      transfer = transferRepo.save(transfer);
      count++;
    }
  } catch (IOException x) {
    log.error("IOException: " + x.getMessage(), x);
  }
  log.info("Finished creating {} transfers", count);
}

}
