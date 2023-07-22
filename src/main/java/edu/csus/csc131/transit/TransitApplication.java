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
import edu.csus.csc131.transit.repository.RouteRepository;
import edu.csus.csc131.transit.repository.StopTimeRepository;

@SpringBootApplication
public class TransitApplication implements CommandLineRunner {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Value("${dataRoot}")
  private String dataDir;

  @Autowired
  private StopTimeRepository stopTimeRepo;

  @Autowired
  private RouteRepository routeRepo;

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

  // ToDo: read transfers from "transfers.txt" and save them to the database
  private void createTransfers() {

  }

  // ToDo: read trips from "trips.txt" and save them to the database
  private void createTrips() {

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

}
