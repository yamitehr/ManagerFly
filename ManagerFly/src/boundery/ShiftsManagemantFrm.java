package boundery;

import java.time.LocalDateTime;
import java.util.ArrayList;

import control.AssignLogic;
import control.FlightsLogic;
import control.Getters;
import entity.AirPlane;
import entity.GroundAttendant;
import entity.GroundAttendantInShift;
import entity.Shift;
import exceptions.InvalidInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import util.Alerts;
import util.Consts;

public class ShiftsManagemantFrm {

    @FXML
    private DatePicker start;

    @FXML
    private DatePicker end;
    
    @FXML
    private ComboBox<Integer> startHour;

    @FXML
    private ComboBox<Integer> startMinute;

    @FXML
    private ComboBox<Integer> endHour;

    @FXML
    private ComboBox<Integer> endMinute;
    @FXML
    private ComboBox<GroundAttendant> att;
    @FXML
    private ComboBox<String> role;
    @FXML
    private ListView<GroundAttendantInShift> allShifts;
    
    @FXML
    private Button saveBtn;
    @FXML
    private Alert a;

	@FXML
    public void initialize(){
		
		initShifts();
		initTime();
		initCmbx();
    }
    
	private void initShifts() {
		ObservableList<GroundAttendantInShift> attInShift = FXCollections.observableArrayList(Getters.getInstance().getGroundAttendantShifts());
		allShifts.setItems(FXCollections.observableArrayList(attInShift));
	}
	
	private void initTime(){
    	ArrayList<Integer> hoursList  = new ArrayList<>();
		ArrayList<Integer> minuteList  = new ArrayList<>();
		for(int i=0;i<24;i++) 
		{
			hoursList.add(i);
		}
		startHour.setItems(FXCollections.observableArrayList(hoursList));
		endHour.setItems(FXCollections.observableArrayList(hoursList));
		for(int i=0;i<60;i++) 
		{
			minuteList.add(i);
		}
		startMinute.setItems(FXCollections.observableArrayList(minuteList));
		endMinute.setItems(FXCollections.observableArrayList(minuteList));
	}
	
	private void initCmbx() {
		ObservableList<GroundAttendant> gatt = FXCollections.observableArrayList(Getters.getInstance().getGroundAttendants());
		att.setItems(FXCollections.observableArrayList(gatt));
		
		ObservableList<String> roles = FXCollections.observableArrayList(Consts.SHIFT_ROLE);
		role.setItems(FXCollections.observableArrayList(roles));
	}
	
	private void clear() {
		start.setValue(null);
		end.setValue(null);
		startHour.setValue(null);
		endHour.setValue(null);
		startMinute.setValue(null);
		endMinute.setValue(null);
		att.setValue(null);
		role.setValue(null);

		initTime();
		initCmbx();
	}
	
	@FXML
	private void saveShift(Event event) {
		try {
			if(start.getValue() == null) {
				throw new InvalidInputException("Please select Start Date");
			}
			if(end.getValue() == null) {
				throw new InvalidInputException("Please select End Date");
			}
			if(endHour.getValue() == null) {
				throw new InvalidInputException("Please select End Hour");
			}
			if(startMinute.getValue() == null) {
				throw new InvalidInputException("Please select Start Minute");
			}
			if(endMinute.getValue() == null) {
				throw new InvalidInputException("Please select End Minute");
			}
			if(startHour.getValue() == null) {
				throw new InvalidInputException("Please select Start Hour");
			}
			if(att.getValue() == null) {
				throw new InvalidInputException("Please select Ground Attendant");
			}
			if(role.getValue() == null) {
				throw new InvalidInputException("Please select Role");
			}
			
			LocalDateTime startDT = LocalDateTime.of(start.getValue().getYear(),
					start.getValue().getMonth(),
					start.getValue().getDayOfMonth(),
					startHour.getValue(),
                    startMinute.getValue());
			
			LocalDateTime endDT = LocalDateTime.of(end.getValue().getYear(),
					end.getValue().getMonth(),
					end.getValue().getDayOfMonth(),
					endHour.getValue(),
                    endMinute.getValue());
			
			if(AssignLogic.getInstance().addShift(new Shift(startDT, endDT)) &&
					AssignLogic.getInstance().addGroundAttendantToShift(new GroundAttendantInShift(new Shift(startDT, endDT), att.getValue(), role.getValue()))) {
				a = Alerts.infoAlert("Added Flight Successfully!");
	    		a.show();
	    		initShifts();
	    		clear();
			} else {
				throw new InvalidInputException("something went wrong while adding a new flight. Try a different ID");
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
