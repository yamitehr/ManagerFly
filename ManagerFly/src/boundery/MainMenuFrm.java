package boundery;

import util.LoadPane;

import java.io.IOException;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainMenuFrm {

    @FXML
     public  BorderPane pannelRoot;
   
    @FXML
    private AnchorPane MainPane;
   
    @FXML
    public void initialize() {
		
    	//pannelRoot.setTop(GlyphsDude.createIcon(FontAwesomeIcons.HOME, "50px"));
    	
    }
    
    @FXML
    void closeApp(ActionEvent event) {
    	
    	Main.closeApp();
    }

    @FXML
    void goAirPorts(ActionEvent event) throws IOException {
    	
    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/AirPortsFrm.fxml");
    	pannelRoot.setCenter(root);
    }

    @FXML
    void goFlights(ActionEvent event) throws IOException {
    	
    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/FlightManagmentFrm.fxml");
    	pannelRoot.setCenter(root);
    }

    @FXML
    void goHome(ActionEvent event) throws IOException {
    	
    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/HomePage.fxml");
    	pannelRoot.setCenter(root);
    }

    @FXML
    void goPlanes(ActionEvent event) throws IOException {
    	
    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/AirPlanesFrm.fxml");
    	pannelRoot.setCenter(root);
    }

    @FXML
    void goReports(ActionEvent event) throws IOException {
    	
    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/ReprotsFrm.fxml");
    	pannelRoot.setCenter(root);
    }
    
    @FXML
    void loadAddFlight(ActionEvent event) throws IOException {

    	Pane root = LoadPane.LoadFXML(getClass(), "/boundery/AddFlightFrm.fxml");
    	pannelRoot.setCenter(root);
    }

}
