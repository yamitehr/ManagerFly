package boundery;

import java.util.ArrayList;
import java.util.HashMap;

import control.Getters;
import entity.AirAttendant;
import entity.AirPort;
import entity.Employee;
import entity.Flight;
import entity.GroundAttendant;
import entity.Pilot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
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
    
    private ObservableList<String> empRolesList = FXCollections.observableArrayList("Air Attendant","Ground Attendant","pilot");
 
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
    
    private Tooltip pervtooltip;
    private Tooltip nexttooltip;
    private Tooltip searchtooltip;
    private ArrayList<Employee> empArrList;				//list of all employee
    private int currentEmpIndex;						//to indicate which employee to show from the list when clicking
    private Employee currentEmployee;					//'>' or '<' button
    private boolean inEditMode; 						//to determine if the user can add new employee
    private Alert a = new Alert(AlertType.NONE);		//for displaying pop up messages for the user
    private HashMap<String,Employee>empMap;
    
    @FXML
    public void initialize() {
		init();
    }
		

    private void init() {
    	
    	//initializing fields and data structures
    	ContractStartDate.setOpacity(1.0);
    	issuedDate.setOpacity(1.0);
    	notInEdit();
    	ArrayList<AirAttendant> airArr = Getters.getInstance().getAirAttendants();
    	ArrayList<GroundAttendant> groundArr = Getters.getInstance().getGroundAttendants();
    	ArrayList<Pilot> pilotsArr = Getters.getInstance().getPilots();
    	empArrList = new ArrayList<Employee>();
    	empArrList.addAll(pilotsArr);empArrList.addAll(groundArr);empArrList.addAll(airArr);
    	inEditMode = false;
    	pervtooltip = new Tooltip();
    	nexttooltip = new Tooltip();
    	searchtooltip = new Tooltip();
    	pervtooltip.setText("previous employee");
    	nexttooltip.setText("next employee");
    	searchtooltip.setText("search");
    	pervBtn.setTooltip(pervtooltip);
    	nextBtn.setTooltip(nexttooltip);
    	IDFld.setTooltip(searchtooltip);
    	currentEmpIndex = 0;
    	EmpRoleCombo.setItems(empRolesList);	
    	EmpCmbBx.getItems().addAll(empArrList);
    	if(!empArrList.isEmpty()) {
    		EmpCmbBx.setValue(empArrList.get(currentEmpIndex));
    		empMap = new HashMap<String,Employee>();
    		for(Employee emloyee: empArrList) {
    			empMap.put(emloyee.getID(), emloyee);
    		}
    		loadEmpByCmb(new ActionEvent());
    	}
    }
    
    @FXML
    void LoadEmp(KeyEvent event) {

    	String s = IDFld.getText();
		if(s != null && !s.isEmpty()) {
			Employee e = null;
			e = empMap.get(s);
			if(e != null) {
				currentEmployee = EmpCmbBx.getValue();
				currentEmpIndex = empArrList.indexOf(e);
				EmpCmbBx.setValue(e);
				notInEdit();
				setFields();				
			}
		}
    }

    @FXML
    void UpdateData(ActionEvent event) {

    }

    @FXML
    void addEmp(ActionEvent event) {

    }

    @FXML
    void loadEmpByCmb(ActionEvent event) {

    	if(EmpCmbBx.getValue() != null) {
    		currentEmployee = EmpCmbBx.getValue();
    		deterMineClass(currentEmployee);
    		currentEmpIndex = empArrList.indexOf(currentEmployee);
    		IDFld.setText(currentEmployee.getID() + "");
    		notInEdit();
    		setFields();
    	}
    }

    @FXML
    void loadEmptyFrm(ActionEvent event) {

    	inEdit();
    }

    @FXML
    void loadNextEmp(ActionEvent event) {

    	if((currentEmpIndex + 1) < empArrList.size()) {
    		currentEmpIndex++;
    		EmpCmbBx.setValue(empArrList.get(currentEmpIndex));
    		loadEmpByCmb(new ActionEvent());
    	}
    	else {
    		a.setAlertType(AlertType.INFORMATION);
    		a.setHeaderText("MESSAGE");
    		a.setTitle("SYSTEM MESSAGE");
    		a.setContentText("Last in the list!");
    		a.show();
    	}
    }

    @FXML
    void loadPervEmp(ActionEvent event) {

    	if((currentEmpIndex - 1) >= 0) {
    		currentEmpIndex--;
    		EmpCmbBx.setValue(empArrList.get(currentEmpIndex));
    		loadEmpByCmb(new ActionEvent());
    	}
    	else {
    		a.setAlertType(AlertType.INFORMATION);
    		a.setHeaderText("MESSAGE");
    		a.setTitle("SYSTEM MESSAGE");
    		a.setContentText("First in the list!");
    		a.show();
    	}
    }
    
    @FXML
    void changeAddMode(ActionEvent event) {

    	if(inEditMode == true)
    		inEdit();
    }
    
    //displayed when the append failed
  	private void faildtoAddMsg() {
  		
  		a.setAlertType(AlertType.ERROR);
  		a.setHeaderText("MESSAGE");
  		a.setTitle("SYSTEM MESSAGE");
  		a.setContentText("Operation Faild");
  		a.show();
  	}

  	 private void setFields() {
  		
  		 ContractStartDate.setValue(currentEmployee.getContractStart());
  		 ContractFinishDate.setValue(currentEmployee.getContractFinish());
  		fNameFld.setText(currentEmployee.getFirstName());
  		lNameFld.setText(currentEmployee.getLastName());
  		 
  	 }
  	 
  	 private void deterMineClass(Employee currentEmployee) {
  		 
  		 if(currentEmployee instanceof Pilot) {
  			PilotPane.setVisible(true);
  			EmpRoleCombo.setValue("pilot");
  			Pilot p = (Pilot)currentEmployee;
  			issuedDate.setValue(p.getIssuedDate());
  			LicenceIDFld.setText(p.getLicenceID());
  			firmTitle.setText("Pilot Details:");
  		 }
  		 else {
	  			PilotPane.setVisible(false);
	  		 if(currentEmployee instanceof AirAttendant) {
	  			EmpRoleCombo.setValue("Air Attendant");
	  			firmTitle.setText("Air Attendant Details:");
	  		 }
	  		 else {
	  			EmpRoleCombo.setValue("Ground Attendant");
	  			firmTitle.setText("Ground Attendant Details:");
	  		 }
  		 }
  	 }
  	 
  	private void inEdit() {
    	
  		inEditMode = true;
  		addBtn.setOpacity(1.0);
  		addBtn.setDisable(false);
  		updateBtn.setDisable(true);
  		updateBtn.setOpacity(0.4);
  		IDFld.setText(null);
  		LicenceIDFld.setEditable(true);
  		fNameFld.setText(null);
  		lNameFld.setText(null);
  		issuedDate.setDisable(false);
  		issuedDate.setEditable(true);
  		ContractStartDate.setEditable(true);
  		ContractStartDate.setDisable(false);
  		ContractStartDate.setValue(null);
  		ContractFinishDate.setValue(null);
  		LicenceIDFld.setText(null);
  		issuedDate.setValue(null);
  		String empType;
  		firmTitle.setText("Add New Employee:");
  		if(EmpRoleCombo.getValue().equals("pilot")) {
  			PilotPane.setVisible(true);
  			empType = "Pilot";
  		}
  		else {
  			PilotPane.setVisible(false);
	  		if(EmpRoleCombo.getValue().equals("Air Attendant"))
	  			empType = "Air Attendant";
	  		else
	  			empType = "Ground Attendant";
  		}
  		firmTitle.setText("Add New " + empType + ":");
    }
    
  	private void notInEdit() {
    	
  		inEditMode = false;
  		addBtn.setOpacity(0.4);
  		updateBtn.setDisable(false);
  		updateBtn.setOpacity(1.0);
  		addBtn.setDisable(true);
  		ContractStartDate.setEditable(false);
  		ContractStartDate.setDisable(true);
  		deterMineClass(currentEmployee);
  		LicenceIDFld.setEditable(false);
  		issuedDate.setDisable(true);
  		issuedDate.setEditable(false);
  		LicenceIDFld.setEditable(false);
    }
}
