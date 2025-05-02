package Lab4;

import java.util.*;

/**
 * @author bautchc Ayden Barber
 * @version 1.0
 * @created 04-Oct-2022 10:10:00 AM
 * @license Apache 2.0
 */
public class Route {

	private String agencyId;
	private int routeColor;
	private String routeDescription;
	private String routeId;
	private String routeLongName;
	private String routeShortName;
	private int routeTextColor;
	private int routeType;
	private String routeUrl;

	public Route(String routeId, String agencyId, String routeShortName, String routeLongName, String routeDescription,
				 int routeType, String routeUrl, int routeColor, int routeTextColor, Model model)
	{
		this.routeId = routeId;
		this.agencyId = agencyId;
		this.routeShortName = routeShortName;
		this.routeLongName = routeLongName;
		this.routeDescription = routeDescription;
		this.routeType = routeType;
		this.routeUrl = routeUrl;
		this.routeColor = routeColor;
		this.routeTextColor = routeTextColor;
	}

	public String getRouteDescription() {
		return routeDescription;
	}

	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getRouteLongName() {
		return routeLongName;
	}

	public void setRouteLongName(String routeLongName) {
		this.routeLongName = routeLongName;
	}

	public String getRouteShortName() {
		return routeShortName;
	}

	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public int getRouteColor() {
		return routeColor;
	}

	public void setRouteColor(int routeColor) {
		this.routeColor = routeColor;
	}

	public int getRouteTextColor() {
		return routeTextColor;
	}

	public void setRouteTextColor(int routeTextColor) {
		this.routeTextColor = routeTextColor;
	}

	public int getRouteType() {
		return routeType;
	}

	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}

	public String getRouteUrl() {
		return routeUrl;
	}

	public void setRouteUrl(String routeUrl) {
		this.routeUrl = routeUrl;
	}

	@Override
	public String toString() {
		return routeId + "," + agencyId + "," + routeLongName + "," + routeDescription + ","
				+ routeType + "," + routeUrl + "," + String.format("%06x", routeColor) + "," +
				String.format("%06x", routeTextColor) + "\n";
	}
}