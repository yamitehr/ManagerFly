package boundery;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import control.AirpPortLogic;
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

public class AirPortsFrm {

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
    private ArrayList<AirPort> airportArrList;
    private int currentAirPortIndex;
    private AirPort currentAirPort;
    private boolean inEditMode;//to determine if the user want to add new airport
    private HashMap<Integer, AirPort> airportMap;
    private Alert a = new Alert(AlertType.NONE);
    @FXML
    public void initialize() {
		init();
    }
		

    private void init() {
		
    	inEditMode = false;
    	GMTtooltip = new Tooltip();
    	pervtooltip = new Tooltip();
    	nexttooltip = new Tooltip();
    	GMTtooltip.setText("GMT Values");
    	pervtooltip.setText("previous airport");
    	nexttooltip.setText("next airport");
    	timeZoneFld.setTooltip(GMTtooltip);
    	pervBtn.setTooltip(pervtooltip);
    	nextBtn.setTooltip(nexttooltip);
    	ArrayList<Integer> GmtArr  = new ArrayList<Integer>();
    	for(int i = -12; i <= 12; i++) {
    		GmtArr.add(i);
    	}
    	
    	GMTValuesList = FXCollections.observableArrayList(GmtArr);
    	timeZoneFld.setItems(GMTValuesList);
    	currentAirPortIndex = 0;
    	airportArrList = AirpPortLogic.getInstance().getAirports();
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

    

	@FXML
    void addAirPort(ActionEvent event) {
		
		if(inEditMode == true) {
			String city = cityFld.getText();
			String country = countryFld.getText();
			String ID = IDFld.getText();
			int timeZone = timeZoneFld.getValue();
			if(InputValidetions.validateName(city) && InputValidetions.validateName(country) && InputValidetions.validatePositiveIntegerOrZero(ID)) {
				int id = Integer.parseInt(ID);
				if(id > 0) {
					if(AirpPortLogic.getInstance().addAirPort(id, city, country, timeZone)) {
						a.setAlertType(AlertType.INFORMATION);
			    		a.setHeaderText("MESSAGE");
			    		a.setTitle("SYSTEM MESSAGE");
			    		a.setContentText("Added succesfully");
			    		a.show();
			    		notInEdit();
			    		AirPort newAP = new AirPort(id, city, country, timeZone);
			    		addtoDataStructures(newAP);
			    		
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
	
	private void faildtoAddMsg() {
		
		a.setAlertType(AlertType.WARNING);
		a.setHeaderText("MESSAGE");
		a.setTitle("SYSTEM MESSAGE");
		a.setContentText("Faild to add");
		a.show();
		notInEdit();
	}
	
	private void addtoDataStructures(AirPort ap) {
		
		airportMap.put(ap.getAirportCode(), ap);
		currentAirPort = ap;
		airportArrList.add(ap);
		currentAirPortIndex = airportArrList.size() - 1; 
		airPortCmbBx.getItems().add(ap);
		airPortCmbBx.setValue(ap);
	}

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

}
