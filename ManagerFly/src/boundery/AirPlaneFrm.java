package boundery;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class AirPlaneFrm {

    @FXML
    private TextField attNumFld;

    @FXML
    private TextField IDFld;

    @FXML
    private ComboBox<Integer> attNumcoboBox;

    @FXML
    private Button loadEmptyFrmBtn;

    @FXML
    private Button saveAirPlane;

    @FXML
    private Button nextBtn;

    @FXML
    private Button pervBtn;

    @FXML
    private Pane seatsPane;

    @FXML
    private TableView<?> seatsTable;

    @FXML
    private TableColumn<?, ?> ID;

    @FXML
    private TableColumn<?, ?> seatRow;

    @FXML
    private TableColumn<?, ?> seatCol;

    @FXML
    private TableColumn<?, ?> seatType;

    @FXML
    private Pane addSeatsPane;

    @FXML
    private ComboBox<Integer> bsnsCombo;

    @FXML
    private ComboBox<Integer> firstClassCombo;

    @FXML
    private ComboBox<Integer> TouristsCombo;

    @FXML
    private ComboBox<Integer> totalCollsCombo;

    @FXML
    void LoadPlane(KeyEvent event) {

    }

    @FXML
    void addAirPlane(ActionEvent event) {

    }

    @FXML
    void loadEmptyFrm(ActionEvent event) {

    }

    @FXML
    void loadNextAirPlane(ActionEvent event) {

    }

    @FXML
    void loadPervAirPlane(ActionEvent event) {

    }

}
