package boundery;


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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import util.Consts;
import util.InputValidetions;

public class FlightFrm {
	
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
	@FXML
	private TextField cityFld;
	@FXML
	private TextField countryFld;
	@FXML
	private TextField portIDFld;
	@FXML
    private ComboBox<Integer> timeZoneFld;
	private ObservableList<Integer> GMTValuesList;
	@FXML
    private Button saveAirPort;
	//AirPlanes
	@FXML
	private ListView<AirPlane> airPlanes;
	@FXML
    private TextField IDFld;
    @FXML
    private ComboBox<Integer> attNumcoboBox;
    @FXML
    private Button saveAirPlane;
    @FXML
    private ComboBox<Integer> bsnsCombo;
    @FXML
    private ComboBox<Integer> firstClassCombo;
    @FXML
    private ComboBox<Integer> TouristsCombo;
    @FXML
    private ComboBox<Integer> totalCollsCombo;
    private ObservableList<Integer> AttensdList;
    private ObservableList<Integer> buissnessList;
    private ObservableList<Integer> firstClsList;
    private ObservableList<Integer> touristsList;
    private ObservableList<Integer> totalCollsList;
    private ArrayList<FlightSeat> flightSeatsArrList;
    private int biggestSeatID;
	//Flight
	@FXML
	private Button addFlight;
	@FXML
	private TextArea messageToUser;
	@FXML
	private Alert a = new Alert(AlertType.NONE);
	
	private FlightsLogic flightsInstance = FlightsLogic.getInstance();
	
	@FXML
    public void initialize() {
		init();
    }
	
	private void init() {
		initFlights();
		initAirports();
		initAirplanes();
		
	}
	
	private void initFlights() {
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
	
	private void initAirports(){
		ObservableList<AirPort> airports = FXCollections.observableArrayList(AirpPortLogic.getInstance().getAirports());
		depAirports.getItems().clear();		
		arrAirports.getItems().clear();	
		depAirports.setItems(FXCollections.observableArrayList(airports));
		arrAirports.setItems(FXCollections.observableArrayList(airports));
		ArrayList<Integer> GmtArr  = new ArrayList<Integer>();
    	for(int i = -12; i <= 12; i++) {
    		GmtArr.add(i);
    	}
    	
    	GMTValuesList = FXCollections.observableArrayList(GmtArr);
    	timeZoneFld.setItems(GMTValuesList);
	}
	
	private void initAirplanes() {
		initPlanesItems();
		ArrayList<Integer> AttendArr  = new ArrayList<Integer>();
    	ArrayList<Integer> buissnessArr  = new ArrayList<Integer>();
    	ArrayList<Integer> firstClsArr  = new ArrayList<Integer>();
    	ArrayList<Integer> touristsArr  = new ArrayList<Integer>();
    	ArrayList<Integer> totalColsArr  = new ArrayList<Integer>();
    	for(int i = 1; i <= 12; i++) {
    		AttendArr.add(i);
    	}
    	for(int i = 1; i <= 10; i++) {
    		buissnessArr.add(i);
    	}
    	for(int i = 1; i <= 6; i++) {
    		firstClsArr.add(i);
    	}
    	for(int i = 20; i <= 60; i+= 5) {
    		touristsArr.add(i);
    	}
    	touristsArr.add(1);
    	for(int i = 1; i <= 5; i++) {
    		totalColsArr.add(i);
    	}
    	flightSeatsArrList = AirPlaneLogic.getInstance().getFlightSeats();
		biggestSeatID = flightSeatsArrList.get(0).getSeatID();
		for(FlightSeat fs: flightSeatsArrList) {
			if(fs.getSeatID() > biggestSeatID) {
				biggestSeatID = fs.getSeatID();
			}
		}
    	AttensdList = FXCollections.observableArrayList(AttendArr);
    	buissnessList = FXCollections.observableArrayList(buissnessArr);
        firstClsList = FXCollections.observableArrayList(firstClsArr);
        touristsList = FXCollections.observableArrayList(touristsArr);
        totalCollsList = FXCollections.observableArrayList(totalColsArr);
    	attNumcoboBox.setItems(AttensdList);
    	bsnsCombo.setItems(buissnessList);
    	bsnsCombo.setValue(buissnessArr.get(0));
        firstClassCombo.setItems(firstClsList);
        firstClassCombo.setValue(firstClsArr.get(0));
        TouristsCombo.setItems(touristsList);
        TouristsCombo.setValue(touristsArr.get(0));
        totalCollsCombo.setItems(totalCollsList);
        totalCollsCombo.setValue(totalColsArr.get(0));
	}
	private void initPlanesItems() {
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
			
			if(!flightsInstance.isPlaneOverlapping(airPlanes.getSelectionModel().getSelectedItem(), depatureDateTime, landingDateTime)) {
				throw new InvalidInputException("Airplane is already taken by another flight");
			}
			if(!flightsInstance.isAirportsOverlapping(depAirports.getValue(), depatureDateTime, true)) {
				throw new InvalidInputException("Please select a different Departue airport - flights collison");
			}
			if(!flightsInstance.isAirportsOverlapping(arrAirports.getValue(), landingDateTime, false)) {
				throw new InvalidInputException("Please select a different Landing airport - flights collison");
			}
			if(depAirports.getValue().equals(arrAirports.getValue())) {
				throw new InvalidInputException("Departure and Destination airports cannot be the same");
			}
			
			if(flightsInstance.addFlight(Integer.parseInt(flightNumber), depatureDateTime, landingDateTime, depAirports.getValue().getAirportCode(),arrAirports.getValue().getAirportCode(), airPlanes.getSelectionModel().getSelectedItem().getTailNum(), null, null,null)) {
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
	
	@FXML
    void addAirPort(ActionEvent event) {
		
		//if in edit mode
		try {
			
			//collect data from fields
			String city = cityFld.getText();
			String country = countryFld.getText();
			String ID = portIDFld.getText();
			
			if(timeZoneFld.getValue() == null) {
				throw new InvalidInputException("Please select time zone for the new Airport");
			}
			int timeZone = timeZoneFld.getValue();
			if(ID == null) {
				throw new InvalidInputException("Please fill ID for the new Airport");
			}
			if(country == null) {
				throw new InvalidInputException("Please fill Country for the new Airport");
			}
			if(city == null) {
				throw new InvalidInputException("Please fill City for the new Airport");
			}
			if(!InputValidetions.validateName(city)) {
				throw new InvalidInputException("Invalid City");
			}
			if(!InputValidetions.validateName(country)) {
				throw new InvalidInputException("Invalid Country");
			}
			if(!InputValidetions.validatePositiveIntegerOrZero(ID)) {
				throw new InvalidInputException("Invalid ID of Airport");
			}
			int id = Integer.parseInt(ID);
			if(id < 0) {
				throw new InvalidInputException("Invalid ID of Airport");
			}
			//adding a new airport
			if(AirpPortLogic.getInstance().addAirPort(id, city, country, timeZone)) {
				//printing success message to user
				messageToUser.setText("Added successfult a new Airport \nExpand the list to see it");
	    		initAirports();
			} else {
				throw new InvalidInputException("There was an error adding the new Airport, please try again");
			}
	    }  catch(InvalidInputException ipe) {
			messageToUser.setText(ipe.getMessage());
		} catch(Exception exc) {
			messageToUser.setText("an error has accured please try again");
		}
	}
	
	//----------------------------------------------------------------------------------------------------
	
	
	 @FXML
	    void addAirPlane(ActionEvent event) {

	    	//if in edit mode
			try {
				
				//collect data from fields
				String tailNum = IDFld.getText();
				Integer attendNumByCombo = attNumcoboBox.getValue();
				Integer totalColl = totalCollsCombo.getValue();
				Integer toursitsRows =  TouristsCombo.getValue();
				Integer firstClassRows  = firstClassCombo.getValue();
				Integer BuissnessRows = bsnsCombo.getValue();
				
				if(tailNum == null) {
					throw new InvalidInputException("Please fill Tail ID for the new Airplane");
				}
				if(totalColl == null) {
					throw new InvalidInputException("Please select columns amount for the new Airport");
				}
				if(toursitsRows == null) {
					throw new InvalidInputException("Please select number of rows in tourist section for the new Airport");
				}
				if(!InputValidetions.validateTailNum(tailNum)) {
					throw new InvalidInputException("Invalid Tail ID");
				}
				if(firstClassRows == null) {
					throw new InvalidInputException("Please select number of rows in First Class section for the new Airport");
				}
				if(BuissnessRows == null) {
					throw new InvalidInputException("Please select number of rows in Buisness section for the new Airport");
				}
				if(attendNumByCombo == null) {
					throw new InvalidInputException("Please select number of attendants in Buisness section for the new Airport");
				}
				Integer attNum = attendNumByCombo;
				//adding a new airport
				if(AirPlaneLogic.getInstance().addAirPlane(tailNum, attNum)) {
					AirPlane newAP = new AirPlane(tailNum, attNum, null);
		    		ArrayList<FlightSeat> seats = createSeats(tailNum, totalColl, firstClassRows, BuissnessRows, toursitsRows, newAP);
		    		newAP.setSeats(seats);
					initPlanesItems();
					//printing success message to user
					messageToUser.setText("Added successfuly a new Plane \nIt has " + seats.size() + " seats");
				} else {
					throw new InvalidInputException("Invalid ID of Airport");
				}
			}  catch(InvalidInputException ipe) {
				messageToUser.setText(ipe.getMessage());
			} catch(Exception exc) {
				messageToUser.setText("an error has accured please try again");
			}
		}
	 
	    /**
	     * generate seats and add them to DB by user input from the add plane screen
	     * @param tailNum = tail number of the plane
	     * @param totalCl = = user input columns number for the plane
	     * @param firstClsRow = user input rows number for first class seats
	     * @param buisRow = user input rows number for business seats
	     * @param tourRow = user input rows number for tourists seats
	     * @param plane
	     * @return
	     */
	    private ArrayList<FlightSeat> createSeats(String tailNum, int totalCl, int firstClsRow, int buisRow, int tourRow, AirPlane plane) {
			
	    	ArrayList<FlightSeat> seats = new ArrayList<FlightSeat>();
	    	String[] colls = {"A", "B", "C", "D", "E"};
	    	int rowindex = 1;
	    	int idBegin =  biggestSeatID + 1;
	    	for(int i = 1; i <= firstClsRow; i++) {
	    		for(int j = 0; j < totalCl; j++) {
		    		FlightSeat fs = new FlightSeat(idBegin++, rowindex, colls[j], Consts.SEAT_TYPES[0], plane);
		    		seats.add(fs);
		    		AirPlaneLogic.getInstance().addFlightSeat(idBegin, rowindex, colls[j], Consts.SEAT_TYPES[0], tailNum);
	    		}
	    		rowindex++;
	    	}
	    	for(int i = 1; i <= buisRow; i++) {
	    		for(int j = 0; j < totalCl; j++) {
		    		FlightSeat fs = new FlightSeat(idBegin++, rowindex, colls[j], Consts.SEAT_TYPES[1], plane);
		    		seats.add(fs);
		    		AirPlaneLogic.getInstance().addFlightSeat(idBegin, rowindex, colls[j], Consts.SEAT_TYPES[1], tailNum);
	    		}
	    		rowindex++;
	    	}
	    	for(int i = 1; i <= tourRow; i++) {
	    		for(int j = 0; j < totalCl; j++) {
		    		FlightSeat fs = new FlightSeat(idBegin++, rowindex, colls[j], Consts.SEAT_TYPES[2], plane);
		    		seats.add(fs);
		    		AirPlaneLogic.getInstance().addFlightSeat(idBegin, rowindex, colls[j], Consts.SEAT_TYPES[2], tailNum);
	    		}
	    		rowindex++;
	    	}
	    	biggestSeatID = idBegin - 1;
	    	
	    	return seats;
		}

}
