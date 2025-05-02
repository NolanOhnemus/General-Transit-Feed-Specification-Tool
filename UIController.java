package Lab4;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.util.*;


/**
 * @author bautchc  Ayden Barber
 * @version 1.0
 * @created 04-Oct-2022 10:10:06 AM
 * @license Apache 2.0
 */
public class UIController implements Initializable {

	private Model model = new Model();

	FileChooser fileChooser = new FileChooser();

	HashMap<String, Double> distanceList = new HashMap<>();
	@FXML
	private Button importButton;
	@FXML
	private Button importAllButton;
	@FXML
	private Button exportButton;
	@FXML
	private Button exportAllButton;
	@FXML
	private Label importLabel;
	@FXML
	private TextArea textArea;
	@FXML
	private Button viewStops;
	@FXML
	private Button viewStopTimes;
	@FXML
	private Button viewRoutes;
	@FXML
	private Button viewTrips;
	@FXML
	private Button routesWithStop;
	@FXML
	private Button searchNextTrip;
	@FXML
	private TextField searchBar;
	@FXML
	private Button howManyTrips;
	@FXML
	private Button stopsWithRoute;
	@FXML
	private Button distanceCheck;
	@FXML
	private Button showTripButton;
	@FXML
	private Button showSpeed;
	@FXML
	private TextField stopToUpdate;
	@FXML
	private TextField updatedStopIdLabel;
	@FXML
	private Button editStopButton;
	@FXML
	private TextField updatedStopNameLabel;
	@FXML
	private TextField updatedStopDescriptionLabel;
	@FXML
	private TextField updatedStopLat;
	@FXML
	private TextField updatedStopLong;
	@FXML
	private Button editRouteButton;
	@FXML
	private TextField routeToUpdate;
	@FXML
	private TextField routeId;
	@FXML
	private TextField updatedAgencyId;
	@FXML
	private TextField updatedRouteLongName;
	@FXML
	private TextField updatedRouteDescription;
	@FXML
	private TextField updatedRouteType;
	@FXML
	private TextField updatedRouteURL;
	@FXML
	private TextField updatedRouteTextColor;
	@FXML
	private TextField updatedRouteColor;
	@FXML
	private Button editTripButton;
	@FXML
	private TextField tripToUpdate;
	@FXML
	private TextField updatedRouteId;
	@FXML
	private TextField updatedServiceId;
	@FXML
	private TextField updatedTripId;
	@FXML
	private TextField updatedTripHeadsign;
	@FXML
	private TextField updatedDirectionId;
	@FXML
	private TextField updatedBlockId;
	@FXML
	private TextField updatedShapeId;
	@FXML
	private Button stopTimeUpdateButton;
	@FXML
	private TextField stopSequenceToUpdate;
	@FXML
	private TextField updatedArrivaltime;
	@FXML
	private TextField updatedDepartureTime;
	@FXML
	private TextField updatedDropoffType;
	@FXML
	private TextField updatedPickupType;
	@FXML
	private TextField updatedStopHeadsign;
	@FXML
	private TextField updatedStopId;
	@FXML
	private TextField updatedStopSequence;
	@FXML
	private TextField updatedTripId2;





	private boolean stopsImported;
	private boolean stopTimesImported;
	private boolean routesImported;
	private boolean tripsImported;
	private String stopIdToUpdate;
	private String routeIdToUpdate;
	private String tripIdToUpdate;
	private String stopNumberToUpdate;

	private boolean allImported() {
		return stopsImported && stopTimesImported && routesImported && tripsImported;
	}
	@FXML
	private Button viewMap;
	@FXML
	private Pane mapHolder;

	public UIController(){
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setModel(Model model) {
		this.model = model;
	}

	@FXML
	private void editStop() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UPDATESTOPWINDOW.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setTitle("Update Stop Attribute(s)");
		stage.setScene(new Scene(root1));
		stage.show();
	}
	@FXML
	private void importStopInfo() {
		stopIdToUpdate = stopToUpdate.getText();
		Stop updateStop = model.searchStop(stopIdToUpdate);

		updatedStopIdLabel.setText(updateStop.getStopId());
		updatedStopNameLabel.setText(updateStop.getStopName());
		updatedStopDescriptionLabel.setText(updateStop.getStopDesc());
		updatedStopLat.setText(String.valueOf(updateStop.getStopLat()));
		updatedStopLong.setText(String.valueOf(updateStop.getStopLon()));
	}
	@FXML
	private void updateStopInfo() {
		HashMap<String, Stop> stops = model.getStops();
		stops.get(stopIdToUpdate).setStopId(updatedStopIdLabel.getText());
		stops.get(stopIdToUpdate).setStopName(updatedStopNameLabel.getText());
		stops.get(stopIdToUpdate).setStopDesc(updatedStopDescriptionLabel.getText());
		stops.get(stopIdToUpdate).setStopLat(Double.parseDouble(updatedStopLat.getText()));
		stops.get(stopIdToUpdate).setStopLon(Double.parseDouble(updatedStopLong.getText()));
		Stage stage = (Stage) stopToUpdate.getScene().getWindow();
		stage.close();
		System.out.println(model.searchStop(stopIdToUpdate).toString());
	}

	@FXML
	private void editRoute() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UPDATEROUTEWINDOW.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setTitle("Update Route Attribute(s)");
		stage.setScene(new Scene(root1));
		stage.show();
	}
	@FXML
	private void importRouteInfo() {
		routeIdToUpdate = routeToUpdate.getText();
		Route updateRoute = model.searchRoute(routeIdToUpdate);

		routeId.setText(updateRoute.getRouteId());
		updatedAgencyId.setText(updateRoute.getAgencyId());
		updatedRouteLongName.setText(updateRoute.getRouteLongName());
		updatedRouteDescription.setText(String.valueOf(updateRoute.getRouteDescription()));
		updatedRouteType.setText(String.valueOf(updateRoute.getRouteType()));
		updatedRouteURL.setText(updateRoute.getRouteUrl());
		updatedRouteTextColor.setText(String.valueOf(updateRoute.getRouteTextColor()));
		updatedRouteColor.setText(String.valueOf(updateRoute.getRouteColor()));
	}
	@FXML
	private void updateRouteInfo() {
		HashMap<String, Route> routes = model.getRoutes();
		routes.get(routeIdToUpdate).setRouteId(routeId.getText());
		routes.get(routeIdToUpdate).setAgencyId(updatedAgencyId.getText());
		routes.get(routeIdToUpdate).setRouteLongName(updatedRouteLongName.getText());
		routes.get(routeIdToUpdate).setRouteDescription(updatedRouteDescription.getText());
		routes.get(routeIdToUpdate).setRouteType(Integer.parseInt(updatedRouteType.getText()));
		routes.get(routeIdToUpdate).setRouteUrl(updatedRouteURL.getText());
		routes.get(routeIdToUpdate).setRouteTextColor(Integer.parseInt(updatedRouteTextColor.getText()));
		routes.get(routeIdToUpdate).setRouteColor(Integer.parseInt(updatedRouteColor.getText()));
		Stage stage = (Stage) routeToUpdate.getScene().getWindow();
		stage.close();
		System.out.println(model.searchRoute(routeIdToUpdate).toString());
	}
	@FXML
	private void editTrip() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UPDATETRIPWINDOW.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setTitle("Update Trip Attribute(s)");
		stage.setScene(new Scene(root1));
		stage.show();
	}
	@FXML
	private void importTripInfo() {
		tripIdToUpdate = tripToUpdate.getText();
		Trip updateTrip = model.searchTrip(tripIdToUpdate);

		updatedRouteId.setText(updateTrip.getRouteId());
		updatedServiceId.setText(updateTrip.getServiceId());
		updatedTripId.setText(updateTrip.getTripId());
		updatedTripHeadsign.setText(updateTrip.getTripHeadsign());
		updatedDirectionId.setText(String.valueOf(updateTrip.getDirectionId()));
		updatedBlockId.setText(String.valueOf(updateTrip.getBlockId()));
		updatedShapeId.setText(updateTrip.getShapeId());
	}
	@FXML
	private void updateTripInfo() {
		HashMap<String, Trip> trips = model.getTrips();
		trips.get(tripIdToUpdate).setRouteId(updatedRouteId.getText());
		trips.get(tripIdToUpdate).setServiceId(updatedServiceId.getText());
		trips.get(tripIdToUpdate).setTripId(updatedTripId.getText());
		trips.get(tripIdToUpdate).setTripHeadsign(updatedTripHeadsign.getText());
		trips.get(tripIdToUpdate).setDirectionId(Integer.parseInt(updatedDirectionId.getText()));
		trips.get(tripIdToUpdate).setBlockId(Long.parseLong(updatedBlockId.getText()));
		trips.get(tripIdToUpdate).setShapeId(updatedShapeId.getText());
		Stage stage = (Stage) tripToUpdate.getScene().getWindow();
		stage.close();
		System.out.println(model.searchTrip(tripIdToUpdate).toString());
	}

	@FXML
	private void editStopTime() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UPDATESTOPTIMEWINDOW.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setTitle("Update Stop Time Attribute(s)");
		stage.setScene(new Scene(root1));
		stage.show();
	}
	@FXML
	private void importStopTimeInfo() {
		stopNumberToUpdate = stopSequenceToUpdate.getText();
		StopTime updateStopTime = model.searchStopSequence(Integer.parseInt(stopSequenceToUpdate.getText()) - 1);

		updatedArrivaltime.setText(String.valueOf(updateStopTime.getArrivalTime()));
		updatedDepartureTime.setText(String.valueOf(updateStopTime.getDepartureTime()));
		updatedDropoffType.setText(String.valueOf(updateStopTime.getDropOffType()));
		updatedPickupType.setText(String.valueOf(updateStopTime.getPickupType()));
		updatedStopHeadsign.setText(updateStopTime.getStopHeadsign());
		updatedStopId.setText(updateStopTime.getStopId());
		updatedStopSequence.setText(String.valueOf(updateStopTime.getStopSequence()));
		updatedTripId2.setText(String.valueOf(updateStopTime.getTripId()));
	}
	@FXML
	private void updateStopTimeInfo() {
		ArrayList<StopTime> stopTimes = model.getStopTimes();
		stopTimes.get(Integer.parseInt(stopNumberToUpdate)).setArrivalTime(Integer.parseInt(updatedArrivaltime.getText()));
		stopTimes.get(Integer.parseInt(stopNumberToUpdate)).setDepartureTime(Integer.parseInt(updatedDepartureTime.getText()));
		stopTimes.get(Integer.parseInt(stopNumberToUpdate)).setDropOffType(Integer.parseInt(updatedDropoffType.getText()));
		stopTimes.get(Integer.parseInt(stopNumberToUpdate)).setPickupType(Integer.parseInt(updatedPickupType.getText()));
		stopTimes.get(Integer.parseInt(stopNumberToUpdate)).setStopHeadsign((updatedStopHeadsign.getText()));
		stopTimes.get(Integer.parseInt(stopNumberToUpdate)).setStopId((updatedArrivaltime.getText()));
		stopTimes.get(Integer.parseInt(stopNumberToUpdate)).setTripId((updatedArrivaltime.getText()));
		Stage stage = (Stage) stopSequenceToUpdate.getScene().getWindow();
		stage.close();
		System.out.println(model.searchStopSequence(Integer.parseInt(updatedStopSequence.getText())).toString());
	}

	/**
	 * exports the data structures to text files
	 */
	@FXML
	private void exportFiles() throws IOException {
		importButton.setDisable(true);
		importAllButton.setDisable(true);
		exportAllButton.setDisable(true);
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save");
		chooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("txt files", "*.txt"));
		File savedFile =  chooser.showSaveDialog(new Stage());
		if(savedFile != null)
		{
			FileWriter fileWriter = new FileWriter(savedFile);
			if (importLabel.getText().equalsIgnoreCase("imported: stops.txt")) {
				viewStops.setDisable(false);
				fileWriter.write("stop_id,stop_name,stop_desc,stop_lat,stop_lon \n");
				StringBuilder finalStops = new StringBuilder();
				HashMap<String, Stop> stops = model.getStops();
				for(var entry: stops.entrySet())
				{
					String entryString = entry.toString();
					String finalString = entryString.split("=")[1];
					finalStops.append(finalString);
				}
				fileWriter.write(finalStops.toString());
				fileWriter.close();
			} else if (importLabel.getText().equalsIgnoreCase("imported: trips.txt")) {
				viewTrips.setDisable(false);
				fileWriter.write("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id \n");
				StringBuilder finalTrips = new StringBuilder();
				HashMap<String, Trip> stops = model.getTrips();
				for(var entry: stops.entrySet())
				{
					String entryString = entry.toString();
					String finalString = entryString.split("=")[1];
					finalTrips.append(finalString);
				}
				fileWriter.write(finalTrips.toString());
				fileWriter.close();
			} else if (importLabel.getText().equalsIgnoreCase("imported: routes.txt")) {
				viewRoutes.setDisable(false);
				fileWriter.write("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type," +
						"route_url,route_color,route_text_color \n");
				StringBuilder finalRoutes = new StringBuilder();
				HashMap<String, Route> routes = model.getRoutes();
				for(var entry: routes.entrySet())
				{
					String entryString = entry.toString();
					String finalString = entryString.split("=")[1];
					finalRoutes.append(finalString);
				}
				fileWriter.write(finalRoutes.toString());
				fileWriter.close();
			} else if (importLabel.getText().equalsIgnoreCase("imported: stop_times.txt")) {
				viewStopTimes.setDisable(false);
				fileWriter.write("trip_id,arrival_time,departure_time,stop_id,stop_sequence," +
						"stop_headsign,pickup_type,drop_off_type \n");
				StringBuilder finalStopTimes = new StringBuilder();
				ArrayList<StopTime> stopTimes = model.getStopTimes();
				for(StopTime stopTime: stopTimes)
				{
					finalStopTimes.append(stopTime.toString());
				}
				fileWriter.write(finalStopTimes.toString());
				fileWriter.close();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error in Exporting");
				alert.setContentText("The file you attempted to export is not an acceptable file");
				alert.show();
			}
		}
		importButton.setDisable(false);
		importAllButton.setDisable(false);
		exportAllButton.setDisable(false);
	}

	@FXML
	private void exportAll() throws IOException
	{
		importButton.setDisable(true);
		importAllButton.setDisable(true);
		exportButton.setDisable(true);
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Save all files to");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File directory = directoryChooser.showDialog(new Stage());
		if(directory != null)
		{
			File stops = new File(directory.toPath() + "\\stops.txt");
			File trips = new File(directory.toPath() + "\\trips.txt");
			File stopTimes = new File(directory.toPath() + "\\stop_times.txt");
			File routes = new File(directory.toPath() + "\\routes.txt");
			try(FileWriter stopWriter = new FileWriter(stops);
				FileWriter tripsWriter = new FileWriter(trips);
				FileWriter routesWriter = new FileWriter(routes);
				FileWriter stopTimesWriter = new FileWriter(stopTimes))
			{
				viewStops.setDisable(false);
				stopWriter.write("stop_id,stop_name,stop_desc,stop_lat,stop_lon \n");
				StringBuilder finalStops = new StringBuilder();
				HashMap<String, Stop> stopMap = model.getStops();
				for(var entry: stopMap.entrySet())
				{
					String entryString = entry.toString();
					String finalString = entryString.split("=")[1];
					finalStops.append(finalString);
				}
				stopWriter.write(finalStops.toString());
				stopWriter.close();

				viewTrips.setDisable(false);
				tripsWriter.write("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id \n");
				StringBuilder finalTrips = new StringBuilder();
				HashMap<String, Trip> tripMap = model.getTrips();
				for(var entry: tripMap.entrySet())
				{
					String entryString = entry.toString();
					String finalString = entryString.split("=")[1];
					finalTrips.append(finalString);
				}
				tripsWriter.write(finalTrips.toString());
				tripsWriter.close();

				viewRoutes.setDisable(false);
				routesWriter.write("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type," +
						"route_url,route_color,route_text_color \n");
				StringBuilder finalRoutes = new StringBuilder();
				HashMap<String, Route> routesMap = model.getRoutes();
				for(var entry: routesMap.entrySet())
				{
					String entryString = entry.toString();
					String finalString = entryString.split("=")[1];
					finalRoutes.append(finalString);
				}
				routesWriter.write(finalRoutes.toString());
				routesWriter.close();

				viewStopTimes.setDisable(false);
				stopTimesWriter.write("trip_id,arrival_time,departure_time,stop_id,stop_sequence," +
						"stop_headsign,pickup_type,drop_off_type \n");
				StringBuilder finalStopTimes = new StringBuilder();
				ArrayList<StopTime> stopTimesList = model.getStopTimes();
				for(StopTime stopTime: stopTimesList)
				{
					finalStopTimes.append(stopTime.toString());
				}
				stopTimesWriter.write(finalStopTimes.toString());
				stopTimesWriter.close();
			}
		}
		importButton.setDisable(false);
		importAllButton.setDisable(false);
		exportButton.setDisable(false);
	}

	@FXML
	private void importAll(){
		viewStops.setDisable(true);
		viewRoutes.setDisable(true);
		viewTrips.setDisable(true);
		viewStopTimes.setDisable(true);
		exportAllButton.setDisable(false);
		exportButton.setDisable(false);
		routesWithStop.setDisable(false);
		searchNextTrip.setDisable(false);
		howManyTrips.setDisable(false);
		editStopButton.setDisable(false);
		editRouteButton.setDisable(false);
		editTripButton.setDisable(false);
		stopsWithRoute.setDisable(false);
		stopTimeUpdateButton.setDisable(false);
		viewMap.setDisable(false);
		if (textArea.getText() != null) {
			textArea.clear();
		}
		exportButton.setDisable(true);
		importButton.setDisable(true);
		exportAllButton.setDisable(true);
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Import GTFS Files");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File directory = directoryChooser.showDialog(new Stage());
		boolean stopCheck = false;
		boolean tripCheck = false;
		boolean routesCheck = false;
		boolean stopTimeCheck = false;
		if(directory != null){
			File[] dirList = directory.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".txt");
				}
			});
			if(dirList != null)
			{
				for(File file : dirList)
				{
					String fileName = file.getName();
					switch (fileName) {
						case "stops.txt" -> {
							model.getStops().clear();
							stopCheck = model.addStops(file);
						}
						case "routes.txt" -> {
							model.getRoutes().clear();
							routesCheck = model.addRoutes(file);
						}
						case "trips.txt" -> {
							model.getTrips().clear();
							distanceList.clear();
							tripCheck = model.addTrips(file);
						}
						case "stop_times.txt" -> {
							model.getStopTimes().clear();
							stopTimeCheck = model.addStopTimes(file);
						}
					}
				}
			}
			if(!stopCheck || !routesCheck || !tripCheck || !stopTimeCheck)
			{
				model.getStops().clear();
				model.getRoutes().clear();
				model.getTrips().clear();
				model.getStopTimes().clear();
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Failed to import");
				alert.setContentText("Failed to import one or more files");
				alert.show();
			}
			else
			{
				viewStops();
				viewStops.setDisable(false);
				viewRoutes.setDisable(false);
				viewTrips.setDisable(false);
				viewStopTimes.setDisable(false);
				importLabel.setText("imported: all files");
			}
		}
		exportButton.setDisable(false);
		exportAllButton.setDisable(false);
		importButton.setDisable(false);
	}

	/**
	 * imports files to be read
	 */
	@FXML
	private void importFiles(){
		if (textArea.getText() != null) {
			textArea.clear();
		}
		exportButton.setDisable(true);
		exportAllButton.setDisable(true);
		importAllButton.setDisable(true);
		fileChooser.setTitle("Import GTFS files");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		if (selectedFile != null) {
			importLabel.setDisable(false);
			importLabel.setText("imported: " + selectedFile.getName());

			if (selectedFile.getName().equalsIgnoreCase("stops.txt")) {
				model.getStops().clear();
				stopsImported = true;
				if (allImported()) {
					exportAllButton.setDisable(false);
					exportButton.setDisable(false);
					routesWithStop.setDisable(false);
					searchNextTrip.setDisable(false);
					howManyTrips.setDisable(false);
					stopsWithRoute.setDisable(false);
				}
				model.addStops(selectedFile);
				viewStops();
				viewStops.setDisable(false);
				editStopButton.setDisable(false);

			} else if (selectedFile.getName().equalsIgnoreCase("trips.txt")) {
				model.getTrips().clear();
				distanceList.clear();
				tripsImported = true;
				editTripButton.setDisable(false);
				if (allImported()) {
					exportAllButton.setDisable(false);
					exportButton.setDisable(false);
					routesWithStop.setDisable(false);
					searchNextTrip.setDisable(false);
					howManyTrips.setDisable(false);
					stopsWithRoute.setDisable(false);
				}
				model.addTrips(selectedFile);
				viewTrips();
				viewTrips.setDisable(false);
			} else if (selectedFile.getName().equalsIgnoreCase("routes.txt")) {
				model.getRoutes().clear();
				routesImported = true;
				editRouteButton.setDisable(false);
				if (allImported()) {
					exportAllButton.setDisable(false);
					exportButton.setDisable(false);
					routesWithStop.setDisable(false);
					searchNextTrip.setDisable(false);
					howManyTrips.setDisable(false);
					editRouteButton.setDisable(false);
					stopsWithRoute.setDisable(false);
				}
				model.addRoutes(selectedFile);
				viewRoutes();
				viewRoutes.setDisable(false);
			} else if (selectedFile.getName().equalsIgnoreCase("stop_times.txt")) {
				model.getStopTimes().clear();
				stopTimesImported = true;
				stopTimeUpdateButton.setDisable(false);
				if (allImported()) {
					exportAllButton.setDisable(false);
					exportButton.setDisable(false);
					routesWithStop.setDisable(false);
					searchNextTrip.setDisable(false);
					howManyTrips.setDisable(false);
					stopsWithRoute.setDisable(false);
				}
				model.addStopTimes(selectedFile);
				viewStopTimes();
				viewStopTimes.setDisable(false);
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Incorrect File Imported");
				alert.setContentText("The file selected does not match the correct formatting");
				alert.show();
			}
		}
		HashMap<String, Stop> stops = model.getStops();
		HashMap<String, Trip> trips = model.getTrips();
		HashMap<String, Route> routes = model.getRoutes();
		ArrayList<StopTime> stopTimes = model.getStopTimes();
		if(!stops.isEmpty() && !trips.isEmpty() && !routes.isEmpty() && !stopTimes.isEmpty())
		{
			exportAllButton.setDisable(false);
			exportButton.setDisable(false);
		}
		importAllButton.setDisable(false);
	}

	@FXML
	private void viewStops() {
		distanceCheck.setVisible(false);
		showTripButton.setVisible(false);
		showSpeed.setVisible(false);
		textArea.setVisible(true);
		mapHolder.setVisible(false);
		StringBuilder textString = new StringBuilder();
		HashMap<String, Stop> stops = model.getStops();
		for (int i = stops.size() - 1; i >= 0; i--) {
			Stop currentStop = stops.get(model.getStopIDList().get(i));
			textString.append(currentStop.toString());
		}
		textArea.setText(textString.toString());
	}

	@FXML
	private void viewStopTimes() {
		distanceCheck.setVisible(false);
		showTripButton.setVisible(false);
		showSpeed.setVisible(false);
		textArea.setVisible(true);
		mapHolder.setVisible(false);
		ArrayList<StopTime> stopTimes = model.getStopTimes();
		StringBuilder textString = new StringBuilder();
		for (int i = 100; i >= 0; i--) {
			StopTime currentStopTime = stopTimes.get(i);
			textString.append(currentStopTime.toString());
		}
		textArea.setText(textString.toString());
	}

	@FXML
	private void viewRoutes() {
		distanceCheck.setVisible(false);
		showTripButton.setVisible(false);
		showSpeed.setVisible(false);
		textArea.setVisible(true);
		mapHolder.setVisible(false);
		StringBuilder textString = new StringBuilder();
		HashMap<String, Route> routes = model.getRoutes();

		for (int i = routes.size() - 1; i >= 0; i--) {
			Route currentRoute = routes.get(model.getRouteIDList().get(i));
			textString.append(currentRoute.toString());
		}
		textArea.setText(textString.toString());
	}

	@FXML
	private void viewTrips() {
		textArea.setVisible(true);
		mapHolder.setVisible(false);
		if(!model.getTrips().isEmpty() && !model.getRoutes().isEmpty() &&
				!model.getStopTimes().isEmpty() && !model.getStops().isEmpty())
		{
			distanceCheck.setVisible(true);
			showTripButton.setVisible(true);
			showSpeed.setVisible(true);
		}
		StringBuilder textString = new StringBuilder();
		HashMap<String, Trip> trips = model.getTrips();
		for(int i = trips.size() - 1; i >= 0; i--) {
			Trip currentTrip = trips.get(model.getTripIDList().get(i));
			textString.append(currentTrip.toString());
		}
		textArea.setText(textString.toString());
	}

	@FXML
	private void showDistance()
	{
		HashMap<String, Trip> trips = model.getTrips();
		if(!trips.isEmpty())
		{
			StringBuilder builder = new StringBuilder();
			for(var trip: trips.entrySet())
			{

				String routeID = trip.getValue().getRouteId();
				if(!distanceList.containsKey(routeID))
				{
					builder.append("Distance of ");
					builder.append(trip.getKey());
					builder.append(": ");
					Double distance = trip.getValue().calculateDistance();
					distanceList.put(routeID, distance);
					builder.append(distance);
					builder.append("\n");
				}
				else
				{
					builder.append("Distance of ");
					builder.append(trip.getKey());
					builder.append(": ");
					Double distance = distanceList.get(routeID);
					builder.append(distance);
					builder.append("\n");
				}
			}
			textArea.setText(builder.toString());
			showSpeed.setDisable(false);
		}
	}

	@FXML
	private void showSpeed()
	{
		HashMap<String, Trip> trips = model.getTrips();
		ArrayList<StopTime> stopTimes = model.getStopTimes();
		ArrayList<ArrayList<StopTime>> listOfList = listOfListForStopTime(stopTimes);
		if(!trips.isEmpty() && !stopTimes.isEmpty())
		{
			StringBuilder builder = new StringBuilder();
			for(ArrayList<StopTime> listOfStopTimes : listOfList)
			{
				long start = listOfStopTimes.get(0).getDepartureTime();
				long end = listOfStopTimes.get(listOfStopTimes.size() - 1).getArrivalTime();
				double msTime = end - start;
				double time = (msTime / 1000) / 60 / 60;
				String tripID = listOfStopTimes.get(0).getTripId();
				String routeID = trips.get(tripID).getRouteId();
				double distance = distanceList.get(routeID);
				builder.append("Average speed of ");
				builder.append(tripID);
				builder.append(": ");
				double speed = distance/time;
				builder.append(speed);
				builder.append(" units an hour\n");
			}
			textArea.setText(builder.toString());
			showSpeed.setDisable(false);
		}
	}

	private ArrayList<ArrayList<StopTime>> listOfListForStopTime(ArrayList<StopTime> stopTimes)
	{
		ArrayList<ArrayList<StopTime>> finalReturnable = new ArrayList<>();
		ArrayList<StopTime> intermediateList = new ArrayList<>();
		StopTime previous = null;
		boolean firstCheck = false;
		for(StopTime stopTime: stopTimes)
		{
			if(!firstCheck)
			{
				previous = stopTimes.get(0);
				intermediateList.add(previous);
				firstCheck = true;
			}
			else
			{
				if (!previous.getTripId().equals(stopTime.getTripId())) {
					finalReturnable.add(intermediateList);
					intermediateList = new ArrayList<>();
				}
				intermediateList.add(stopTime);
				previous = stopTime;
			}
		}
		return finalReturnable;
	}

	public void nextTripForStop(){
		String idToSearch = searchBar.getText();
		Trip t = null;
		Set<String> keys = model.getStops().keySet();
		boolean validKey = false;
		for (String k:
			 keys) {
			if (idToSearch.equals(k)) {
				validKey = true;
				break;
			}
		}
		if(!idToSearch.equals("")&&validKey) {
			t = model.nextTripForStop(idToSearch);
			textArea.clear();
			textArea.setText(t.getRouteId() + "," + t.getServiceId() + "," + t.getTripId() + "," + t.getTripHeadsign()
					+ "," + t.getDirectionId() + "," + t.getBlockId() + "," + t.getShapeId());
		} else if(!validKey){
			Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid stop ID", ButtonType.OK);
			alert.showAndWait();
		}
	}

	public void routesForStop(){
		String idToSearch = searchBar.getText();
		Set<String> matchingRoutes = model.routeForStop(idToSearch).keySet();
		Set<String> keys = model.getStops().keySet();
		boolean validKey = false;
		for (String k:
				keys) {
			if (idToSearch.equals(k)) {
				validKey = true;
				break;
			}
		}
		textArea.clear();
		if(matchingRoutes.size()>0&&validKey) {
			StringBuilder validRoutes = new StringBuilder();
			for (String routeId :
					matchingRoutes) {
				Route route = model.getRoutes().get(routeId);
				validRoutes.append(route.getRouteId()+","+route.getAgencyId()+","+route.getRouteShortName()+","+
						route.getRouteLongName()+","+route.getRouteDescription()+","+route.getRouteType()+","+
						route.getRouteUrl()+","+route.getRouteColor()+","+route.getRouteTextColor()+"\n");
			}
			textArea.setText(validRoutes.toString());
		}else if(!validKey){
			Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid stop ID", ButtonType.OK);
			alert.showAndWait();
		}
	}

	public void showMap(){
		ArrayList<Node> nodeList;
		mapHolder.getChildren().clear();
		if(!searchBar.getText().equals("")) {
			mapHolder.getChildren().addAll(model.generateMap(searchBar.getText())
					.generate(mapHolder.getWidth(), mapHolder.getHeight()));
		} else {
			for (TransitMap map : model.generateFullMap()) {
				mapHolder.getChildren()
						.addAll(map.generate(mapHolder.getWidth(), mapHolder.getHeight()));
			}

		}
		textArea.setVisible(false);
		mapHolder.setVisible(true);
	}

	public void stopsForRoute(){
		String idToSearch = searchBar.getText();
		Set<String> keys = model.getRoutes().keySet();
		Boolean validKey = false;
		for(String k: keys){
			if (k.equals(idToSearch)){
				validKey = true;
			}
		}
		if(validKey) {
			textArea.clear();
			StringBuilder sb = new StringBuilder();
			Set<String> stops = model.stopsForRoute(idToSearch).keySet();
			for (String stopId: stops){
				Stop s = model.getStops().get(stopId);
				sb.append(s.getStopId()+","+s.getStopName()+","+s.getStopDesc()+","+s.getStopLat()+
						","+s.getStopLon()+"\n");
			}
			textArea.setText(sb.toString());
		}
	}

	public void numberOfTrips(){
		Set<String> stopIds = model.getStops().keySet();
		ArrayList<StopTime> stopTimes = model.getStopTimes();
		textArea.clear();
		StringBuilder stringBuilder = new StringBuilder();
		HashMap<String, Integer> numberMap = makeMapForNumTrips(stopTimes);
		for(String id: stopIds){
			int numberOfTrips = numberMap.get(id);
			stringBuilder.append(id).append(": ").append(numberOfTrips).append("\n");
		}
		textArea.setText(stringBuilder.toString());
	}

	private HashMap<String, Integer> makeMapForNumTrips(ArrayList<StopTime> stopTimes)
	{
		HashMap<String, Integer> map = new HashMap<>();
		for(StopTime stopTime: stopTimes)
		{
			String stopID = stopTime.getStopId();
			if(!map.containsKey(stopID))
			{
				map.put(stopID, 1);
			}
			else
			{
				int value = map.get(stopID) + 1;
				map.replace(stopID, value);
			}
		}
		return map;
	}
}