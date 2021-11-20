package boundery;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import control.FlightsLogic;
import entity.AirPlane;
import entity.AirPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightFrm {

	@FXML
	private TextField flightNum;
	@FXML
	private JFXDatePicker departureDate;
	@FXML
	private JFXDatePicker landingDate;
	@FXML
	private JFXTimePicker departureTime;
	@FXML
	private JFXTimePicker landingTime;
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
	}
	
}
