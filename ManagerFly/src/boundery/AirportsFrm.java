package boundery;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import control.FlightsLogic;
import control.Getters;
import entity.AirPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import util.InputValidetions;

public class AirportsFrm {

    @FXML
    private TextField cityFld;

    @FXML
    private TextField countryFld;

    @FXML
    private TextField IDFld;

    @FXML
    private ComboBox<AirPort> airPortCmbBx;

    @FXML
    private ComboBox<Integer> timeZoneFld;

    @FXML
    private Button loadEmptyFrmBtn;

    @FXML
    private Button saveAirPort;

    @FXML
    private Button nextBtn;

    @FXML
    private Button pervBtn;
    
    private ObservableList<AirPort> airPortsList;
    private ObservableList<Integer> GMTValuesList;
    private Tooltip GMTtooltip;
    private Tooltip pervtooltip;
    private Tooltip nexttooltip;
    private Tooltip searchtooltip;
    private ArrayList<AirPort> airportArrList;			//list of all airports
    private int currentAirPortIndex;					//to indicate which airport to show from the list when clicking
    													//'>' or '<' button
    private AirPort currentAirPort;
    private boolean inEditMode; 						//to determine if the user can add new airport
    private HashMap<Integer, AirPort> airportMap;		//contains all airports, uses their id as key help to search and
    													//display airport details when typing in the id field
    private Alert a = new Alert(AlertType.NONE);		//for displaying pop up messages for the user
    @FXML
    public void initialize() {
		init();
    }
		

    private void init() {
		
    	//initializing fields and data structures
    	inEditMode = false;
    	GMTtooltip = new Tooltip();
    	pervtooltip = new Tooltip();
    	nexttooltip = new Tooltip();
    	searchtooltip = new Tooltip();
    	GMTtooltip.setText("GMT Values");
    	pervtooltip.setText("previous airport");
    	nexttooltip.setText("next airport");
    	searchtooltip.setText("search");
    	timeZoneFld.setTooltip(GMTtooltip);
    	pervBtn.setTooltip(pervtooltip);
    	nextBtn.setTooltip(nexttooltip);
    	IDFld.setTooltip(searchtooltip);
    	ArrayList<Integer> GmtArr  = new ArrayList<Integer>();
    	for(int i = -12; i <= 12; i++) {
    		GmtArr.add(i);
    	}
    	
    	GMTValuesList = FXCollections.observableArrayList(GmtArr);
    	timeZoneFld.setItems(GMTValuesList);
    	currentAirPortIndex = 0;
    	airportArrList = Getters.getInstance().getAirports();
    	if(airportArrList != null) {
    		airPortsList = FXCollections.observableArrayList(airportArrList);
    		airPortCmbBx.setItems(airPortsList);
    		airPortCmbBx.setValue(airportArrList.get(0));
    		airportMap = new HashMap<Integer,AirPort>();
    		for(AirPort ap: airportArrList) {
    			airportMap.put(ap.getAirportCode(), ap);
    		}
    		loadAirPortByCmb(new ActionEvent());
    	}
    	
	}

    
    /**
     * adding a new air port to the DB
     * @param event
     */
	@FXML
    void addAirPort(ActionEvent event) {
		
		//if in edit mode
		if(inEditMode == true) {
			
			//collect data from fields
			String city = cityFld.getText();
			String country = countryFld.getText();
			String ID = IDFld.getText();
			int timeZone = timeZoneFld.getValue();
			//validating fields
			if(timeZoneFld.getValue() != null && IDFld.getText() != null && countryFld.getText() != null && cityFld.getText() != null && InputValidetions.validateName(city) && InputValidetions.validateName(country) && InputValidetions.validatePositiveIntegerOrZero(ID)) {
				int id = Integer.parseInt(ID);
				if(id > 0 && airportMap.get(id) == null) {
					//adding a new airport
					if(FlightsLogic.getInstance().addAirPort(id, city, country, timeZone)) {
						
						//printing  message to user
						a.setAlertType(AlertType.INFORMATION);
			    		a.setHeaderText("MESSAGE");
			    		a.setTitle("SYSTEM MESSAGE");
			    		a.setContentText("Added succesfully");
			    		a.show();
			    		notInEdit();
			    		AirPort newAP = new AirPort(id, city, country, timeZone);
			    		addtoDataStructures(newAP);
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
		
    }
	
	//displayed when the append failed
	private void faildtoAddMsg() {
		
		a.setAlertType(AlertType.WARNING);
		a.setHeaderText("MESSAGE");
		a.setTitle("SYSTEM MESSAGE");
		a.setContentText("Faild to add");
		a.show();
	}
	
	/**
	 * adding the new airport to the class data structures
	 * @param ap = the new airport
	 */
	private void addtoDataStructures(AirPort ap) {
		
		airportMap.put(ap.getAirportCode(), ap);
		currentAirPort = ap;
		airportArrList.add(ap);
		currentAirPortIndex = airportArrList.size() - 1; 
		airPortCmbBx.getItems().add(ap);
		airPortCmbBx.setValue(ap);
	}

	/**
	 * display current airport details
	 * @param event
	 */
    @FXML
    void LoadPort(KeyEvent event) {
    	
    	String s = IDFld.getText();
		boolean ans = InputValidetions.validatePositiveIntegerOrZero(s);
		if(ans == true && s != null && !s.isEmpty()) {
			AirPort ap = null;
			ap = airportMap.get(Integer.parseInt(s));
			if(ap != null) {
				currentAirPort = ap;
				airPortCmbBx.setValue(ap);
				IDFld.setText(currentAirPort.getAirportCode() + "");
	    		cityFld.setText(currentAirPort.getCity());
	    		countryFld.setText(currentAirPort.getCountry());
	    		timeZoneFld.setValue(currentAirPort.getTimeZone());
	    		currentAirPortIndex = airportArrList.indexOf(ap);
	    		notInEdit();
			}
		}
    }

 /////loading airport by combo box or id field/////
    @FXML
    void loadAirPortByCmb(ActionEvent event) {
    	
    	if(airPortCmbBx.getValue() != null && inEditMode == false) {
    		currentAirPort = airPortCmbBx.getValue();
    		IDFld.setText(currentAirPort.getAirportCode() + "");
    		cityFld.setText(currentAirPort.getCity());
    		countryFld.setText(currentAirPort.getCountry());
    		timeZoneFld.setValue(currentAirPort.getTimeZone());
    		currentAirPortIndex = airportArrList.indexOf(currentAirPort);
    		notInEdit();
    	}
    }

    /**
     * load empty firm (when entering edit mode)
     * @param event
     */
    @FXML
    void loadEmptyFrm(ActionEvent event) {

    	inEdit();  	
    }

    @FXML
    void loadNextAirPort(ActionEvent event) {
    	
    	if((currentAirPortIndex + 1) < airportArrList.size()) {
    		currentAirPortIndex++;
    		airPortCmbBx.setValue(airportArrList.get(currentAirPortIndex));
    		notInEdit();
    		loadAirPortByCmb(new ActionEvent());
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

    @FXML
    void loadPervAirPort(ActionEvent event) {
    	
    	if((currentAirPortIndex - 1) >= 0) {
    		currentAirPortIndex--;
    		airPortCmbBx.setValue(airportArrList.get(currentAirPortIndex));
    		notInEdit();
    		loadAirPortByCmb(new ActionEvent());
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
    
    private void notInEdit() {
    	inEditMode = false;
		saveAirPort.setOpacity(0.4);
		saveAirPort.setDisable(true);
		cityFld.setEditable(false);
		countryFld.setEditable(false);
    }
    
    private void inEdit() {
    	inEditMode = true;
		saveAirPort.setOpacity(1.0);
		saveAirPort.setDisable(false);
		cityFld.setText(null);
		countryFld.setText(null);
		IDFld.setText(null);
		cityFld.setEditable(true);
		countryFld.setEditable(true);
    }
    
   public void inEditAddFlight() {
    	
    	inEditMode = true;
    	pervtooltip.hide();
    	nexttooltip.hide();
    	nextBtn.setTooltip(nexttooltip);
    	IDFld.clear();
		saveAirPort.setOpacity(1.0);
		saveAirPort.setDisable(false);
		cityFld.setText(null);
		countryFld.setText(null);
		IDFld.setText(null);
		cityFld.setEditable(true);
		countryFld.setEditable(true);
		airPortCmbBx.setVisible(false);
		pervBtn.setVisible(false);
		nextBtn.setVisible(false);
		loadEmptyFrmBtn.setVisible(false);
    }

}
