package Lab4;

import java.util.*;

/**
 * @author bautchc
 * @version 1.0
 * @created 04-Oct-2022 10:10:01 AM
 * @license Apache 2.0
 */
public class Stop {

	private String stopDesc;
	private String stopId;
	private double stopLat;
	private double stopLon;
	private String stopName;
	private final ArrayList<StopTime> stopTimes;

	//Can take empties and nulls need to be careful
	public Stop(String stopId, String stopName, String stopDesc, double stopLat, double stopLon, Model model){
		this.stopId = stopId;
		this.stopName = stopName;
		this.stopDesc = stopDesc;
		this.stopLat = stopLat;
		this.stopLon = stopLon;
		stopTimes = new ArrayList<>();
	}

	public String getStopId() {
		return stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public String getStopDesc() {
		return stopDesc;
	}

	public void setStopDesc(String stopDesc) {
		this.stopDesc = stopDesc;
	}

	public double getStopLat() {
		return stopLat;
	}

	public void setStopLat(double stopLat) {
		this.stopLat = stopLat;
	}

	public double getStopLon() {
		return stopLon;
	}

	public void setStopLon(double stopLon) {
		this.stopLon = stopLon;
	}

	/**
	 * Moves the stop to a given longitude and latitude
	 * @param latitude the new latitude of the stop
	 * @param longitude the new longitude of the stop
	 * @author Nathaniel Zawarus
	 */
	public void moveStop(double latitude, double longitude){
		stopLat = latitude;
		stopLon = longitude;
	}

	public double getLatitude(){
		return stopLat;
	}

	public double getLongitude(){
		return stopLon;
	}

	public ArrayList<StopTime> getStopTimes() {
		return stopTimes;
	}

	@Override
	public String toString() {
		return stopId + "," + stopDesc + "," + stopLat + "," + stopLon + "," + stopName + "\n";
	}
}