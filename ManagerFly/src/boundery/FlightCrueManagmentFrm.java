package boundery;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import control.AssignLogic;
import control.FlightsLogic;
import control.Getters;
import entity.AirAttendant;
import entity.AirPlane;
import entity.AirPort;
import entity.Flight;
import entity.Pilot;
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
import javafx.scene.control.ListView;
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

public class FlightCrueManagmentFrm {

    @FXML
    private AnchorPane ManagmentPane;

    @FXML
    private TextField IDFld;

    @FXML
    private ComboBox<Flight> FlightCmbBx;
    
    @FXML
    private ComboBox<Pilot> pilotCmbx;
    
    @FXML
    private ComboBox<Pilot> coPilotCmbx;
    
    @FXML
    private ListView<AirAttendant> airAttendantList;
    
    @FXML
    private ListView<AirAttendant> currentAirAttendantList;
    
    @FXML
    private Button nextBtn;

    @FXML
    private Button pervBtn;

    @FXML
    private Button updateBtn;
    
    @FXML
    private Label attenNumberLbl;
    
    private Tooltip pervtooltip;
    private Tooltip nexttooltip;
    private Tooltip searchtooltip;
    
    private ObservableList<Flight> flightsList;
    private HashMap<String,Flight> flightsById;
    private  Flight currentFlight;
    private static Flight curreStatucntFlight;
    private int currFlightIndex;
    private ArrayList<Flight> flightArr;
    private Alert a = new Alert(AlertType.NONE);	
    private FlightsLogic flightsInstance = FlightsLogic.getInstance();

    
    
    public static Flight getCurrentFlight() {
		return curreStatucntFlight;
	}

	public static void setCurrentFlight(Flight currentFlight) {
		FlightCrueManagmentFrm.curreStatucntFlight = currentFlight;
	}

	@FXML
    public void initialize(){
		
    	init();
    }
    
    private void init(){
    	initFlights();
    	initPilots();
    	initAirAttendant();
    	flightsById = new HashMap<String,Flight>();
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
    		flightsById = new HashMap<String,Flight>();
    		for(Flight f: flightArr) {
    			flightsById.put(f.getFlightNum(), f);
    		}
    		loadFlightByCmb(new ActionEvent());
    	}
    	
	}
   private void initFlights(){
		
    	flightArr = Getters.getInstance().getFlights();
		
	}
   
   public void initPilots() {
		
	   	ObservableList<Pilot> pilots = FXCollections.observableArrayList(Getters.getInstance().getPilots());
	   	pilotCmbx.setItems(FXCollections.observableArrayList(pilots));
		coPilotCmbx.setItems(FXCollections.observableArrayList(pilots));
	}
   
   public void initAirAttendant() {
	   ObservableList<AirAttendant> airAttendants = FXCollections.observableArrayList(Getters.getInstance().getAirAttendants());
	   airAttendantList.setItems(FXCollections.observableArrayList(airAttendants));
	   
   }
	@FXML
    void LoadFlight(KeyEvent event) {

		String s = IDFld.getText();
		if(s != null && !s.isEmpty()) {
			Flight f = null;
			f = flightsById.get(s);
			if(f != null) {
				currentFlight = FlightCmbBx.getValue();
				currFlightIndex = flightArr.indexOf(f);
				FlightCmbBx.setValue(f);
				setFields();
			}
		}
    }


    @FXML
    void loadFlightByCmb(ActionEvent event) {

    	if(FlightCmbBx.getValue() != null) {
    		currentFlight = FlightCmbBx.getValue();
    		currFlightIndex = flightArr.indexOf(currentFlight);
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
	    	
	    	String pilotID = currentFlight.getCheifPilotID();
			String coPilotID = currentFlight.getCoPilotID();
			ArrayList<Pilot> pilots = Getters.getInstance().getPilots();
			if(pilotID != null) {
				Pilot pilot1 = pilots.stream().filter(p -> p.equals(new Pilot(pilotID))).collect(Collectors.toList()).get(0);
				pilotCmbx.setValue(pilot1);
			} else {
				pilotCmbx.setValue(null);
			}
			if(coPilotID != null) {
				Pilot pilot2 = pilots.stream().filter(p -> p.equals(new Pilot(coPilotID))).collect(Collectors.toList()).get(0);
				coPilotCmbx.setValue(pilot2);
			}else {
				coPilotCmbx.setValue(null);
			}
			
			ObservableList<AirAttendant> currentAirAttendants = FXCollections.observableArrayList(Getters.getInstance().getAirAttendantsByFlight(currentFlight));
			currentAirAttendantList.setItems(FXCollections.observableArrayList(currentAirAttendants));
			
			ArrayList<AirPlane> airplanes = Getters.getInstance().getAirplanes();
			int num = airplanes.stream().filter(a -> a.equals(currentFlight.getAirPlaneTailNum())).collect(Collectors.toList()).get(0).getAttendantsNum();
			attenNumberLbl.setText(Integer.toString(num));
	    }
	
	@FXML
	private void addAirAttendant() {
		AirAttendant selectedAirAttendant = airAttendantList.getSelectionModel().getSelectedItem();
		if(selectedAirAttendant != null) {
			ObservableList<AirAttendant> currentAirAttendants = currentAirAttendantList.getItems();
			currentAirAttendants.add(selectedAirAttendant);
			currentAirAttendantList.setItems(currentAirAttendants);
		}
	}
	
	@FXML
	private void removeAirAttendant() {
		AirAttendant selectedAirAttendant = currentAirAttendantList.getSelectionModel().getSelectedItem();
		if(selectedAirAttendant != null) {
			ObservableList<AirAttendant> currentAirAttendants = currentAirAttendantList.getItems();
			currentAirAttendants.remove(selectedAirAttendant);
			currentAirAttendantList.setItems(currentAirAttendants);
		}
	}
	
	@FXML
	private void saveChanges() {
		Pilot pilot1 = pilotCmbx.getSelectionModel().getSelectedItem();
		Pilot pilot2 = coPilotCmbx.getSelectionModel().getSelectedItem();
		if(pilot1 != null) {
			AssignLogic.getInstance().updateMainPilot(pilot1.getID(), currentFlight.getFlightNum());
			currentFlight.setCheifPilotID(pilot1.getID());
		}
		if(pilot2 != null) {
			AssignLogic.getInstance().updateSecondaryPilot(pilot2.getID(), currentFlight.getFlightNum());
			currentFlight.setCheifPilotID(pilot2.getID());
		}
		if(currentAirAttendantList.getItems() != null) {
			AssignLogic.getInstance().deleteAirAttendantsFromFlight(currentFlight);
			ObservableList<AirAttendant> currentAirAttendants = currentAirAttendantList.getItems();
			for(AirAttendant aa : currentAirAttendants) {
				AssignLogic.getInstance().addAirAttendantToFlight(aa, currentFlight);
			}
		} else {
			AssignLogic.getInstance().deleteAirAttendantsFromFlight(currentFlight);
		}
	}
}
