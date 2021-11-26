package boundery;

import java.time.LocalDate;

import javax.swing.JFrame;

import control.ReportsLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import util.InputValidetions;

public class ReportsFrm {

    @FXML
    private DatePicker fromFld;

    @FXML
    private Button BiggestFlightsReportBtn;

    @FXML
    private DatePicker untilFld;

    @FXML
    private TextField seatsNumFld;

    @FXML
    private ImageView readMoreIcn;

    @FXML
    private Button readMoreBtn;
    
    private LocalDate from;
    private LocalDate until;
    private int seatsNum = 0;
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

    	boolean numCheck = validateSeatsFld();
    	boolean datesheck = validateUntilafterFrom();
    	if(numCheck &&  datesheck) {
    			from = fromFld.getValue();
    			until = untilFld.getValue();
    			seatsNum = Integer.parseInt(seatsNumFld.getText());
    			JFrame fram = ReportsLogic.getInstance().compileBiggestFlights(seatsNum, from, until);
    			fram.setVisible(true);
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
     * validate seats amount
     * @return true if valid
     */
    private boolean validateSeatsFld() {

    	String s = seatsNumFld.getText();
    	if(s != null) {
    		boolean ans = InputValidetions.validatePositiveIntegerOrZero(s);
    		if(ans == false) {
    			a.setAlertType(AlertType.ERROR);
    			a.setHeaderText("INPUT RERROR");
    			a.setContentText("must be positive integer or 0!");
    			a.setTitle("ERROR MESSAGE");
    			a.show();
    			seatsNumFld.setText(null);
    			return false;
    		}
    		return true;
    	}
    	missingFldWarning();
    	return false;
    }

    /**
     * validate dates for the report
     * @return true if valid
     */
    private boolean validateUntilafterFrom() {
    	
    	LocalDate from = fromFld.getValue();
    	LocalDate until = untilFld.getValue();
    	if(until != null && from != null) {
    		boolean ans = InputValidetions.validateLastAferFirst(from.atTime(0,0,0), until.atTime(0,0,0));
    		if(ans == false){
    			b.setAlertType(AlertType.ERROR);
    			b.setHeaderText("INPUT RERROR");
    			b.setContentText("until date must be greater than from date");
    			b.setTitle("ERROR MESSAGE");
    			b.show();
    			untilFld.setValue(null);untilFld.setPromptText(null);
    			return false;
    		}
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
