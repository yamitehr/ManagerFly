package boundery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import control.AssignLogic;
import control.Getters;
import entity.AirAttendant;
import entity.AirPort;
import entity.Employee;
import entity.Flight;
import entity.GroundAttendant;
import entity.Pilot;
import exceptions.InvalidInputException;
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
import util.Alerts;
import util.Consts;
import util.InputValidetions;

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

    	String id = IDFld.getText();
    	LocalDate finish  = ContractFinishDate.getValue();
    	String fName = fNameFld.getText();
    	String lName = lNameFld.getText();
    	String query = (EmpRoleCombo.getValue().equals("pilot")) ? 
    			Consts.SQL_UPD_PILOT:
    				(EmpRoleCombo.getValue().equals("Air Attendant")) 
    				? Consts.SQL_UPD_AIRATTENDANT: Consts.SQL_UPD_GROUNDATTENDANT;
	    try {	
	    	if(id != null && !id.isEmpty() && empMap.containsKey(id) 
		    			&& InputValidetions.validateName(fName) == true 
		    			&& InputValidetions.validateName(lName) == true && fName != null && lName != null && !fName.isEmpty() && !lName.isEmpty()) {
		    		if((finish != null && finish.isAfter(ContractStartDate.getValue())) || finish == null) {
		    			if(AssignLogic.getInstance().editEmployee(id, fName, lName, finish, query)) {
		    				a = Alerts.infoAlert("Updated successfully!");
		    				a.show();
		    				currentEmployee.setFirstName(fName);currentEmployee.setLastName(lName);currentEmployee.setContractFinish(finish);
		    			}
		    			else {
		    				throw new Exception();
		    			}
		    		}
		    		else {
		    			throw new InvalidInputException("contract finish date must be after contract start date!");
		    		}
		    	}
	    	else {
	    		throw new InvalidInputException("Invalid input!");
	    	}
	    }catch(InvalidInputException inputE) {
	    	a = Alerts.errorAlert(inputE.getMessage());
    		a.show();
	    }catch(Exception e) {
	    	a = Alerts.errorAlert("Update Failed!");
    		a.show();
	    }
    }

    @FXML
    void addEmp(ActionEvent event) {
    	
    	String id = IDFld.getText();
    	LocalDate start  = ContractStartDate.getValue();
    	LocalDate finish  = ContractFinishDate.getValue();
    	String fName = fNameFld.getText();
    	String lName = lNameFld.getText();
    	String issued = null;
    	LocalDate issuedDate = null;
    	int type = (EmpRoleCombo.getValue().equals("pilot")) ? 1:(EmpRoleCombo.getValue().equals("Air Attendant")) ? 2: 3;
    	if(type == 1) {
    		issued = LicenceIDFld.getText();
    		issuedDate = this.issuedDate.getValue();
    	}
    	boolean ans = validateFields(id,start,finish,fName,lName,issued,issuedDate,type);
    	if(ans == true) {
    		if(type == 1) {
    			ans = AssignLogic.getInstance().addPilot(id, fName, lName, start, finish, issued, issuedDate);
    			Pilot p = new Pilot(id, fName, lName, start, finish, issued, issuedDate);
    			addtoDataStructures(p);
    		}
    		else if(type == 2) {
    			ans = AssignLogic.getInstance().addAirAttendant(id, fName, lName, start, finish);
    			AirAttendant at = new AirAttendant(id, fName, lName, start, finish);
    			addtoDataStructures(at);
    		}
    		else {
    			ans = AssignLogic.getInstance().addGroundAttendant(id, fName, lName, start, finish);
    			GroundAttendant ga = new GroundAttendant(id, fName, lName, start, finish);
    			addtoDataStructures(ga);
    		}
    		if(ans == true) {
    			a = Alerts.infoAlert("Added successfully!");
    			a.show();
    			notInEdit();
        		EmpCmbBx.setValue(EmpCmbBx.getItems().get(empArrList.size() -1 ));
        		loadEmpByCmb(new ActionEvent());
    		}
    		else {
    			a = Alerts.errorAlert("Failed to add!");
    			a.show();
    		}
    	}
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
  	
  	private boolean validateFields(String id, LocalDate start, LocalDate finish, String fName, String lName, String issued, LocalDate issuedDate, int type) {
  		try {
    		if(id == null || id.isEmpty() || start == null || fName.isEmpty() || fName == null || lName.isEmpty() || lName == null)
    			throw new InvalidInputException("empty fields");
    		if(type == 1) {
    			if(issued.isEmpty() || issued == null || issuedDate == null )
    				throw new InvalidInputException("empty fields");
    			if(issuedDate.isAfter(start))
    				throw new InvalidInputException("Licence issued date must be before or equal to contract start date!");
    			if(issued.length() > 12)
    				throw new InvalidInputException("max field size of Licence ID  is 12!");
    			if(InputValidetions.validatePositiveIntegerOrZero(issued) == false)
    				throw new InvalidInputException("Licence ID  can contain digits only!");
    		}
    		if(InputValidetions.validateName(fName) == false || InputValidetions.validateName(lName) == false)
    			throw new InvalidInputException("name must contain letters only!");
    		if(finish != null &&(finish.isBefore(start)))
    			throw new InvalidInputException("contract finish date must be after contract start date!");
    		if(InputValidetions.validatePositiveIntegerOrZero(id) == false) {
    			throw new InvalidInputException("Licence ID  can contain digits only!");
    		}
    		if(id.length() != 9) {
    			throw new InvalidInputException("id must conatin 9 digits!");
    		}
    		
    	}catch(InvalidInputException inputE) {
    		a = Alerts.errorAlert(inputE.getMessage());
    		a.show();
    		return false;
    	}catch(Exception e) {
    		a = Alerts.errorAlert("error");
    		a.show();
    		return false;
    	}
  		return true;
  	}
  	
  	private void addtoDataStructures(Employee emp) {
  		
  		if(emp != null) {
	  		empMap.put(emp.getID(), emp);
	  		empArrList.add(emp);
	  		EmpCmbBx.getItems().add(emp);
  		}
  	}
}
