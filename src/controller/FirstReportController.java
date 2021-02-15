package controller;

import DAO.Appointment_DAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AppointmentReport;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FirstReport FXML Controller class
 *
 * @author Obianuju Akusoba
 */

public class FirstReportController implements Initializable {
    /*Sets parent and stage.**/
    Parent root;
    Stage stage;

    /*FXML ID information**/
    @FXML
    private Label firstReportTitle;

    @FXML
    private TableView<AppointmentReport> FirstTableView;

    @FXML
    private TableColumn<?, ?> monthColl;

    @FXML
    private TableColumn<?, ?> typeColl;

    @FXML
    private TableColumn<?, ?> countColl;

    @FXML
    private Button backBTN;

    /**This method returns user to the Reporting screen.*/
    @FXML
    private void returnReports(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
        stage = (Stage)backBTN.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**This method initializes the controller class.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Loads data to the TableView.
        Appointment_DAO appointment_dao = new Appointment_DAO();
        ObservableList<AppointmentReport> observableList = appointment_dao.getFirstReport();

        monthColl.setCellValueFactory(new PropertyValueFactory<>("Month"));
        typeColl.setCellValueFactory(new PropertyValueFactory<>("Type"));
        countColl.setCellValueFactory(new PropertyValueFactory<>("Total"));

        FirstTableView.setItems(observableList);

    }
}
