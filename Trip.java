package Lab4;

import java.io.*;
import java.util.*;

/**
 * @author bautchc
 * @version 1.0
 * @created 04-Oct-2022 10:10:04 AM
 * @license Apache 2.0
 */
public class Trip {

	private long blockId;
	private int directionId;
	private String routeId;
	private String serviceId;
	private String shapeId;
	private final ArrayList<StopTime> stopTimes;
	private String tripHeadsign;
	private String tripId;
	private HashMap<String, Stop> stops;


	//Can take empties and nulls need to be careful
	public Trip(String routeId, String serviceId, String tripId, String tripHeadsign,
				int directionId, long blockId, String shapeId, HashMap<String, Stop> stops, ArrayList<StopTime> stopTimes)
	{
		this.routeId = routeId;
		this.serviceId = serviceId;
		this.tripId = tripId;
		this.tripHeadsign = tripHeadsign;
		this.directionId = directionId;
		this.blockId = blockId;
		this.shapeId = shapeId;
		this.stopTimes = stopTimes;
		this.stops = stops;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId){this.serviceId = serviceId;}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getTripHeadsign() {
		return tripHeadsign;
	}

	public void setTripHeadsign(String tripHeadsign) {this.tripHeadsign = tripHeadsign;}

	public int getDirectionId() {
		return directionId;
	}

	public void setDirectionId(int directionId) {this.directionId = directionId;}

	public long getBlockId() {
		return blockId;
	}

	public void setBlockId(long blockId) {this.blockId = blockId;}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {this.shapeId = shapeId;}



	/**
	 * calculates the distance between two stops
	 * @return the distance between the two stops
	 * @author Nathaniel Zawarus
	 */
	public double calculateDistance(){
		ArrayList<Stop> tripStops = new ArrayList<>();
		for (StopTime s: stopTimes) {
			if(s.getTripId().equals(tripId)){
				Stop stop = stops.get(s.getStopId());
				tripStops.add(stop);
			}
		}
		double distance = -1;
		if(!tripStops.isEmpty())
		{
			distance = distanceFormula(tripStops.get(tripStops.size() - 1), tripStops.get(0));
			for(int i = 0; i < tripStops.size() - 2; i++)
			{
				distance = distance + distanceFormula(tripStops.get(i), tripStops.get(i + 1));
			}
		}
		return distance;
	}

	private double distanceFormula(Stop stopOne, Stop stopTwo)
	{
		return Math.sqrt(Math.pow((stopOne.getStopLat() - stopTwo.getStopLat()), 2) +
				Math.pow((stopOne.getStopLon() - stopTwo.getStopLon()), 2));
	}

	/**
	 * calculates the speed of the bus
	 * @return the speed of the bus
	 * @author Nathaniel Zawarus
	 */
	public double calculateSpeed(){
		double distance = calculateDistance();
		ArrayList<StopTime> tripStopTimes = new ArrayList<>();
		for (StopTime s:
			 stopTimes) {
			if(s.getTripId().equals(tripId)){
				tripStopTimes.add(s);
			}
		}
		double departureTime = tripStopTimes.get(0).getDepartureTime();
		double arrivalTime = tripStopTimes.get(tripStopTimes.toArray().length).getArrivalTime();
		double timeElapsed = departureTime-arrivalTime;
		return distance/timeElapsed;
	}

	public ArrayList<StopTime> getStopTimes() {
		return stopTimes;
	}

	@Override
	public String toString() {
		return routeId + "," + serviceId + "," + tripId + "," + tripHeadsign + "," + directionId +
				"," + blockId + "," + shapeId + "\n";
	}
}