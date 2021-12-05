package boundery;

import java.util.ArrayList;

import control.AirpPortLogic;
import entity.AirPort;
import exceptions.InvalidInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.Alerts;
import util.InputValidetions;

public class AddAirportFrm {
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
	@FXML
    private Alert a;

	@FXML
    public void initialize() {
		init();
    }
	
	private void init() {
		ArrayList<Integer> GmtArr  = new ArrayList<Integer>();
    	for(int i = -12; i <= 12; i++) {
    		GmtArr.add(i);
    	}
    	
    	GMTValuesList = FXCollections.observableArrayList(GmtArr);
    	timeZoneFld.setItems(GMTValuesList);
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
				throw new InvalidInputException("Invalid City, should contain only letters");
			}
			if(!InputValidetions.validateName(country)) {
				throw new InvalidInputException("Invalid Country, should contain only letters");
			}
			if(!InputValidetions.validatePositiveIntegerOrZero(ID)) {
				throw new InvalidInputException("Invalid ID of Airport, should contain only numbers");
			}
			int id = Integer.parseInt(ID);
			if(id < 0) {
				throw new InvalidInputException("Invalid ID of Airport");
			}
			//adding a new airport
			if(AirpPortLogic.getInstance().addAirPort(id, city, country, timeZone)) {
				//printing success message to user
				a = Alerts.infoAlert("Added successfult a new Airport ");
				a.show();
			} else {
				throw new InvalidInputException("There was an error adding the new Airport, please try again");
			}
	    }  catch(InvalidInputException ipe) {
	    	a = Alerts.errorAlert(ipe.getMessage());
			a.show();
		} catch(Exception exc) {
			a = Alerts.errorAlert("an error has accured please try again");
			a.show();
		}
	}
}
