package boundery;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import control.AirPlaneLogic;
import control.AirpPortLogic;
import control.FlightsLogic;
import entity.AirPlane;
import entity.AirPort;
import entity.Flight;
import exceptions.InvalidInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import util.InputValidetions;
import util.LoadPane;

public class FlightManagmentFrm {

    @FXML
    private AnchorPane ManagmentPane;

    @FXML
    private TextField DepFld;

    @FXML
    private TextField IDFld;

    @FXML
    private ComboBox<Flight> FlightCmbBx;

    @FXML
    private Button nextBtn;

    @FXML
    private Button pervBtn;

    @FXML
    private TextField DestFld;

    @FXML
    private TextField DepIDFld;

    @FXML
    private TextField DestIDFld;

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
    private ComboBox<AirPlane> airPlanes;

    @FXML
    private Button updateBtn;
    
    @FXML
    private Label FlightStatusLbl;
    
    private Tooltip pervtooltip;
    private Tooltip nexttooltip;
    private Tooltip searchtooltip;
    
    private ObservableList<Flight> flightsList;
    private HashMap<Integer,Flight> flightsById;
    private HashMap<Integer,AirPort> airPortsById;
    private Flight currentFlight;
    private int currFlightIndex;
    private ArrayList<Flight> flightArr;
    private Alert a = new Alert(AlertType.NONE);	
    private FlightsLogic flightsInstance = FlightsLogic.getInstance();
    
    
    @FXML
    public void initialize(){
		
    	init();
    }
    
    private void init(){
		
    	airPortsById = new HashMap<Integer,AirPort>();
    	flightsById = new HashMap<Integer,Flight>();
    	initPlanesItems();
    	initAirports();
    	initFlights();
    	pervtooltip = new Tooltip();
    	nexttooltip = new Tooltip();
    	searchtooltip = new Tooltip();
    	pervtooltip.setText("previous flight");
    	nexttooltip.setText("next flight");
    	searchtooltip.setText("search");
    	pervBtn.setTooltip(pervtooltip);
    	nextBtn.setTooltip(nexttooltip);
    	IDFld.setTooltip(searchtooltip);
    	currFlightIndex = 0;
    	if(flightArr != null) {
    		flightsList = FXCollections.observableArrayList(flightArr);
    		FlightCmbBx.setItems(flightsList);
    		FlightCmbBx.setValue(flightArr.get(0));
    		flightsById = new HashMap<Integer,Flight>();
    		for(Flight f: flightArr) {
    			flightsById.put(f.getFlightNum(), f);
    		}
    		loadFlightByCmb(new ActionEvent());
    	}
    	
	}
    
    private void initFlights(){
		
    	flightArr = flightsInstance.getFlights();

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

	public void initPlanesItems() {
		
    	ObservableList<AirPlane> planes = FXCollections.observableArrayList(AirPlaneLogic.getInstance().getAirplanes());
		airPlanes.getItems().clear();	
		airPlanes.setItems(FXCollections.observableArrayList(planes));
	}
    
    private void initAirports(){
		
    	ObservableList<AirPort> airports = FXCollections.observableArrayList(AirpPortLogic.getInstance().getAirports());
		for(AirPort ap: airports) {
			 airPortsById.put(ap.getAirportCode(), ap);
		}
	}
    
	@FXML
    void LoadFlight(KeyEvent event) {

		String s = IDFld.getText();
		boolean ans = InputValidetions.validatePositiveIntegerOrZero(s);
		if(ans == true && s != null && !s.isEmpty()) {
			Flight f = null;
			f = flightsById.get(Integer.parseInt(s));
			if(f != null) {
				currentFlight = FlightCmbBx.getValue();
				FlightCmbBx.setValue(f);
				setFields();
			}
		}
    }

    @FXML
    void UpdateData(ActionEvent event) {
    	
    	if(validateFields() == true) {
    		AirPlane plane = airPlanes.getValue();
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
			if(flightsInstance.editFlight(currentFlight.getFlightNum(), depatureDateTime, landingDateTime, plane.getTailNum())) {
				currentFlight.setDepatureTime(depatureDateTime);
	    		currentFlight.setLandingTime(landingDateTime);
	    		currentFlight.setAirPlaneTailNum(plane);
				a.setAlertType(AlertType.INFORMATION);
	    		a.setHeaderText("MESSAGE");
	    		a.setTitle("SYSTEM MESSAGE");
	    		a.setContentText("Flight Updated successfully!");
	    		a.show();
			}
			else {
				a.setAlertType(AlertType.WARNING);
	    		a.setHeaderText("MESSAGE");
	    		a.setTitle("SYSTEM MESSAGE");
	    		a.setContentText("Somthing went wrong!");
	    		a.show();
			}
    	}
    }

    @FXML
    void loadFlightByCmb(ActionEvent event) {

    	if(FlightCmbBx.getValue() != null) {
    		currentFlight = FlightCmbBx.getValue();
    		IDFld.setText(currentFlight.getFlightNum() + "");
    		setFields();
    	}
    }

    @FXML
    void loadNextFlight(ActionEvent event) {

    	if((currFlightIndex + 1) < flightArr.size()) {
    		currFlightIndex++;
    		FlightCmbBx.setValue(flightArr.get(currFlightIndex));
    		loadFlightByCmb(new ActionEvent());
    	}
    	else {
    		a.setAlertType(AlertType.INFORMATION);
    		a.setHeaderText("MESSAGE");
    		a.setTitle("SYSTEM MESSAGE");
    		a.setContentText("Last in the list!");
    		a.show();
    	}
    }

    @FXML
    void loadPervFlight(ActionEvent event) {

    	if((currFlightIndex - 1) >= 0) {
    		currFlightIndex--;
    		FlightCmbBx.setValue(flightArr.get(currFlightIndex));
    		loadFlightByCmb(new ActionEvent());
    	}
    	else {
    		a.setAlertType(AlertType.INFORMATION);
    		a.setHeaderText("MESSAGE");
    		a.setTitle("SYSTEM MESSAGE");
    		a.setContentText("First in the list!");
    		a.show();
    	}
    }
    private void setFields() {
    	
    	LocalDateTime depTime = currentFlight.getDepatureTime();
		LocalDateTime destTime = currentFlight.getLandingTime();
		AirPort depAp = airPortsById.get(currentFlight.getDepatureAirportID().getAirportCode());
		AirPort destAp = airPortsById.get(currentFlight.getDestinationAirportID().getAirportCode());
		AirPlane plane = currentFlight.getAirPlaneTailNum();
		String fStatus = currentFlight.getFlightStatus();
		FlightStatusLbl.setText(fStatus);
		if(fStatus.equals("on time")) {
			FlightStatusLbl.setTextFill(Color.web("Green"));
		}
		else if(fStatus.equals("canclelled")) {
			FlightStatusLbl.setTextFill(Color.web("Red"));
		}
		else
			FlightStatusLbl.setTextFill(Color.web("Blue"));
		airPlanes.setValue(plane);
		DepIDFld.setText(depAp.getAirportCode() + "");
		DestIDFld.setText(destAp.getAirportCode() + "");
		DepFld.setText(depAp.getCity() + " " + depAp.getCountry());
		DestFld.setText(destAp.getCity() + " " + destAp.getCountry());
		departureDate.setValue(depTime.toLocalDate());
		landingDate.setValue(destTime.toLocalDate());
		depHour.setValue(depTime.getHour());depMinute.setValue(depTime.getMinute());
		arrHour.setValue(destTime.getHour());arrMinute.setValue(destTime.getMinute());
    }
    
    private boolean validateFields() {
    	
    	try {
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
				throw new InvalidInputException("flight must be added 2 months before it's planned date");
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
			if(airPlanes.getSelectionModel().getSelectedItem() == null) {
				throw new InvalidInputException("Please select an Airplane");
			}
			if(!flightsInstance.isPlaneOverlapping(airPlanes.getSelectionModel().getSelectedItem(), depatureDateTime, landingDateTime, currentFlight.getFlightNum())) {
				throw new InvalidInputException("Airplane is already taken by another flight");
			}
			if(!flightsInstance.isAirportsOverlapping(currentFlight.getDepatureAirportID(), depatureDateTime, true, currentFlight.getFlightNum())) {
				throw new InvalidInputException("Please select a different Departue Date - flights collison");
			}
			if(!flightsInstance.isAirportsOverlapping(currentFlight.getDestinationAirportID(), landingDateTime, false, currentFlight.getFlightNum())) {
				throw new InvalidInputException("Please select a different Landing Date - flights collison");
			}
			
			return true;
    	}
    	catch(InvalidInputException e) {
    		a.setAlertType(AlertType.WARNING);
    		a.setHeaderText("MESSAGE");
    		a.setTitle("SYSTEM MESSAGE");
    		a.setContentText(e.getMessage());
    		a.show();
    	}
    	return false;
    }
}
