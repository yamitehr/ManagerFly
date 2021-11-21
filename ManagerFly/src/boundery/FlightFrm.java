package boundery;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import control.FlightsLogic;
import entity.AirPlane;
import entity.AirPort;
import exceptions.InvalidInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightFrm {

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
	@FXML
	private ComboBox<AirPort> depAirports;
	@FXML
	private ComboBox<AirPort> arrAirports;
	@FXML
	private ComboBox<AirPlane> airPlanes;
	@FXML
	private Button addFlight;
	@FXML
	private TextArea messageToUser;
	
	private FlightsLogic flightsInstance = FlightsLogic.getInstance();
	
	@FXML
    public void initialize() {
		init();
    }
	
	private void init() {
		//init AirPorts
		ObservableList<AirPort> airports = FXCollections.observableArrayList(FlightsLogic.getInstance().getAirports());
		depAirports.getItems().clear();		
		arrAirports.getItems().clear();	
		depAirports.setItems(FXCollections.observableArrayList(airports));
		arrAirports.setItems(FXCollections.observableArrayList(airports));
		
		//init planes
		ObservableList<AirPlane> planes = FXCollections.observableArrayList(FlightsLogic.getInstance().getAirplanes());
		airPlanes.getItems().clear();	
		airPlanes.setItems(FXCollections.observableArrayList(planes));
		
		//init time
		ArrayList<Integer> hoursList  = new ArrayList<>();
		ArrayList<Integer> minuteList  = new ArrayList<>();
		for(int i=0;i<22;i++) 
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
			if(departureDate.getValue().isBefore(LocalDate.now())) {
				throw new InvalidInputException("departure date cannot be before today");
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
			if(airPlanes.getValue() == null) {
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
			
			if(!flightsInstance.isPlaneOverlapping(airPlanes.getValue(), depatureDateTime, landingDateTime)) {
				throw new InvalidInputException("Airplane is already taken by another flight");
			}
			
			if(flightsInstance.addFlight(Integer.parseInt(flightNumber), depatureDateTime, landingDateTime, depAirports.getValue().getAirportCode(),
													arrAirports.getValue().getAirportCode(), airPlanes.getValue().getTailNum(), null, null, null, null)) {
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
	
	
}
