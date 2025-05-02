package Lab4;


import java.util.HashMap;

/**
 * @author bautchc
 * @version 1.0
 * @created 04-Oct-2022 10:10:03 AM
 * @license Apache 2.0
 */
public class StopTime {

	private long arrivalTime;
	private long departureTime;
	private int dropOffType;
	private int pickupType;
	private String stopHeadsign;
	private String stopId;
	private int stopSequence;
	private String tripId;

	public StopTime(String tripID, long arrivalTime, long departureTime, String stopID, int stopSequence,
					String stopHeadsign, int pickupType, int dropOffType, Model model)
	{
		this.tripId = tripID;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.stopId = stopID;
		this.stopSequence = stopSequence;
		this.stopHeadsign = stopHeadsign;
		this.pickupType = pickupType;
		this.dropOffType = dropOffType;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public long getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(long departureTime) {
		this.departureTime = departureTime;
	}

	public String getStopId() {
		return stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
	}

	public int getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(int stopSequence) {
		this.stopSequence = stopSequence;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getStopHeadsign() {
		return stopHeadsign;
	}

	public void setStopHeadsign(String stopHeadsign) {this.stopHeadsign = stopHeadsign;}

	public int getPickupType() {
		return pickupType;
	}

	public void setPickupType(int pickupType) {this.pickupType = pickupType;}

	public int getDropOffType() {
		return dropOffType;
	}

	public void setDropOffType(int dropOffType) {this.dropOffType = dropOffType;}


	@Override
	public String toString() {
		return arrivalTime + "," + departureTime + "," + dropOffType + "," + pickupType + "," + stopHeadsign
				+ "," + stopId + "," + stopSequence + "," + tripId + "\n";
	}
}