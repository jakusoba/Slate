package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/** MainScreen FXML controller
        * @author obianuju akusoba */

public class MainScreenController implements Initializable{

    /**Sets the Stage and Scene.*/
    Parent root;
    Stage stage;

    /**FXML ID information*/
        @FXML
        private Button manageUsers_btn;

        @FXML
        private Button reporting_btn;

        @FXML
        private Button manageAppts_btn;

        @FXML
        private Button manageCustomers_btn;

        @FXML
        private Button exit_btn;

        /**This method exits the system and takes user login screen.*/
        @FXML
        private void exitSystem(ActionEvent event) throws SQLException, IOException {

            root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            stage = (Stage) exit_btn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        }
        /**This method shows the Appointment screen.*/
        @FXML
        private void loadAppointments(ActionEvent event) throws IOException,SQLException {
            root = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
            stage = (Stage)manageAppts_btn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        /**This method shows Customer screen.*/
        @FXML
        private void loadCustomers(ActionEvent event) throws IOException, SQLException {
            root = FXMLLoader.load(getClass().getResource("/view/ManageCustomer.fxml"));
            stage = (Stage)manageCustomers_btn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        /**This method shows the Reports screen.*/
        @FXML
        private void loadReports(ActionEvent event) throws IOException, SQLException {
            root = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
            stage = (Stage)reporting_btn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }



        /**This method initializes the controller class.*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}



