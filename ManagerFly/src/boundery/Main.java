/**
 * This class will run the flights management system
 */

package boundery;


import static util.LoadPane.LoadFXML;
import java.time.LocalDate;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.ReportsLogic;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.functions.standard.ReportCategory;

public class Main extends Application{
	static Stage stg;
	
	public void start(Stage primaryStage) throws Exception {
		 
		stg  = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());	
		primaryStage.setScene(scene);
		primaryStage.setTitle("Manager-Fly");
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e ->{
			
			e.consume();
		closeApp();});
		//set close option by clicking the Esc button
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
		  () {

		        @Override
		        public void handle(KeyEvent t) {
		          if(t.getCode()==KeyCode.ESCAPE)
		          {
		        	  closeApp();
		          }
		        }
		    });
		
	}
	
	
	
	public static void main(String args[]) {
		launch(args);
	}
	
	//close app method
		public static void closeApp() {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("SYSTEM MESSAGE");
			alert.setHeaderText("EXIT CONFIRMATION");
			alert.setContentText("Are you sure you want to close the app?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				stg.close();
			} 
			
		}
}
