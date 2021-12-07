package boundery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import control.FlightsLogic;
import control.Getters;
import entity.AirPlane;
import entity.AirPort;
import entity.FlightSeat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import util.Consts;
import util.InputValidetions;

public class ManageAirplanesFrm {


    @FXML
    private TextField IDFld;

    @FXML
    private ComboBox<Integer> attNumcoboBox;

    @FXML
    private Button loadEmptyFrmBtn;

    @FXML
    private Button saveAirPlane;

    @FXML
    private Button nextBtn;

    @FXML
    private Button pervBtn;

    @FXML
    private Pane seatsPane;

    @FXML
    private ListView<FlightSeat> seatsTable;

    @FXML
    private Pane addSeatsPane;

    @FXML
    private ComboBox<Integer> bsnsCombo;

    @FXML
    private ComboBox<Integer> firstClassCombo;

    @FXML
    private ComboBox<Integer> TouristsCombo;

    @FXML
    private ComboBox<Integer> totalCollsCombo;
    
    @FXML
    private ComboBox<AirPlane> planeCmbo;
    
    private ObservableList<AirPlane> airPlaneList;
    private ObservableList<FlightSeat> flightSeatsList;
    private ObservableList<Integer> AttensdList;
    private ObservableList<Integer> buissnessList;
    private ObservableList<Integer> firstClsList;
    private ObservableList<Integer> touristsList;
    private ObservableList<Integer> totalCollsList;
    private Tooltip IDtooltip;
    private Tooltip pervtooltip;
    private Tooltip nexttooltip;
    private ArrayList<AirPlane> airplaneArrList;			//list of all airplanes
    private ArrayList<FlightSeat> flightSeatsArrList;
    private int currentAirPlaneIndex;					//to indicate which airplane to show from the list when clicking 
    													//'>' or '<' button
    private AirPlane currentPlane;
    private boolean inEditMode; 						//to determine if the user can add new airplane
    private HashMap<String, AirPlane> airPlaneMap;		//contains all airplane, uses their id as key help to search and
    private HashMap<String, ArrayList<FlightSeat>> seatsMap;
    private Alert a = new Alert(AlertType.NONE);		//for displaying pop up messages for the user
    private int biggestSeatID;
    
    @FXML
    public void initialize() {
		init();
    }
    
   
	private void init() {
		
    	//initializing fields and data structures
    	inEditMode = false;
    	IDtooltip = new Tooltip();
    	pervtooltip = new Tooltip();
    	nexttooltip = new Tooltip();
    	IDtooltip.setText("Search ,for Example: A-2079");
    	pervtooltip.setText("previous airplane");
    	nexttooltip.setText("next airplane");
    	IDFld.setTooltip(IDtooltip);
    	pervBtn.setTooltip(pervtooltip);
    	nextBtn.setTooltip(nexttooltip);
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
    	for(int i = 2; i <= 5; i++) {
    		totalColsArr.add(i);
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
    	currentAirPlaneIndex = 0;
    	seatsTable.setFixedCellSize(45);
    	airplaneArrList = Getters.getInstance().getAirplanes();
    	if(airplaneArrList != null) {
    		airPlaneList = FXCollections.observableArrayList(airplaneArrList);
    		planeCmbo.setItems(airPlaneList);
    		planeCmbo.setValue(airplaneArrList.get(0));
    		airPlaneMap = new HashMap<String,AirPlane>();
    		seatsMap  = new HashMap<String,ArrayList<FlightSeat>>();
    		flightSeatsArrList = Getters.getInstance().getFlightSeats();
    		biggestSeatID = flightSeatsArrList.get(0).getSeatID();
    		for(FlightSeat fs: flightSeatsArrList) {
    			if(fs.getSeatID() > biggestSeatID) {
    				biggestSeatID = fs.getSeatID();
    			}
    			ArrayList<FlightSeat> sArr  = seatsMap.get(fs.getPlane().getTailNum());
    			if(sArr == null) {
    				sArr = new ArrayList<FlightSeat>();
    			}
    			sArr.add(fs);
    			seatsMap.put(fs.getPlane().getTailNum(), sArr);
    		}
    		for(AirPlane ap: airplaneArrList) {
    			ap.setSeats(seatsMap.get(ap.getTailNum()));
    			airPlaneMap.put(ap.getTailNum(), ap);	
    		}
    		loadPlaneByCmbo(new ActionEvent());
    	}
		
	}

	/**
	 * load the chosen plane data
	 * @param event
	 */
	@FXML
    void LoadPlane(KeyEvent event) {

		String s = IDFld.getText();
		if(IDFld.getText() != null && inEditMode == false && !s.isEmpty()) {
			AirPlane ap = null;
			ap = airPlaneMap.get(s);
			if(ap != null) {
				currentPlane = ap;
	    		IDFld.setText(currentPlane.getTailNum() + "");
	    		attNumcoboBox.setValue(currentPlane.getAttendantsNum());
	    		currentAirPlaneIndex = airplaneArrList.indexOf(currentPlane);
	    		flightSeatsList  = FXCollections.observableArrayList(currentPlane.getSeats());		
	    		seatsTable.setItems(flightSeatsList);
	    		notInEdit();
			}
    	}
    }

	/**
	 * load the chosen plane data
	 * @param event
	 */
    @FXML
    void loadPlaneByCmbo(ActionEvent event) {
    	
    	if(planeCmbo.getValue() != null && inEditMode == false) {
    		currentPlane = planeCmbo.getValue();
    		IDFld.setText(currentPlane.getTailNum() + "");
    		attNumcoboBox.setValue(currentPlane.getAttendantsNum());
    		currentAirPlaneIndex = airplaneArrList.indexOf(currentPlane);
    		flightSeatsList  = FXCollections.observableArrayList(currentPlane.getSeats());		
    		seatsTable.setItems(flightSeatsList);
    		notInEdit();
    	}

    }
    
    /**
     * add new plane and seats to the DB
     * @param event
     */
    @FXML
    void addAirPlane(ActionEvent event) {

    	//if in edit mode
		if(inEditMode == true) {
			
			//collect data from fields
			String tailNum = IDFld.getText();
			Integer attendNumByCombo = attNumcoboBox.getValue();
			Integer totalColl = totalCollsCombo.getValue();
			Integer toursitsRows =  TouristsCombo.getValue();
			Integer firstClassRows  = firstClassCombo.getValue();
			Integer BuissnessRows = bsnsCombo.getValue();
			
			//validating fields
			if(tailNum != null  && !tailNum.isEmpty() &&  InputValidetions.validateTailNum(tailNum)) {
				if( totalCollsCombo.getValue() != null && TouristsCombo.getValue() != null && firstClassCombo.getValue() != null && bsnsCombo.getValue() != null) {			
					if(attendNumByCombo != null) {
						Integer attNum = attendNumByCombo;
						
						//adding a new airport
						if(FlightsLogic.getInstance().addAirPlane(tailNum, attNum)) {
							
    							//printing  message to user
    							a.setAlertType(AlertType.INFORMATION);
    				    		a.setHeaderText("MESSAGE");
    				    		a.setTitle("SYSTEM MESSAGE");
    				    		a.setContentText("Added succesfully");
    				    		a.show();
    				    		AirPlane newAP = new AirPlane(tailNum, attNum, null);
    				    		ArrayList<FlightSeat> seats = createSeats(tailNum, totalColl, firstClassRows, BuissnessRows, toursitsRows, newAP);
    				    		newAP.setSeats(seats);
    				    		addtoDataStructures(newAP);
    				    		notInEdit();	
    				    		AddFlightFrm.closeWindow();
							}
						else
							faildtoAddMsg();
						}
					else
    						faildtoAddMsg();
					}
				else
					faildtoAddMsg();
				}
			else {
				a.setAlertType(AlertType.ERROR);
		  		a.setHeaderText("ERROR");
		  		a.setTitle("INPUT ERROR");
		  		a.setContentText("Tail number must be of this pattern Letter-Letters/integers\n"
		  				+ "Example: A-2079B");
		  		a.show();
			}					
		}
		else
			faildtoAddMsg();
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
	    		FlightsLogic.getInstance().addFlightSeat(idBegin, rowindex, colls[j], Consts.SEAT_TYPES[0], tailNum);
    		}
    		rowindex++;
    	}
    	for(int i = 1; i <= buisRow; i++) {
    		for(int j = 0; j < totalCl; j++) {
	    		FlightSeat fs = new FlightSeat(idBegin++, rowindex, colls[j], Consts.SEAT_TYPES[1], plane);
	    		seats.add(fs);
	    		FlightsLogic.getInstance().addFlightSeat(idBegin, rowindex, colls[j], Consts.SEAT_TYPES[1], tailNum);
    		}
    		rowindex++;
    	}
    	for(int i = 1; i <= tourRow; i++) {
    		for(int j = 0; j < totalCl; j++) {
	    		FlightSeat fs = new FlightSeat(idBegin++, rowindex, colls[j], Consts.SEAT_TYPES[2], plane);
	    		seats.add(fs);
	    		FlightsLogic.getInstance().addFlightSeat(idBegin, rowindex, colls[j], Consts.SEAT_TYPES[2], tailNum);
    		}
    		rowindex++;
    	}
    	biggestSeatID = idBegin - 1;
    	
    	return seats;
	}


    /**
     * load empty firm for adding new plane and its seats
     * @param event
     */
	@FXML
    void loadEmptyFrm(ActionEvent event) {

    	inEdit();
    }

	/**
	 * load next data of the next plane from the plane array list
	 * @param event
	 */
    @FXML
    void loadNextAirPlane(ActionEvent event) {

    	if((currentAirPlaneIndex + 1) < airplaneArrList.size()) {
    		currentAirPlaneIndex++;
    		planeCmbo.setValue(airplaneArrList.get(currentAirPlaneIndex));
    		notInEdit();
    		loadPlaneByCmbo(new ActionEvent());
    	}
    	else {
    		a.setAlertType(AlertType.INFORMATION);
    		a.setHeaderText("MESSAGE");
    		a.setTitle("SYSTEM MESSAGE");
    		a.setContentText("Last in the list!");
    		a.show();
    		notInEdit();
    	}
    }

    /**
	 * load next data of the previous plane from the plane array list
	 * @param event
	 */
    @FXML
    void loadPervAirPlane(ActionEvent event) {

    	if((currentAirPlaneIndex - 1) >= 0) {
    		currentAirPlaneIndex--;
    		planeCmbo.setValue(airplaneArrList.get(currentAirPlaneIndex));
    		notInEdit();
    		loadPlaneByCmbo(new ActionEvent());
    	}
    	else {
    		a.setAlertType(AlertType.INFORMATION);
    		a.setHeaderText("MESSAGE");
    		a.setTitle("SYSTEM MESSAGE");
    		a.setContentText("First in the list!");
    		a.show();
    		notInEdit();
    	}
    }
    
  //displayed when the append failed
  	private void faildtoAddMsg() {
  		
  		a.setAlertType(AlertType.WARNING);
  		a.setHeaderText("MESSAGE");
  		a.setTitle("SYSTEM MESSAGE");
  		a.setContentText("Faild to add");
  		a.show();
  		notInEdit();
  	}
  	
  	/**
	 * adding the new airplane to the class data structures
	 * @param ap = the new airport
	 */
	private void addtoDataStructures(AirPlane ap) {
		
		airPlaneMap.put(ap.getTailNum(), ap);
		currentPlane = ap;
		airplaneArrList.add(ap);
		currentAirPlaneIndex = airplaneArrList.size() - 1; 
		seatsMap.put(ap.getTailNum(), ap.getSeats());
		planeCmbo.getItems().add(ap);
		planeCmbo.setValue(ap);
		flightSeatsArrList.addAll(ap.getSeats());
		flightSeatsList.setAll(ap.getSeats());
		seatsTable.setItems(flightSeatsList);
	}
	
	/**
	 * set screen for edit mode
	 */
	 private void notInEdit() {
	    	
	    	inEditMode = false;
			saveAirPlane.setOpacity(0.4);
			saveAirPlane.setDisable(true);
			addSeatsPane.setVisible(false);
			seatsPane.setVisible(true);
			planeCmbo.setVisible(true);
			
	    }
	    
	 	/**
	 	 * set screen for view mode
	 	 */
	    private void inEdit() {
	    	
	    	inEditMode = true;
			saveAirPlane.setOpacity(1.0);
			saveAirPlane.setDisable(false);
			addSeatsPane.setVisible(true);
			seatsPane.setVisible(false);
			planeCmbo.setVisible(false);
			IDFld.setText(null);
	    }
	    
	    public void inEditAddFlight() {
	    	
	    	inEditMode = true;
	    	IDtooltip.setText("Format: A-1234");
	    	pervtooltip.hide();
	    	nexttooltip.hide();
	    	nextBtn.setTooltip(nexttooltip);
	    	IDFld.clear();
			saveAirPlane.setOpacity(1.0);
			saveAirPlane.setDisable(false);
			addSeatsPane.setVisible(true);
			seatsPane.setVisible(false);
			planeCmbo.setVisible(false);
			pervBtn.setVisible(false);
			nextBtn.setVisible(false);
			loadEmptyFrmBtn.setVisible(false);
	    }

}
