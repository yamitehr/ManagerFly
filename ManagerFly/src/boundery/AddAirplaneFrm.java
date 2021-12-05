package boundery;

import java.util.ArrayList;

import control.AirPlaneLogic;
import entity.AirPlane;
import entity.FlightSeat;
import exceptions.InvalidInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import util.Alerts;
import util.Consts;
import util.InputValidetions;

public class AddAirplaneFrm {
	

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
    @FXML
    private Alert a;
    
    @FXML
    public void initialize() {
		init();
    }
    
    private void init() {
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
    
    @FXML
    void addAirPlane(ActionEvent event) {

		try {
			
			//collect data from fields
			String tailNum = IDFld.getText();
			Integer attendNumByCombo = attNumcoboBox.getValue();
			Integer totalColl = totalCollsCombo.getValue();
			Integer toursitsRows =  TouristsCombo.getValue();
			Integer firstClassRows  = firstClassCombo.getValue();
			Integer BuissnessRows = bsnsCombo.getValue();
			
			if(tailNum == null) {
				throw new InvalidInputException("Please fill Tail ID for the new Airplane, in the form of A-1234");
			}
			if(totalColl == null) {
				throw new InvalidInputException("Please select columns amount for the new Airport");
			}
			if(toursitsRows == null) {
				throw new InvalidInputException("Please select number of rows in tourist section for the new Airport");
			}
			if(!InputValidetions.validateTailNum(tailNum)) {
				throw new InvalidInputException("Invalid Tail ID, should be in th form of A-1234");
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
				//printing success message to user
			a = Alerts.infoAlert("Added new Airplane successfully with " +seats.size()+ " seats");
			a.show();
			} else {
				throw new InvalidInputException("Invalid ID of Airport");
			}
		}  catch(InvalidInputException ipe) {
			a = Alerts.errorAlert(ipe.getMessage());
			a.show();
		} catch(Exception exc) {
			a = Alerts.errorAlert("an error has accured please try again");
			a.show();
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
