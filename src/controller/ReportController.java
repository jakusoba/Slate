package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Report FXML FXML Controller class
 *
 * @author Obianuju Akusoba
 */

public class ReportController implements Initializable {

    /*Sets Stage and scene.**/
    Parent root;
    Stage stage;

    /* FXML ID information**/

    @FXML
    private Label title_Lbl;

    @FXML
    private Button appointmenttMonthBtn;

    @FXML
    private Button appointtmentContactBtn;

    @FXML
    private Button appointmentCusBtn;

    @FXML
    private Button backBtn;

    /**This method takes user back to the main screen.*/
    @FXML
    void returnToMainScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
        stage = (Stage)backBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**This method loads a screen to view appointments by type and month.*/
    @FXML
    private void viewApptByTypeAndMonth(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/FirstReport.fxml"));
        stage = (Stage)appointmenttMonthBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**This method loads a screen to view each contact appointment schedule.*/
    @FXML
    private void viewContactAppointments(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/SecondReport.fxml"));
        stage = (Stage)appointtmentContactBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**This method loads a screen to view customer appointment schedule.*/
    @FXML
    private void viewCustomerAppointments(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/ThirdReport.fxml"));
        stage = (Stage)appointtmentContactBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    /**This method initializes the controller.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


