package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	public static Alert infoAlert(String message) {
		Alert a = new Alert(AlertType.INFORMATION);
		a.setHeaderText("SUCCESS");
		a.setTitle("SYSTEM MESSAGE");
		a.setContentText(message);
		return a;
	}
	
	public static Alert errorAlert(String message) {
		Alert a = new Alert(AlertType.ERROR);
  		a.setHeaderText("ERROR");
  		a.setTitle("INPUT ERROR");
		a.setContentText(message);
		return a;
	}
}
