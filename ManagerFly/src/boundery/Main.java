/**
 * This class will run the flights management system
 */

package boundery;



import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

	public void start(Stage primaryStage) throws Exception {
		  
		
		Parent root = FXMLLoader.load(getClass().getResource(".fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());	
		primaryStage.setScene(scene);
		primaryStage.setTitle("Manager-Fly");
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.show();
		
	}
	
	
	
	public static void main(String args[]) {
		launch(args);
	}
}
