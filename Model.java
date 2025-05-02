package Lab4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

/**
 * @author bautchc
 * @version 1.0
 * @created 04-Oct-2022 10:09:58 AM
 * @license Apache 2.0
 */
public class Model extends Application {
	private UIController controller;
	private static HashMap<String, Route> routes;
	private static HashMap<String, Stop> stops;
	private static HashMap<String, Trip> trips;
	private static ArrayList<StopTime> stopTimes;
	private final long TIME_IN_DAY = 86400000;
	private long systemTime = System.currentTimeMillis()%TIME_IN_DAY;
	private List<String> routeIDList = new ArrayList<>();
	private List<String> tripIDList = new ArrayList<>();
	private List<String> stopIDList = new ArrayList<>();

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GTFS.fxml"));
		Parent mainWindow = loader.load();
		Scene scene = new Scene(mainWindow);
		scene.getStylesheets().add(getClass().getResource("style.css").toString());
		stage.setTitle("GTFSTool");
		stage.setScene(scene);
		stage.show();

		controller = loader.getController();
		controller.setModel(this);
	}

	public Model() {

	}

	/**
	 * Extra model constructor only used for testing since Model is usually launched through the
	 * main method, something that would make some tests more complex
	 * @param isTesting Not used
	 */
	public Model(boolean isTesting) {
		routes = new HashMap<>();
		stops = new HashMap<>();
		trips = new HashMap<>();
		stopTimes = new ArrayList<>();
		launch();
	}

	public static void main(String[] args) {
		routes = new HashMap<>();
		stops = new HashMap<>();
		trips = new HashMap<>();
		stopTimes = new ArrayList<>();
		launch(args);
	}

	/**
	 * Reads the stops from the files and adds the new stops to the data structures
	 * @param stopFile the file containing all the stops
	 * @return true if parsing was completed and false if any problems arise
	 */
	public boolean addStops(File stopFile)
	{
		String[] entryLocation;
		ArrayList<String[]> lines = new ArrayList<>();

		String stopId = "";
		String stopName = "";
		String stopDesc = "";
		double stopLat = -1;
		double stopLong = -1;
		Stop stop;
		ArrayList<StopTime> stopTimesArray;

		try(BufferedReader in = new BufferedReader(new FileReader(stopFile))) {
			String entryString = in.readLine();
			boolean firstLineCheck = checkStopsFirstLine(entryString);
			if(!firstLineCheck)
			{
				return false;
			}
			entryLocation = entryString.split(",");
			String nextLine;
			while((nextLine = in.readLine()) != null) {
				String[] nextSplitLine = splitLine(nextLine);
				boolean check = checkStopsDataLines(nextSplitLine);
				if(!check)
				{
					return false;
				}
				lines.add(splitLine(nextLine));
			}
		} catch(IOException e) {
			return false;
		}
		for(String[] line : lines) {
			for (int i = 0; i < entryLocation.length; i++) {
				try {
					switch (entryLocation[i]) {
						case "stop_id" -> stopId = line[i];
						case "stop_name" -> stopName = line[i];
						case "stop_desc" -> stopDesc = line[i];
						case "stop_lat" -> stopLat = Double.parseDouble(line[i]);
						case "stop_lon" -> stopLong = Double.parseDouble(line[i]);
					}
				} catch(NumberFormatException e) {
					stops.clear();
					return false;
				}
			}
			stopIDList.add(stopId);
			stop = new Stop(stopId, stopName, stopDesc, stopLat, stopLong, this);
			stops.put(stopId, stop);
			// if Stops was added after StopTimes, add StopTimes to stops
			if (stopTimes.size() != 0) {
				stopTimesArray = stop.getStopTimes();
				for (StopTime stopTime : stopTimes) {
					if (stopTime.getStopId().equals(stopId)) {
						stopTimesArray.add(stopTime);
					}
				}
			}
		}
		return true;
	}

	private boolean checkStopsFirstLine(String firstLine){
		String[] lineArray = firstLine.split(",");
		boolean stopId = false;
		boolean stopName = false;
		boolean stopLong = false;
		boolean stopLat = false;
		if(lineArray.length < 4 || lineArray[4].equals(""))
		{
			throwFileException("stops.txt", "first line has missing headers");
			return false;
		}
		for(String str : lineArray)
		{
			switch(str)
			{
				case "stop_id":
					stopId = true;
				case "stop_name":
					stopName = true;
				case "stop_lat":
					stopLat = true;
				case "stop_lon":
					stopLong = true;
			}
		}
		if(!stopId || !stopName || !stopLat || !stopLong)
		{
			throwFileException("stops.txt", "first line is missing vital header");
			return false;
		}
		else return true;
	}

	private boolean checkStopsDataLines(String[] dataLine)
	{
		if(dataLine.length == 5)
		{
			int cordCount = 0;
			int stringCount = 0;
			for (String tempStr : dataLine) {
				if(!tempStr.equals(""))
				{
					try{
						Double.parseDouble(tempStr);
						cordCount++;
					}catch(NumberFormatException n)
					{
						stringCount++;
					}
				}
			}
			if(cordCount >= 2 && stringCount >= 1)
			{
				return true;
			}
			else{
				throwFileException("stops.txt", "data line and has improper data counts");
				return false;
			}
		}
		else
		{
			throwFileException("stops.txt", "data line is not long enough");
			return false;
		}
	}

	private void throwFileException(String filename, String failLocation)
	{
		Alert alert = new Alert(Alert.AlertType.ERROR, "File: " + filename + " has a problem in\n" + failLocation);
		alert.show();
	}

	/**
	 * Reads the trips from the files and adds the new trips to the data structures
	 * @param tripFile the file containing all the stops
	 * @return true if parsing was completed and false if any problems arise
	 */
	public boolean addTrips(File tripFile)
	{
		String[] entryLocation;
		String routeID = ""; // Required
		String serviceID = ""; // Required
		String tripID = ""; // Required
		String tripHeadSign = ""; // Optional
		int direction = -1; // Optional
		long block = -1; // Optional
		String shapeID = ""; // Conditionally required
		Trip trip;

		try(BufferedReader in = new BufferedReader(new FileReader(tripFile))) {
			String entryString = in.readLine();
			boolean check = checkTripsFirstLine(entryString);
			if(!check)
			{
				return false;
			}
			entryLocation = entryString.split(",");
			String nextLine;
			while((nextLine = in.readLine()) != null) {
				String[] nextLineArray = splitLine(nextLine);
				boolean dataCheck = checkTripsDataLines(nextLineArray);
				if(!dataCheck)
				{
					return false;
				}
				for (int i = 0; i < entryLocation.length; i++) {
					try {
						switch (entryLocation[i]) {
							case "route_id" -> routeID = nextLineArray[i];
							case "service_id" -> serviceID = nextLineArray[i];
							case "trip_id" -> tripID = nextLineArray[i];
							case "trip_headsign" -> tripHeadSign = nextLineArray[i];
							case "direction_id" -> direction = !(nextLineArray[i].equals(""))
									? Integer.parseInt(nextLineArray[i]) : -1;
							case "block_id" -> block = !(nextLineArray[i].equals(""))
									? Long.parseLong(nextLineArray[i]) : -1;
							case "shape_id" -> shapeID = nextLineArray[i];
						}
					} catch(NumberFormatException e) {
						trips.clear();
						return false;
					}
				}
				tripIDList.add(tripID);
				ArrayList<StopTime> stopTimesLocal = new ArrayList<>();
				if(stopTimes.size() != 0)
				{
					stopTimesLocal = stopTimes;
				}
				trip = new Trip(routeID, serviceID, tripID, tripHeadSign, direction, block,
						shapeID, stops, stopTimesLocal);
				trips.put(tripID, trip);
			}
		} catch(IOException e) {
			return false;
		}
		return true;
	}

	private boolean checkTripsFirstLine(String firstLine) {
		String[] lineArray = firstLine.split(",");
		if (lineArray.length < 6 || lineArray[6].equals("")) {
			throwFileException("trips.txt", "trips header is missing a header");
			return false;
		}
		boolean routeId = false;
		boolean tripId = false;
		boolean shapeId = false;
		for (String str : lineArray) {
			switch (str) {
				case "route_id":
					routeId = true;
				case "trip_id":
					tripId = true;
				case "shape_id":
					shapeId = true;
			}
		}
		if (!routeId || !tripId || !shapeId) {
			throwFileException("trips.txt", "tips is missing a vital header");
			return false;
		} else return true;
	}

	private boolean checkTripsDataLines(String[] dataLine)
	{
		if(dataLine.length == 7)
		{
			int stringCount = 0;
			for (String tempStr : dataLine) {
				if(!tempStr.equals(""))
				{
					stringCount++;
				}
			}
			if(stringCount >= 3)
			{
				return true;
			}
			else{
				throwFileException("trips.txt", "data lines are missing a vital number of data entries");
				return false;
			}
		}
		else
		{
			throwFileException("trips.txt", "data lines contain an improper amount of data");
			return false;
		}
	}

	/**
	 * Adds routes from file
	 * @param routesFile CSV file containing routes
	 * @return True if the file is successfully added and false otherwise
	 * @author Coltrane Bautch
	 */
	public boolean addRoutes(File routesFile) {
		String[] headers;
		String currentID = ""; // Required
		String currentAgencyID = ""; // Only required if more than one agency
		String currentShortName = ""; // Not required if long_name is there, but may have both
		String currentLongName = ""; // Not required if short_name is there, but may have both
		String currentDesc = ""; // Optional
		int currentType = 0; // Required
		String currentURL = ""; // Optional
		int currentColor = 0xFFFFFF; // Required (Optional in docs, but required in Proj Desc)
		int currentTextColor = 0x000000; // Optional
		// Not covered fields:
		// route_sort_order, continuous_pickup, continuous_drop_off
		try(BufferedReader in = new BufferedReader(new FileReader(routesFile))) {
			String headerLine = in.readLine();
			boolean check = checkRoutesFirstLine(headerLine);
			if(!check)
			{
				return false;
			}
			headers = headerLine.split(",");
			String nextLine;
			while((nextLine = in.readLine()) != null) {
				String[] nextLineArray = splitLine(nextLine);
				boolean dataCheck = checkRoutesDataLines(nextLineArray);
				if(!dataCheck)
				{
					return false;
				}
				for (int i = 0; i < headers.length; i++) {
					try {
						switch (headers[i]) {
							case "route_id" -> currentID = nextLineArray[i];
							case "agency_id" -> currentAgencyID = nextLineArray[i];
							case "route_short_name" -> currentShortName = nextLineArray[i];
							case "route_long_name" -> currentLongName = nextLineArray[i];
							case "route_desc" -> currentDesc = nextLineArray[i];
							case "route_type" -> currentType = Integer.parseInt(nextLineArray[i]);
							case "route_url" -> currentURL = nextLineArray[i];
							case "route_color" -> currentColor = Integer.parseInt(nextLineArray[i], 16);
							case "route_text_color" -> currentTextColor = !(nextLineArray[i].equals("")) ?  Integer.parseInt(nextLineArray[i], 16) : 0x000000;
						}
					} catch(NumberFormatException e) {
						routes.clear();
						return false;
					}
				}
				routeIDList.add(currentID);
				routes.put(currentID, new Route(currentID, currentAgencyID, currentShortName,
						currentLongName, currentDesc, currentType, currentURL, currentColor, currentTextColor, this));
			}
		} catch(IOException e) {
			return false;
		}
		return true;
	}

	private boolean checkRoutesFirstLine(String firstLine) {
		String[] lineArray = firstLine.split(",");
		if (lineArray.length < 8 || lineArray[8].equals("")) {
			throwFileException("routes.txt", "first line is missing headers");
			return false;
		}
		boolean routeId = false;
		boolean routeColor = false;
		for (String str : lineArray) {
			switch (str) {
				case "route_id":
					routeId = true;
				case "route_color":
					routeColor = true;
			}
		}
		if (!routeId || !routeColor) {
			throwFileException("routes.txt", "first line is missing a vital header");
			return false;
		} else return true;
	}

	private boolean checkRoutesDataLines(String[] dataLine)
	{
		if(dataLine.length == 9)
		{
			int stringCount = 0;
			for (String tempStr : dataLine) {
				if(!tempStr.equals(""))
				{
					stringCount++;
				}
			}
			if(stringCount >= 2)
			{
				return true;
			}
			else{
				throwFileException("routes.txt", "routes contains an invalid amount of data");
				return false;
			}
		}
		else
		{
			throwFileException("routes.txt", "routes is missing data");
			return false;
		}
	}

	/**
	 * Adds stop times from file
	 * @param stopTimesFile CSV file containing stoptimes
	 * @return True if the file is successfully added and false otherwise
	 * @author Coltrane Bautch
	 */
	public boolean addStopTimes(File stopTimesFile) {
		String[] headers;
		StopTime currentStopTime;
		String currentTripID = ""; // Required
		long currentArrivalTime = 0L; // Conditionally required
		long currentDepartureTime = 0L; // Conditionally required
		String currentStopID = ""; // Required
		int currentSequence = 0; // Required
		String currentHeadsign = ""; // Optional
		int currentPickupType = 0; // Optional
		int currentDropOffType = 0; // Optional
		// Not covered fields:
		// continuous_pickup, continuous_drop_off, shape_dist_traveled, timepoint
		try(BufferedReader in = new BufferedReader(new FileReader(stopTimesFile))) {
			String headerLine = in.readLine();
			boolean check = checkStopTimesFirstLine(headerLine);
			if(!check)
			{
				return false;
			}
			headers = headerLine.split(",");
			String nextLine;
			while((nextLine = in.readLine()) != null) {
				String[] nextLineArray = splitLine(nextLine);
				boolean dataCheck = checkStopTimesDataLines(nextLineArray);
				if(!dataCheck)
				{
					return false;
				}
				for (int i = 0; i < headers.length; i++) {
					try {
						switch (headers[i]) {
							case "trip_id" -> currentTripID = nextLineArray[i];
							case "arrival_time" -> currentArrivalTime = timeToMS(nextLineArray[i]);
							case "departure_time" -> currentDepartureTime = timeToMS(nextLineArray[i]);
							case "stop_id" -> currentStopID = nextLineArray[i];
							case "stop_sequence" -> currentSequence = Integer.parseInt(nextLineArray[i]);
							case "stop_headsign" -> currentHeadsign = nextLineArray[i];
							case "pickup_type" -> currentPickupType = !(nextLineArray[i].equals("")) ?  Integer.parseInt(nextLineArray[i]) : 0;
							case "drop_off_type" -> currentDropOffType = !nextLineArray[i].equals("") ?  Integer.parseInt(nextLineArray[i]) : 0;
						}
					} catch(NumberFormatException e) {
						stopTimes.clear();
						return false;
					}
				}
				currentStopTime = new StopTime(currentTripID, currentArrivalTime, currentDepartureTime,
						currentStopID, currentSequence, currentHeadsign, currentPickupType,
						currentDropOffType, this);
				if (trips.get(currentTripID) != null) {
					trips.get(currentTripID).getStopTimes().add(currentStopTime);
				}
				if (stops.get(currentStopID) != null) {
					stops.get(currentStopID).getStopTimes().add(currentStopTime);
				}
				stopTimes.add(currentStopTime);
			}
		} catch(IOException e) {
			return false;
		}
		return true;
	}

	private boolean checkStopTimesFirstLine(String firstLine) {
		String[] lineArray = firstLine.split(",");
		if (lineArray.length < 6 || lineArray[6].equals("")) {
			throwFileException("stop_times.txt", "first line is missing headers");
			return false;
		}
		boolean tripId = false;
		boolean arrivalTime = false;
		boolean departureTime = false;
		boolean stopId = false;
		boolean stopSequence = false;

		for (String str : lineArray) {
			switch (str) {
				case "trip_id":
					tripId = true;
				case "arrival_time":
					arrivalTime = true;
				case "departure_time":
					departureTime = true;
				case "stop_id":
					stopId = true;
				case "stop_sequence":
					stopSequence = true;
			}
		}

		if (!tripId || !arrivalTime || !departureTime || !stopId || !stopSequence) {
			throwFileException("stop_times.txt", "first line is missing an essential header");
			return false;
		} else return true;
	}

	private boolean checkStopTimesDataLines(String[] dataLine)
	{
		if(dataLine.length == 8)
		{
			boolean sequenceCheck = false;
			int stringCount = 0;
			for (String tempStr : dataLine) {
				if(!tempStr.equals(""))
				{
					try{
						Integer.parseInt(tempStr);
						sequenceCheck = true;
					}catch(NumberFormatException n)
					{
						stringCount++;
					}
				}
			}
			if(sequenceCheck && stringCount >= 3)
			{
				return true;
			}
			else{
				throwFileException("stop_times.txt", "data line contains an invalid amount of data");
				return false;
			}
		}
		else
		{
			throwFileException("stop_times.txt", "data lines contain not enough data");
			return false;
		}
	}

	private long timeToMS(String time) {
		String[] timeArray = time.split(":");
		return Integer.parseInt(timeArray[0]) * 3600000L + Integer.parseInt(timeArray[1]) * 60000L
				+ Integer.parseInt(timeArray[2]) * 1000L;
	}

	private String[] splitLine(String line) {
		StringBuilder currentTerm = new StringBuilder();
		ArrayList<String> outputArray = new ArrayList<>();
		boolean inQuotes = false;
		for (char c : line.toCharArray()) {
			if (c == '\"' && !inQuotes) {
				inQuotes = true;
			} else if (c == '\"') {
				inQuotes = false;
			} else if (c == ',' && !inQuotes) {
				outputArray.add(currentTerm.toString().trim());
				currentTerm = new StringBuilder();
			} else {
				currentTerm.append(c);
			}
		}
		outputArray.add(currentTerm.toString().trim());
		return outputArray.toArray(new String[0]);
	}

	/**
	 * Generates a visual of the route and projects it onto a map
	 */
	public TransitMap generateMap(String routeID){
		HashMap<String, ArrayList<StopTime>> relevantStopTimes = new HashMap<>();
		ArrayList<String> relevantTrips = new ArrayList<>();
		HashMap<String, Stop> relevantStops = new HashMap<>();
		ArrayList<String> relevantStopIDs = new ArrayList<>();
		double minLat = 1;
		double maxLat = 0;
		double minLon = 1;
		double maxLon = 0;
		for (String tripID : tripIDList) {
			if (trips.get(tripID).getRouteId().equals(routeID)) {
				relevantStopTimes.put(tripID, new ArrayList<>());
				relevantTrips.add(tripID);
			}
		}
		for (StopTime stopTime : stopTimes) {
			if (relevantStopTimes.containsKey(stopTime.getTripId())) {
				while (relevantStopTimes.get(stopTime.getTripId()).size()
						<= stopTime.getStopSequence()) {
					relevantStopTimes.get(stopTime.getTripId()).add(null);
				}
				relevantStopTimes.get(stopTime.getTripId())
						.set(stopTime.getStopSequence() - 1, stopTime);
				if(!relevantStops.containsKey(stopTime.getStopId())) {
					relevantStops.put(stopTime.getStopId(), new Stop(stopTime.getStopId(),
							stops.get(stopTime.getStopId()).getStopName(),
							stops.get(stopTime.getStopId()).getStopDesc(),
							(1.0 / 180.0) * (90.0 - stops.get(stopTime.getStopId()).getStopLat()),
							(1.0 / 360.0) * (180.0 + stops.get(stopTime.getStopId()).getStopLon()),
							this));
					relevantStopIDs.add(stopTime.getStopId());
				}


				if ((1.0 / 180.0) * (90.0 - stops.get(stopTime.getStopId()).getStopLat()) < minLat) {
					minLat = (1.0 / 180.0) * (90.0 - stops.get(stopTime.getStopId()).getStopLat());
				}
				if ((1.0 / 180.0) * (90.0 - stops.get(stopTime.getStopId()).getStopLat()) > maxLat) {
					maxLat = (1.0 / 180.0) * (90.0 - stops.get(stopTime.getStopId()).getStopLat());
				}
				if ((1.0 / 360.0) * (180.0 + stops.get(stopTime.getStopId()).getStopLon()) < minLon) {
					minLon = (1.0 / 360.0) * (180.0 + stops.get(stopTime.getStopId()).getStopLon());
				}
				if ((1.0 / 360.0) * (180.0 + stops.get(stopTime.getStopId()).getStopLon()) > maxLon) {
					maxLon = (1.0 / 360.0) * (180.0 + stops.get(stopTime.getStopId()).getStopLon());
				}
			}
		}
		return new TransitMap(routes.get(routeID), relevantTrips, relevantStops, relevantStopIDs, relevantStopTimes,
				minLat, maxLat, minLon, maxLon);
	}

	/**
	 * moves the stop location to a new desired location
	 * @param stop the stop to be moved
	 * @param latitude the new latitude of the stop
	 * @param longitude the new longitude of the stop
	 * @return true if stop is successfully moved
	 * @author Nathaniel Zawarus
	 */
	public boolean moveStop(Stop stop, double latitude, double longitude){
		stop.moveStop(latitude,longitude);
		return stop.getLatitude() == latitude && stop.getLongitude() == longitude;
	}

	public int numberOfTrips(String stopId){
		int howManyTrips = 0;
		for(StopTime s: stopTimes){
			if(s.getStopId().equals(stopId)){
				howManyTrips++;
			}
		}
		return howManyTrips;
	}

	/**
	 * A method to find the next trip happening at a given stop
	 * @param stopId the identifier for the stop were searching for
	 * @author Nathaniel Zawarus
	 */
	public Trip nextTripForStop(String stopId){
		long earliestTime = TIME_IN_DAY;
		Trip nextTrip = trips.get("0");
		for (StopTime s:
			 stopTimes) {
			if(s.getStopId().equals(stopId)){
				if(s.getDepartureTime()>systemTime&&s.getDepartureTime()<earliestTime){
					earliestTime = s.getDepartureTime();
					nextTrip = trips.get(s.getTripId());
				}
			}
		}
		return nextTrip;
	}

	/**
	 * A method that gives all routes that go through a given stop
	 * @param stopId the identifier for the stop were searching for
	 * @return An ArrayList of routes that contains all the routes in a stop
	 * @author Nathaniel Zawarus
	 */
	public HashMap<String, Route> routeForStop(String stopId){
		HashMap<String, Route> routes1 = new HashMap<>();
		for (StopTime s:
			 stopTimes) {
			if(s.getStopId().equals(stopId)){
				routes1.put(trips.get(s.getTripId()).getRouteId(), routes.get(trips.get(s.getTripId()).getRouteId()));
			}
		}
		return routes1;
	}


	/**
	 * Searches all routes and matches route ID's
	 * @param routeId the route id for a desired route
	 * @return the desired route as a Route object
	 * @author Nathaniel Zawarus
	 */
	public Route searchRoute(String routeId){
		return routes.get(routeId);
	}

	/**
	 * Searches for a stop given an ID and returns a matching stop object
	 * @param stopId the ID for the desired stop
	 * @return the desired stop as a Stop object
	 * @author Nathaniel Zawarus
	 */
	public Stop searchStop(String stopId){
		return stops.get(stopId);
	}

	/**
	 * Searches for a trip with the given ID and returns a matching stop Object
	 * @param tripId the ID of the desired trip
	 * @return the desired trip as a Trip object
	 * @author Ayden Barber
	 */
	public Trip searchTrip(String tripId) {return trips.get(tripId);}

	/**
	 * Searches for a Stop Time given its place in a sequence
	 * @param sequence where the Stop Time appears in a sequence of stop times
	 * @return the desired stop time as a StopTime object
	 * @author Ayden Barber
	 */
	public StopTime searchStopSequence(int sequence) {return stopTimes.get(sequence);}

	/**
	 * Searches for all stops along a given route
	 * @param routeId the identifier for a given route
	 * @return an ArrayList of all stops along the route
	 * @author Nathaniel Zawarus
	 */
	public HashMap<String, Stop> stopsForRoute(String routeId){
		HashMap<String,Stop> stops1 = new HashMap<>();
		for (StopTime s: stopTimes) {
			if(trips.get(s.getTripId()).getRouteId().equals(routeId)) {
				stops1.put(s.getStopId(), stops.get(s.getStopId()));
			}
		}
		return stops1;
	}


	/**
	 * Searches for all trips that go along a given route
	 * @param routeId the identifier for a given route
	 * @return an ArrayList of all trips that use the route
	 * @author Nathaniel Zawarus
	 */
	public ArrayList<Trip> tripsForRoute(String routeId){
		ArrayList<Trip> trips1 = new ArrayList<>();
		trips.forEach((tripId, trip) -> {
			if(trip.getRouteId().equals(routeId)){
				trips1.add(trip);
			}
		});
		return trips1;
	}

	public HashMap<String, Stop> getStops() {
		return stops;
	}

	/**
	 * Updates a given route
	 * @param route the route to be updated
	 * @author Nathaniel Zawarus
	 */
	public void updateRoute(Route route) {
		Route routeToUpdate = routes.get(route.getRouteId());
		while(!routeToUpdate.equals(route)){
			if(routeToUpdate.getRouteColor()!=route.getRouteColor()){
				routeToUpdate.setRouteColor(route.getRouteColor());
			}else if(!routeToUpdate.getRouteUrl().equals(route.getRouteUrl())){
				routeToUpdate.setRouteUrl(route.getRouteUrl());
			}else if(routeToUpdate.getRouteType()!=route.getRouteType()){
				routeToUpdate.setRouteType(route.getRouteType());
			}else if(!routeToUpdate.getRouteDescription().equals(route.getRouteDescription())){
				routeToUpdate.setRouteDescription(route.getRouteDescription());
			}else if(!routeToUpdate.getAgencyId().equals(route.getAgencyId())){
				routeToUpdate.setAgencyId(route.getAgencyId());
			}else if(!routeToUpdate.getRouteLongName().equals(route.getRouteLongName())){
				routeToUpdate.setRouteLongName(route.getRouteLongName());
			}else if(!routeToUpdate.getRouteShortName().equals(route.getRouteShortName())){
				routeToUpdate.setRouteShortName(route.getRouteShortName());
			}else if(routeToUpdate.getRouteTextColor()!=route.getRouteTextColor()){
				routeToUpdate.setRouteTextColor(route.getRouteTextColor());
			}
		}
	}

	public HashMap<String, Trip> getTrips() {
		return trips;
	}

	/**
	 * Updates a given stop
	 * @param stop the stop to be updated
	 * @author Nathaniel Zawarus
	 */
	public void updateStop(Stop stop){
		String stopId = stop.getStopId();
		Stop stopToUpdate = stops.get(stopId);
		while(!stopToUpdate.equals(stop))
		if(!stopToUpdate.getStopName().equals(stop.getStopName())){
			stopToUpdate.setStopName(stop.getStopName());
		} else if(stopToUpdate.getLatitude() != stop.getLatitude()){
			stopToUpdate.moveStop(stop.getLatitude(), stopToUpdate.getLongitude());
		} else if(stopToUpdate.getLongitude() != stop.getLongitude()){
			stopToUpdate.moveStop(stopToUpdate.getLatitude(), stop.getLongitude());
		} else if(!stopToUpdate.getStopDesc().equals(stop.getStopDesc())){
			stopToUpdate.setStopDesc(stop.getStopDesc());
		}
	}

	/**
	 * Updates a single stop time
	 * @param stopTime the stop time to be updated
	 * @author Nathaniel Zawarus
	 */
	public void updateStopTime(StopTime stopTime){
		StopTime stopTime1 = stopTime;
		for (StopTime s:
			 stopTimes) {
			if(stopTime.getStopId().equals(s.getStopId())&&stopTime.getTripId().equals(s.getTripId())){
				stopTime1 = s;
			}
		}
		while(!stopTime1.equals(stopTime)){
			if(stopTime1.getArrivalTime()!=stopTime.getArrivalTime()){
				stopTime1.setArrivalTime(stopTime.getArrivalTime());
			} else if(stopTime1.getDepartureTime()!=stopTime.getDepartureTime()){
				stopTime1.setDepartureTime(stopTime.getDepartureTime());
			} else if(stopTime1.getStopSequence()!=stopTime.getStopSequence()){
				stopTime1.setStopSequence(stopTime.getStopSequence());
			}
		}
	}

	public HashMap<String, Route> getRoutes() {
		return routes;
	}

	public List<String> getRouteIDList() {
		return routeIDList;
	}

	public List<String> getTripIDList() {
		return tripIDList;
	}
	public List<String> getStopIDList() {
		return stopIDList;
	}

	public ArrayList<StopTime> getStopTimes() {
		return stopTimes;
	}

	public ArrayList<TransitMap> generateFullMap() {
		ArrayList<TransitMap> maps = new ArrayList<>();
		TransitMap currentMap;
		double minLat = 1;
		double maxLat = 0;
		double minLon = 1;
		double maxLon = 0;
		for(String routeID : routeIDList) {
			currentMap = generateMap(routeID);
			if (currentMap.getMinLat() < minLat) {
				minLat = currentMap.getMinLat();
			}
			if (currentMap.getMaxLat() > maxLat) {
				maxLat = currentMap.getMaxLat();
			}
			if (currentMap.getMinLon() < minLon) {
				minLon = currentMap.getMinLon();
			}
			if (currentMap.getMaxLon() > maxLon) {
				maxLon = currentMap.getMaxLon();
			}
			maps.add(currentMap);
		}
		for(TransitMap map : maps) {
			map.setMaxLat(maxLat);
			map.setMaxLon(maxLon);
			map.setMinLat(minLat);
			map.setMinLon(minLon);
		}
		return maps;
	}
}