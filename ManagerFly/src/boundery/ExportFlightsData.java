package boundery;


import java.time.LocalDate;
import java.util.ArrayList;

import control.ExportControl;
import control.Getters;
import entity.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ExportFlightsData {
	
	@FXML
	Button exportBtn;
	
	@FXML
	private ListView<Flight> flights;
	
	@FXML
	public void initialize() 
	{

	}

	@FXML
	public void doingExport(MouseEvent event)
	{
		ExportControl.getInstance().exportFlightsToJSON();;
		
		 ArrayList<Flight> flightsAll = Getters.getInstance().getFlights();
		
		ObservableList<Flight> flightsSt = FXCollections.observableArrayList(flightsAll);
		flights.setItems(FXCollections.observableArrayList(flightsSt));
	}
	
}
