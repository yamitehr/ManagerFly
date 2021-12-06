package boundery;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import control.AirPlaneLogic;
import control.AirpPortLogic;
import control.FlightsLogic;
import entity.AirPlane;
import entity.AirPort;
import entity.FlightSeat;
import exceptions.InvalidInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import util.Consts;
import util.InputValidetions;

public class AddFlightFrm {
	@FXML
	private Pane addPlaneFrm;
	@FXML
	private Pane addPortFrm;
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
	public TextArea messageToUser;
	@FXML
	private Alert a;
	
	private FlightsLogic flightsInstance = FlightsLogic.getInstance();
	
	@FXML
    public void initialize() {
		init();
    }
	
	private void init() {
		//load secondary forms
		try {
			addPlaneFrm.getChildren().add(FXMLLoader.load(getClass().getResource("AddAirplaneFrm.fxml")));
			addPortFrm.getChildren().add(FXMLLoader.load(getClass().getResource("AddAirportFrm.fxml")));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initFlights();
		initAirports();

		ObservableList<AirPlane> airplanes = FXCollections.observableArrayList(AirPlaneLogic.getInstance().getAirplanes());
		airPlanes.getItems().clear();		
		airPlanes.setItems(FXCollections.observableArrayList(airplanes));
		
	}
	
	
	
	private void initFlights() {
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
	}
	
	
	private void initAirports(){
		ObservableList<AirPort> airports = FXCollections.observableArrayList(AirpPortLogic.getInstance().getAirports());
		depAirports.getItems().clear();		
		arrAirports.getItems().clear();	
		depAirports.setItems(FXCollections.observableArrayList(airports));
		arrAirports.setItems(FXCollections.observableArrayList(airports));
	}
	
	
	public void initPlanesItems() {
		ObservableList<AirPlane> planes = FXCollections.observableArrayList(AirPlaneLogic.getInstance().getAirplanes());
		airPlanes.getItems().clear();	
		airPlanes.setItems(FXCollections.observableArrayList(planes));
	}
	
	@FXML
	private void addNewFlight() {
		try {
			
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
			if(departureDate.getValue().isBefore(LocalDate.now().plusMonths(2))) {
				throw new InvalidInputException("a new flight must be added 2 months before it's planned date");
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
			
			if(!flightsInstance.isPlaneOverlapping(airPlanes.getSelectionModel().getSelectedItem(), depatureDateTime, landingDateTime, null)) {
				throw new InvalidInputException("Airplane is already taken by another flight");
			}
			if(!flightsInstance.isAirportsOverlapping(depAirports.getValue(), depatureDateTime, true,null)) {
				throw new InvalidInputException("Please select a different Departue airport - flights collison");
			}
			if(!flightsInstance.isAirportsOverlapping(arrAirports.getValue(), landingDateTime, false, null)) {
				throw new InvalidInputException("Please select a different Landing airport - flights collison");
			}
			if(depAirports.getValue().equals(arrAirports.getValue())) {
				throw new InvalidInputException("Departure and Destination airports cannot be the same");
			}
			
			if(flightsInstance.addFlight(Integer.parseInt(flightNumber), depatureDateTime, landingDateTime, depAirports.getValue().getAirportCode(),arrAirports.getValue().getAirportCode(), airPlanes.getSelectionModel().getSelectedItem().getTailNum(), null, null,"on time")) {
				messageToUser.setText("added successfully!");
			} else {
				messageToUser.setText("something went wrong while adding a new flight");
			}
			
		}  catch(InvalidInputException ipe) {
			messageToUser.setText(ipe.getMessage());
		} catch(Exception exc) {
			messageToUser.setText("an error has accured please try again");
		}
	}
	
	//----------------------------------------------------------------------------------------------------
	
	


}
