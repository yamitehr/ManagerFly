package boundery;


import java.time.LocalDate;

import control.ExportControl;
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
	private ListView<String> flights;
	
	@FXML
	public void initialize() 
	{

	}

	@FXML
	public void doingExport(MouseEvent event)
	{
		ExportControl.getInstance().exportFlightsToJSON();;
	}
	
}
