package util;

import java.io.IOException;
import java.util.function.UnaryOperator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextFormatter.Change;

/* utility class globally and commonly used methods are implemented here*/
public final class LoadPane {
	
	// private constructor to prevent creation of object
	private LoadPane() {
		
	}
	
	
	public static <T extends Node> T LoadFXML(Class<?> cls, String path) throws IOException {
		return FXMLLoader.load(cls.getResource(path));
	}
}