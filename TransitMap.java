package Lab4;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.HashMap;

public class TransitMap {
    private Route targetRoute;
    private ArrayList<String> relevantTrips;
    private HashMap<String, Stop> relevantStops;
    private ArrayList<String> relevantStopIDs;
    private HashMap<String, ArrayList<StopTime>> relevantStopTimes;
    private ArrayList<ArrayList<String>> connections;
    private HashMap<ArrayList<String>, Integer> connectionWeights;
    private double maxWeight;
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;

    public TransitMap(Route targetRoute, ArrayList<String> relevantTrips,
                      HashMap<String, Stop> relevantStops, ArrayList<String> relevantStopIDs,
                      HashMap<String, ArrayList<StopTime>> relevantStopTimes, double minLat,
                      double maxLat, double minLon, double maxLon) {
        this.targetRoute = targetRoute;
        this.relevantTrips = relevantTrips;
        this.relevantStops = relevantStops;
        this.relevantStopIDs = relevantStopIDs;
        this.relevantStopTimes = relevantStopTimes;
        maxWeight = 1;
        connectionWeights = new HashMap<>();
        connections = new ArrayList<>();
        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLon = minLon;
        this.maxLon = maxLon;

        calculateConnections();
    }

    private void calculateConnections() {
        ArrayList<String> currentConnection;
        for (String trip : relevantTrips) {
            for (int i = 0; i < relevantStopTimes.get(trip).size() - 1; ++i) {
                if (relevantStopTimes.get(trip).get(i) != null &&
                        relevantStopTimes.get(trip).get(i + 1) != null) {
                    currentConnection = new ArrayList<>();
                    currentConnection.add(relevantStopTimes.get(trip).get(i).getStopId());
                    currentConnection.add(relevantStopTimes.get(trip).get(i + 1).getStopId());
                    if (connectionWeights.containsKey(currentConnection)) {
                        connectionWeights.put(currentConnection,
                                connectionWeights.get(currentConnection) + 1);
                        if(maxWeight < connectionWeights.get(currentConnection)) {
                            maxWeight = connectionWeights.get(currentConnection);
                        }
                    } else {
                        connectionWeights.put(currentConnection, 1);
                        connections.add(currentConnection);
                    }
                }
            }
        }
    }

    public ArrayList<Node> generate(double width, double height) {
        ArrayList<Node> nodeList = new ArrayList<>();
        Line currentLine;
        for(String stopID : relevantStopIDs) {
            nodeList.add(new Circle((relevantStops.get(stopID).getStopLon() - minLon) * width * (1.0 / (maxLon - minLon)),
                    height - (relevantStops.get(stopID).getStopLat() - minLat) * height * (1.0 / (maxLat - minLat)), 3, Color.web(String.format("%06x", targetRoute.getRouteColor()))));
        }
        for (ArrayList<String> connection : connections) {
            currentLine = new Line((relevantStops.get(connection.get(0)).getStopLon() - minLon) *
                    width * (1.0 / (maxLon - minLon)),
                    height - (relevantStops.get(connection.get(0)).getStopLat() - minLat) * height *
                            (1.0 / (maxLat - minLat)),
                    (relevantStops.get(connection.get(1)).getStopLon() - minLon) * width *
                            (1.0 / (maxLon - minLon)),
                    height - (relevantStops.get(connection.get(1)).getStopLat() - minLat) * height *
                            (1.0 / (maxLat - minLat)));
            currentLine.setStrokeWidth(connectionWeights.get(connection) / maxWeight * 4);
            currentLine.setStroke(Color.web(String.format("%06x", targetRoute.getRouteColor())));
            nodeList.add(currentLine);
        }
        return nodeList;
    }

    public double getMinLat() {
        return minLat;
    }

    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    public double getMinLon() {
        return minLon;
    }

    public void setMinLon(double minLon) {
        this.minLon = minLon;
    }

    public double getMaxLon() {
        return maxLon;
    }

    public void setMaxLon(double maxLon) {
        this.maxLon = maxLon;
    }
}
