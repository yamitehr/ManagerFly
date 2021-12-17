package boundery;

import entity.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class EmployeesManagmentFrm {

    @FXML
    private AnchorPane ManagmentPane;

    @FXML
    private Label firmTitle;
    
    @FXML
    private TextField IDFld;

    @FXML
    private ComboBox<Employee> EmpCmbBx;

    @FXML
    private DatePicker ContractFinishDate;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField fNameFld;

    @FXML
    private TextField lNameFld;

    @FXML
    private Button nextBtn;

    @FXML
    private Button pervBtn;

    @FXML
    private DatePicker ContractStartDate;

    @FXML
    private ComboBox<String> EmpRoleCombo;

    @FXML
    private Pane PilotPane;

    @FXML
    private TextField LicenceIDFld;

    @FXML
    private DatePicker issuedDate;

    @FXML
    private Button loadEmptyFrmBtn;

    @FXML
    private Button addBtn;

    @FXML
    void LoadEmp(KeyEvent event) {

    }

    @FXML
    void UpdateData(ActionEvent event) {

    }

    @FXML
    void addEmp(ActionEvent event) {

    }

    @FXML
    void loadEmpByCmb(ActionEvent event) {

    }

    @FXML
    void loadEmptyFrm(ActionEvent event) {

    }

    @FXML
    void loadNextEmp(ActionEvent event) {

    }

    @FXML
    void loadPervEmp(ActionEvent event) {

    }

}
