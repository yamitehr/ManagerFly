package boundery;

import java.util.ArrayList;
import java.util.HashMap;

import control.Getters;
import control.ImportXML;
import entity.AirPlane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ImportFlightStatusFromXML {
	@FXML
	private Button importBtn;
	@FXML
	private ListView<String> flights;
	
	@FXML
    public void initialize(){
		
    }
	@FXML
	public void importBtn(Event event) {
		HashMap<String, String> results = ImportXML.getInstance().importFlightsFromXML();
		ArrayList<String> toshow = new ArrayList<String>();
		for(String key : results.keySet()) {
			toshow.add( "             " + results.get(key) + "                                 " + key);
		}
		
		ObservableList<String> flightsSt = FXCollections.observableArrayList(toshow);
		flights.setItems(FXCollections.observableArrayList(flightsSt));
	}
	
}
