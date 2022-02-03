package boundery;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.ui.RefineryUtilities;

import control.Getters;
import control.ReportsLogic;
import entity.AirPlane;
import entity.AirPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import util.InputValidetions;

public class FlightByDestReportFrm {

    @FXML
    private ComboBox<String> countryCmbx;

    @FXML
    private Button BiggestFlightsReportBtn;

    @FXML
    private ImageView readMoreIcn;

    @FXML
    private Button readMoreBtn;
    
    private String massage = "";
   
    /**
     * info about the biggest flights report
     */
    private String biggestFlightsInfo = "biggest Flights Reprot = return a report of all the flights that occurred in range of 'from' and 'until' fields,\n"
    		+ "and thier plane  contain at least a given amount of  tourists seats.\n"
    		+ "return fields: flight serial num, city and country of dep airport and dest airport, dep and land time and flight status\n"
    		+ "sorted by: main: country + city desc order.\n"
    		+ "           secnd: dep + land time desc order.\n";
    private Alert a = new Alert(AlertType.NONE);
    private Alert b = new Alert(AlertType.NONE);
    
    @FXML
    public void initialize() {
    	ArrayList<AirPort> airports = Getters.getInstance().getAirports();
    	ArrayList<String> countries = new ArrayList<String>();
    	for(AirPort ap : airports) {
    		if(!countries.contains(ap.getCountry())) {
    			countries.add(ap.getCountry());
    		}
    	}
    	ObservableList<String> countriesObs = FXCollections.observableArrayList(countries);	
		countryCmbx.setItems(FXCollections.observableArrayList(countriesObs));
    }
    
    /**
     * display info about the report
     * @param event
     */
    @FXML
    void CallreadMoreICn(MouseEvent event) {
    	
    	a.setAlertType(AlertType.INFORMATION);
    	a.setHeaderText("REPORTS INFO");
    	a.setContentText(massage + biggestFlightsInfo);
    	a.setTitle("Info");
    	a.setHeight(450);a.setWidth(450);
    	a.show();
    }

    /**
     * creating the report
     * @param event
     */
    @FXML
    void callBiggestFlightsReport(ActionEvent event) {

    	boolean datesheck = validateUntilafterFrom();
    	if(datesheck) {
    			FlightsByDepartureCountry chart = new FlightsByDepartureCountry(countryCmbx.getValue());
    			chart.pack();        
    			RefineryUtilities.centerFrameOnScreen(chart);        
    			chart.setVisible(true); 
    	}
    }

    @FXML
    void readMore(ActionEvent event) {

    	a.setAlertType(AlertType.INFORMATION);
    	a.setHeaderText("REPORTS INFO");
    	a.setContentText(massage + biggestFlightsInfo);
    	a.setTitle("Info");
    	a.setHeight(450);a.setWidth(450);
    	a.show();
    }

   ///////////////////////////////////////////////validating methods//////////////////////////////////////////////////////
    

    /**
     * validate dates for the report
     * @return true if valid
     */
    private boolean validateUntilafterFrom() {
    	
    	String from = countryCmbx.getValue();
    	if(from != null) {
    		return true;
    		}
    	missingFldWarning();
    	return false;
    }
    
    private void missingFldWarning() {
    	
    	a.setAlertType(AlertType.WARNING);
    	a.setHeaderText("MESSAGE");
		a.setContentText("Missing fields");
		a.setTitle("System MESSAGE");
		a.show();
    }

}
