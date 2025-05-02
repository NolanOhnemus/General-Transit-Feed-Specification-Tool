package Lab4.test;

import Lab4.Model;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import Lab4.Stop;
import Lab4.StopTime;
import Lab4.Trip;
import Lab4.Route;

import java.io.File;

public class UnitTest {
    // When Testing, the GUI will open. Close the GUI out for the tests to complete.
    Model model;

    @BeforeEach
    void setup() {
        model = new Model(true);
    }

    @Test
    @DisplayName("Test the addStops function with valid files")
    void validAddStops() {
        // The addStops() function should return true if the file was successfully uploaded and
        // false if there was any sort of issue with the formatting

        // Valid file without any unexpected tricks
        Assertions.assertTrue(model.addStops(new File(System.getProperty("user.dir"),
                "\\test\\validStops1.txt")));
        // Valid file with shuffled column order
        // A failed test would imply a reliance on the column order from the examples
        Assertions.assertTrue(model.addStops(new File(System.getProperty("user.dir"),
                "\\test\\validStops2.txt")));
        // Valid file with quotation marks around all values
        // A failed test would imply issues with numbers with quotes
        Assertions.assertTrue(model.addStops(new File(System.getProperty("user.dir"),
                "\\test\\validStops3.txt")));
        // Valid file with internal commas in some values
        // A failed test would imply issues distinguishing between separating and non-separating
        // commas
        Assertions.assertTrue(model.addStops(new File(System.getProperty("user.dir"),
                "\\test\\validStops4.txt")));
        // Valid file with empty strings for stop_description, an optional value
        // A failed test would imply an expectation for stop_description to not be blank
        Assertions.assertTrue(model.addStops(new File(System.getProperty("user.dir"),
                "\\test\\validStops5.txt")));
        // Valid file with spaces after each of the commas, which is valid in CSV
        // A failed test would imply that optional spaces were not taken into account and may have
        // caused issues with the part of validation that checks if certain values are numerical
        Assertions.assertTrue(model.addStops(new File(System.getProperty("user.dir"),
                "\\test\\validStops6.txt")));
    }

    @Test
    @DisplayName("Test the addStops function with invalid files")
    void invalidAddStops() {
        // The addStops() function should return true if the file was successfully uploaded and
        // false if there was any sort of issue with the formatting
        Platform.runLater(
                () -> {
                    // PNG of a penguin converted to .txt
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStops1.txt")));
                    // File without a stopID column
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStops2.txt")));
                    // File without longitude column
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStops3.txt")));
                    // File with one less value than expected on the second row
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStops4.txt")));
                    // File with one of the latitude values replaced with the word "TOFU"
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStops5.txt")));
                    // File with one of the longitude values being beyond the range of valid longitudes
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStops6.txt")));
                }
        );

    }

    @Test
    @DisplayName("Test the addTrips function with valid files")
    void validAddTrips() {
        // The addTrips() function should return true if the file was successfully uploaded and
        // false if there was any sort of issue with the formatting

        // Valid file without any unexpected tricks
        Assertions.assertTrue(model.addTrips(new File(System.getProperty("user.dir"),
                "\\test\\validTrips1.txt")));
        // Valid file with shuffled column order
        // A failed test would imply a reliance on the column order from the examples
        Assertions.assertTrue(model.addTrips(new File(System.getProperty("user.dir"),
                "\\test\\validTrips2.txt")));
        // Valid file with quotation marks around all values
        // A failed test would imply issues with numbers with quotes
        Assertions.assertTrue(model.addTrips(new File(System.getProperty("user.dir"),
                "\\test\\validTrips3.txt")));
        // Valid file with internal commas in some values
        // A failed test would imply issues distinguishing between separating and non-separating
        // commas
        Assertions.assertTrue(model.addTrips(new File(System.getProperty("user.dir"),
                "\\test\\validTrips4.txt")));
        // Valid file with empty strings for trip_headsign, direction_id, and block_id, all
        // optional columns
        // A failed test would imply an expectation for trip_headsign, direction_id, and block_id
        // to not be blank
        Assertions.assertTrue(model.addTrips(new File(System.getProperty("user.dir"),
                "\\test\\validTrips5.txt")));
        // Valid file with spaces after each of the commas, which is valid in CSV
        // A failed test would imply that optional spaces were not taken into account and may have
        // caused issues with the part of validation that checks if certain values are numerical
        Assertions.assertTrue(model.addTrips(new File(System.getProperty("user.dir"),
                "\\test\\validTrips6.txt")));
    }

    @Test
    @DisplayName("Test the addTrips function with invalid files")
    void invalidAddTrips() {
        // The addTrips() function should return true if the file was successfully uploaded and
        // false if there was any sort of issue with the formatting
        Platform.runLater(
                () -> {
                    // PNG of a penguin converted to .txt
                    Assertions.assertFalse(model.addTrips(new File(System.getProperty("user.dir"),
                            "\\test\\invalidTrips1.txt")));
                    // File without a routeID column
                    Assertions.assertFalse(model.addTrips(new File(System.getProperty("user.dir"),
                            "\\test\\invalidTrips2.txt")));
                    // File without serviceID column
                    Assertions.assertFalse(model.addTrips(new File(System.getProperty("user.dir"),
                            "\\test\\invalidTrips3.txt")));
                    // File with one less value than expected on the second row
                    Assertions.assertFalse(model.addTrips(new File(System.getProperty("user.dir"),
                            "\\test\\invalidTrips4.txt")));
                    // File with one of the direction_id values replaced with the word "TOFU"
                    Assertions.assertFalse(model.addTrips(new File(System.getProperty("user.dir"),
                            "\\test\\invalidTrips5.txt")));
                    // File with one of the direction_id values replaced with the number 2, which is outside
                    // the range of accepted values
                    Assertions.assertFalse(model.addTrips(new File(System.getProperty("user.dir"),
                            "\\test\\invalidTrips6.txt")));
                }
        );
    }

    @Test
    @DisplayName("Test the addRoutes function with valid files")
    void validAddRoutes() {
        // The addRoutes() function should return true if the file was successfully uploaded and
        // false if there was any sort of issue with the formatting

        // Valid file without any unexpected tricks
        Assertions.assertTrue(model.addRoutes(new File(System.getProperty("user.dir"),
                "\\test\\validRoutes1.txt")));
        // Valid file with shuffled column order
        // A failed test would imply a reliance on the column order from the examples
        Assertions.assertTrue(model.addRoutes(new File(System.getProperty("user.dir"),
                "\\test\\validRoutes2.txt")));
        // Valid file with quotation marks around all values
        // A failed test would imply issues with numbers with quotes
        Assertions.assertTrue(model.addRoutes(new File(System.getProperty("user.dir"),
                "\\test\\validRoutes3.txt")));
        // Valid file with internal commas in some values
        // A failed test would imply issues distinguishing between separating and non-separating
        // commas
        Assertions.assertTrue(model.addRoutes(new File(System.getProperty("user.dir"),
                "\\test\\validRoutes4.txt")));
        // Valid file with empty strings for route_description, route_url, and text_color
        // A failed test would imply an expectation for stop_description to not be blank
        Assertions.assertTrue(model.addRoutes(new File(System.getProperty("user.dir"),
                "\\test\\validRoutes5.txt")));
        // Valid file with spaces after each of the commas, which is valid in CSV
        // A failed test would imply that optional spaces were not taken into account and may have
        // caused issues with the part of validation that checks if certain values are numerical
        Assertions.assertTrue(model.addRoutes(new File(System.getProperty("user.dir"),
                "\\test\\validRoutes6.txt")));
    }

    @Test
    @DisplayName("Test the addRoutes function with invalid files")
    void invalidAddRoutes() {
        // The addRoutes() function should return true if the file was successfully uploaded and
        // false if there was any sort of issue with the formatting
        Platform.runLater(
                () -> {
                    // PNG of a penguin converted to .txt
                    Assertions.assertFalse(model.addRoutes(new File(System.getProperty("user.dir"),
                            "\\test\\invalidRoutes1.txt")));
                    // File without a route_id column
                    Assertions.assertFalse(model.addRoutes(new File(System.getProperty("user.dir"),
                            "\\test\\invalidRoutes2.txt")));
                    // File without route_type column
                    Assertions.assertFalse(model.addRoutes(new File(System.getProperty("user.dir"),
                            "\\test\\invalidRoutes3.txt")));
                    // File with one less value than expected on the second row
                    Assertions.assertFalse(model.addRoutes(new File(System.getProperty("user.dir"),
                            "\\test\\invalidRoutes4.txt")));
                    // File with one of the route_url values replaced with the word "TOFU"
                    Assertions.assertFalse(model.addRoutes(new File(System.getProperty("user.dir"),
                            "\\test\\invalidRoutes5.txt")));
                    // File with one of the route_type values replaced with the number 8, which is outside
                    // the range of accepted values
                    Assertions.assertFalse(model.addRoutes(new File(System.getProperty("user.dir"),
                            "\\test\\invalidRoutes6.txt")));
                }
        );
    }

    @Test
    @DisplayName("Test the addStopTimes function with valid files")
    void validAddStopTimes() {
        // The addStopTimes() function should return true if the file was successfully uploaded and
        // false if there was any sort of issue with the formatting
        Platform.runLater(
                () -> {
                    // Valid file without any unexpected tricks
                    Assertions.assertTrue(model.addStopTimes(new File(System.getProperty("user.dir"),
                            "\\test\\validStopTimes1.txt")));
                    // Valid file with shuffled column order
                    // A failed test would imply a reliance on the column order from the examples
                    Assertions.assertTrue(model.addStopTimes(new File(System.getProperty("user.dir"),
                            "\\test\\validStopTimes2.txt")));
                    // Valid file with quotation marks around all values
                    // A failed test would imply issues with numbers with quotes
                    Assertions.assertTrue(model.addStopTimes(new File(System.getProperty("user.dir"),
                            "\\test\\validStopTimes3.txt")));
                    // Valid file with internal commas in some values
                    // A failed test would imply issues distinguishing between separating and non-separating
                    // commas
                    Assertions.assertTrue(model.addStopTimes(new File(System.getProperty("user.dir"),
                            "\\test\\validStopTimes4.txt")));
                    // Valid file with empty strings for stop_headsign, pickup_type, and drop_off_type, all
                    // optional values
                    // A failed test would imply an expectation for stop_description to not be blank
                    Assertions.assertTrue(model.addStopTimes(new File(System.getProperty("user.dir"),
                            "\\test\\validStopTimes5.txt")));
                    // Valid file with spaces after each of the commas, which is valid in CSV
                    // A failed test would imply that optional spaces were not taken into account and may have
                    // caused issues with the part of validation that checks if certain values are numerical
                    Assertions.assertTrue(model.addStopTimes(new File(System.getProperty("user.dir"),
                            "\\test\\validStopTimes6.txt")));
                }
        );
    }

    @Test
    @DisplayName("Test the addStopTimes function with invalid files")
    void invalidAddStopTimes() {
        // The addStopTimes() function should return true if the file was successfully uploaded and
        // false if there was any sort of issue with the formatting
        Platform.runLater(
                () -> {
                    // PNG of a penguin converted to .txt
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStopTimes1.txt")));
                    // File without a trip_id column
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStopTimes2.txt")));
                    // File without stop_id column
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStopTimes3.txt")));
                    // File with one less value than expected on the second row
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStopTimes4.txt")));
                    // File with one of the arrival_time values replaced with the word "TOFU"
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStopTimes5.txt")));
                    // File with one of the stop_sequence values replaced with a -1, outside the range of
                    // accepted values
                    Assertions.assertFalse(model.addStops(new File(System.getProperty("user.dir"),
                            "\\test\\invalidStopTimes6.txt")));
                }
        );
    }
    // TESTS ONLY USE MCTS GTFS FILES
    @Test
    @DisplayName("Test Stop getter methods")
    void getStopTest(){
        model.addStops(new File(System.getProperty("user.dir"),"stops.txt"));
        // Get Stop to test, and create new Stop containing correct information
        Stop stopTest = new Stop("2994","HIGHLAND & N17 #2994","",43.0445175,-87.9337565, model);
        Stop stop = model.getStops().get("2994");
        // Compare instance variables
        Assertions.assertEquals(stop.getStopId(), stopTest.getStopId());
        Assertions.assertEquals(stop.getStopName(), stopTest.getStopName());
        Assertions.assertEquals(stop.getStopDesc(), stopTest.getStopDesc());
        Assertions.assertEquals(stop.getStopLat(), stopTest.getStopLat());
        Assertions.assertEquals(stop.getStopLon(), stopTest.getStopLon());

    }
    @Test
    @DisplayName("Test Route getter methods")
    void getRouteTest(){
        model.addRoutes(new File(System.getProperty("user.dir"),"routes.txt"));
        // Get Route to test, and create new Route containing correct information
        Route routeTest = new Route("B44","MCTS","44","Brigg's Run - State Fair",
                "",3,"",0x008345,0x000000, model);
        Route route = model.getRoutes().get("B44");
        // Compare instance variables
        Assertions.assertEquals(route.getRouteId(),routeTest.getRouteId());
        Assertions.assertEquals(route.getAgencyId(),routeTest.getAgencyId());
        Assertions.assertEquals(route.getRouteShortName(),routeTest.getRouteShortName());
        Assertions.assertEquals(route.getRouteLongName(),routeTest.getRouteLongName());
        Assertions.assertEquals(route.getRouteDescription(),routeTest.getRouteDescription());
        Assertions.assertEquals(route.getRouteType(),routeTest.getRouteType());
        Assertions.assertEquals(route.getRouteUrl(),routeTest.getRouteUrl());
        Assertions.assertEquals(route.getRouteColor(),routeTest.getRouteColor());
        Assertions.assertEquals(route.getRouteTextColor(), routeTest.getRouteTextColor());
    }
    @Test
    @DisplayName("Test Trip getter methods")
    void getTripTest(){
        model.addTrips(new File(System.getProperty("user.dir"),"trips.txt"));
        // Get Trip to test, and create new Trip containing correct information
        Trip tripTest = new Trip("80","17-SEP_SUN", "21736832_378",
                "AIRPORT", 1, 80105, "17-SEP_80_1_122", model, model.getStopTimes());
        Trip trip = model.getTrips().get("21736832_378");
        // Compare instance variables
        Assertions.assertEquals(trip.getTripId(),tripTest.getTripId());
        Assertions.assertEquals(trip.getRouteId(),tripTest.getRouteId());
        Assertions.assertEquals(trip.getServiceId(),tripTest.getServiceId());
        Assertions.assertEquals(trip.getTripHeadsign(),tripTest.getTripHeadsign());
        Assertions.assertEquals(trip.getDirectionId(),tripTest.getDirectionId());
        Assertions.assertEquals(trip.getBlockId(),tripTest.getBlockId());
        Assertions.assertEquals(trip.getShapeId(),tripTest.getShapeId());
    }
    @Test
    @DisplayName("Test StopTime getter methods")
    void getStopTimeTest(){
        model.addStopTimes(new File(System.getProperty("user.dir"),"stop_times.txt"));
        // Get StopTime to test, and create new StopTime containing correct information
        StopTime stopTimeTest = new StopTime("21736567_2541",45060000L,45060000L,
                "4756",3,"",0,0, model);
        StopTime stopTime = model.getStopTimes().get(164);
        // Compare instance variables
        Assertions.assertEquals(stopTime.getTripId(),stopTimeTest.getTripId());
        Assertions.assertEquals(stopTime.getDepartureTime(),stopTimeTest.getDepartureTime());
        Assertions.assertEquals(stopTime.getArrivalTime(),stopTimeTest.getArrivalTime());
        Assertions.assertEquals(stopTime.getStopId(),stopTimeTest.getStopId());
        Assertions.assertEquals(stopTime.getStopSequence(),stopTimeTest.getStopSequence());
        Assertions.assertEquals(stopTime.getPickupType(),stopTimeTest.getPickupType());
        Assertions.assertEquals(stopTime.getStopHeadsign(),stopTimeTest.getStopHeadsign());
        Assertions.assertEquals(stopTime.getDropOffType(),stopTimeTest.getDropOffType());
    }
}