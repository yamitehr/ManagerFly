package boundery;

import util.LoadPane;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainMenuFrm {

    @FXML
    private BorderPane pannelRoot;

    @FXML
    void closeApp(ActionEvent event) {
    	
    	Main.closeApp();
    }

    @FXML
    void goAirPorts(ActionEvent event) throws IOException {
    	
    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/ManageAirPortsFrm.fxml");
    	pannelRoot.setCenter(root);
    }

    @FXML
    void goFlights(ActionEvent event) throws IOException {
    	
    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/AddFlightFrm.fxml");
    	pannelRoot.setCenter(root);
    }

    @FXML
    void goHome(ActionEvent event) throws IOException {
    	
    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/HomePage.fxml");
    	pannelRoot.setCenter(root);
    }

    @FXML
    void goPlanes(ActionEvent event) throws IOException {
    	
    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/ManageAirPlanesFrm.fxml");
    	pannelRoot.setCenter(root);
    }

    @FXML
    void goReports(ActionEvent event) throws IOException {
    	
    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/ReprotsFrm.fxml");
    	pannelRoot.setCenter(root);
    }

}
