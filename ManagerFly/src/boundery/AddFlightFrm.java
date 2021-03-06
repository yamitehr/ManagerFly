package boundery;


import static util.LoadPane.LoadFXML;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import control.FlightsLogic;
import control.Getters;
import entity.AirPlane;
import entity.AirPort;
import entity.FlightSeat;
import exceptions.InvalidInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.Alerts;
import util.Consts;
import util.InputValidetions;

public class AddFlightFrm {
	//flight
	@FXML
	private TextField flightNum;
	@FXML
	private DatePicker departureDate;
	@FXML
	private DatePicker landingDate;
	@FXML 
	private ComboBox<Integer> depHour;
	@FXML 
	private ComboBox<Integer> depMinute;
	@FXML 
	private ComboBox<Integer> arrHour;
	@FXML 
	private ComboBox<Integer> arrMinute;
	//AirPorts
	@FXML
	private ComboBox<AirPort> depAirports;
	@FXML
	private ComboBox<AirPort> arrAirports;
	//AirPlanes
	@FXML
	private ComboBox<AirPlane> airPlanes;
	//Flight
	@FXML
	private Button addFlight;
	@FXML
	private Button redAddPlane;
	@FXML
	private Button redAddPort;
	@FXML
	private Alert a;
	
	public static Stage primaryStagePlane;
	public static Stage primaryStagePort;
	
	private FlightsLogic flightsInstance;
	
	@FXML
    public void initialize() {
		init();
    }
	
	private void init() {
		
		initFeilds();
		initAirports();
		initAirplanes();
		
	}
	@FXML
	private void initAirplanes() {
		ObservableList<AirPlane> airplanes = FXCollections.observableArrayList(Getters.getInstance().getAirplanes());	
		airPlanes.setItems(FXCollections.observableArrayList(airplanes));
	}
	
	
	@FXML
	private void initFeilds() {
		ArrayList<Integer> hoursList  = new ArrayList<>();
		ArrayList<Integer> minuteList  = new ArrayList<>();
		for(int i=0;i<24;i++) 
		{
			hoursList.add(i);
		}
		depHour.setItems(FXCollections.observableArrayList(hoursList));
		arrHour.setItems(FXCollections.observableArrayList(hoursList));
		for(int i=0;i<60;i++) 
		{
			minuteList.add(i);
		}
		depMinute.setItems(FXCollections.observableArrayList(minuteList));
		arrMinute.setItems(FXCollections.observableArrayList(minuteList));
		
		//a flight must be in two months from today at least
		departureDate.setDayCellFactory(picker -> new DateCell() {
			 public void updateItem(LocalDate date, boolean empty) {
	                super.updateItem(date, empty);
	                LocalDate twoMnthFromToday = LocalDate.now().plusMonths(2);
	                setDisable(empty || date.compareTo(twoMnthFromToday) < 0 );
	        }
		});
		
		landingDate.setDayCellFactory(picker -> new DateCell() {
			 public void updateItem(LocalDate date, boolean empty) {
	                super.updateItem(date, empty);
	                LocalDate twoMnthFromToday = LocalDate.now().plusMonths(2);
	                setDisable(empty || date.compareTo(twoMnthFromToday) < 0 );
	        }
		});
	}
	
	@FXML
	private void initAirports(){
		
		ObservableList<AirPort> airports = FXCollections.observableArrayList(Getters.getInstance().getAirports());	
		depAirports.setItems(FXCollections.observableArrayList(airports));
		arrAirports.setItems(FXCollections.observableArrayList(airports));
	}
	
	
	@FXML
	private void redirectAddPlane() throws IOException {
		
		primaryStagePlane = new Stage();
		FXMLLoader loader = new FXMLLoader(
				  getClass().getResource(
				    "AirPlanesFrm.fxml"
				  )
				);
		Parent root = loader.load();
		AirplanesFrm controller = 
			    loader.<AirplanesFrm>getController();
		
		controller.inEditAddFlight();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());	
		
		
		primaryStagePlane.setScene(scene);
		primaryStagePlane.setTitle("Add New Plane");
		primaryStagePlane.setResizable(false);
		primaryStagePlane.initStyle(StageStyle.DECORATED);
		primaryStagePlane.setWidth(900);
		primaryStagePlane.setHeight(650);
		primaryStagePlane.show();
		
	}
	
	static void closeWindow() {
		if(primaryStagePlane != null) {
			primaryStagePlane.close();
		}
		
		if(primaryStagePort != null) {
			primaryStagePort.close();
		}
	}
	
	@FXML
	private void redirectAddAirport() throws IOException {
		
		primaryStagePort = new Stage();
		
		FXMLLoader loader = new FXMLLoader(
				  getClass().getResource(
				    "AirPortsFrm.fxml"
				  )
				);
		Parent root = loader.load();
		AirportsFrm controller = 
			    loader.<AirportsFrm>getController();
		controller.inEditAddFlight();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());	
		primaryStagePort.setScene(scene);
		primaryStagePort.setTitle("Add New Airport");
		primaryStagePort.setResizable(false);
		primaryStagePort.initStyle(StageStyle.DECORATED);
		primaryStagePort.setWidth(900);
		primaryStagePort.setHeight(650);
		primaryStagePort.show();
	}
	
	
	
	@FXML
	private void addNewFlight() {
		try {
			flightsInstance = FlightsLogic.getInstance();
			String flightNumber = flightNum.getText();
			if(flightNumber.isEmpty()) {
				throw new InvalidInputException("Please fill Flight Number");
			}
			if(departureDate.getValue() == null) {
				throw new InvalidInputException("Please select Depature Date");
			}
			if(landingDate.getValue() == null) {
				throw new InvalidInputException("Please select Landing Date");
			}
			if(departureDate.getValue().isAfter(landingDate.getValue())) {
				throw new InvalidInputException("landing date should be after the departure date");
			}
			if(depHour.getValue() == null) {
				throw new InvalidInputException("Please select Depature Hour");
			}
			if(depMinute.getValue() == null) {
				throw new InvalidInputException("Please select Depature Minute");
			}
			if(arrHour.getValue() == null) {
				throw new InvalidInputException("Please select Landing Hour");
			}
			if(arrMinute.getValue() == null) {
				throw new InvalidInputException("Please select Landing Minute");
			}
			if(depAirports.getValue() == null) {
				throw new InvalidInputException("Please select Departue Airport");
			}
			if(arrAirports.getValue() == null) {
				throw new InvalidInputException("Please select Landing Airpoert");
			}
			if(airPlanes.getSelectionModel().getSelectedItem() == null) {
				throw new InvalidInputException("Please select an Airplane");
			}
			
			LocalDateTime depatureDateTime = LocalDateTime.of(departureDate.getValue().getYear(),
					departureDate.getValue().getMonth(),
					departureDate.getValue().getDayOfMonth(),
					depHour.getValue(),
                    depMinute.getValue());
			
			LocalDateTime landingDateTime = LocalDateTime.of(landingDate.getValue().getYear(),
					landingDate.getValue().getMonth(),
					landingDate.getValue().getDayOfMonth(),
					arrHour.getValue(),
                    arrMinute.getValue());
			if(depAirports.getValue().equals(arrAirports.getValue())) {
				throw new InvalidInputException("Departure and Destination airports cannot be the same");
			}
			
			if(flightsInstance.addFlight(flightNumber, depatureDateTime, landingDateTime, depAirports.getValue(),arrAirports.getValue(), airPlanes.getSelectionModel().getSelectedItem(), null, null,"on time")) {
				a = Alerts.infoAlert("Added Flight Successfully!");
	    		a.show();
			} else {
				throw new InvalidInputException("something went wrong while adding a new flight. Try a different ID");
			}
			
		}  catch(InvalidInputException ipe) {
			a = Alerts.errorAlert(ipe.getMessage());
			a.show();
		} catch(Exception exc) {
			a = Alerts.errorAlert("an error has accured please try again");
    		a.show();
		}
	}
}
