package boundery;

import static util.LoadPane.LoadFXML;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.LoadPane;

public class LoginFrm {

	@FXML
    private Button loginBtn;
	@FXML
	private TextField txtUserName;
	@FXML
	private PasswordField txtPassword;
	@FXML
    private Label lblStatus;
   
    @FXML
    public void initialize() {
    	
    }

    @FXML
    void login(ActionEvent event) throws IOException {
boolean isExist = false;
    	
    	if(txtUserName.getText().equals("flights") && txtPassword.getText().equals("flights")) {
    		moveToScene("/boundery/MainMenu.fxml", (Stage)loginBtn.getScene().getWindow());
    		isExist = true;
    	}
    	if(txtUserName.getText().equals("employees") && txtPassword.getText().equals("employees")) {
    		moveToScene("/boundery/MainMenuForEmloyeesManager.fxml", (Stage)loginBtn.getScene().getWindow());
    		isExist = true;
    	}
    	if(!isExist)
    		lblStatus.setText("Oops!");	

    }
    
    
    public void moveToScene(String fxmlName, Stage primaryStage) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlName));
	        primaryStage.setScene(new Scene(root));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
