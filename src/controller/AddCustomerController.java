package controller;

import DAO.Country_DAO;
import DAO.Customer_DAO;
import DAO.First_Level_Divisions_DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Country;
import model.First_Levels_Divisions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * AddCustomerController FXML class
 *
 * @author Obianuju Akusoba
 */

public class AddCustomerController implements Initializable {
    /*Sets stage and parent.**/

    Parent root;
    Stage stage;

    /**
     * Add Customer FXML Controller class
     *
     * @author Obianuju Akusoba
     */

    /* FXML ID information**/
    @FXML
    private TextField customer_Name_txt;



    @FXML
    private ComboBox<Country> Country_combo;

    @FXML
    private TextField customer_Phone_txt;

    @FXML
    private TextField customer_Address_txt;

    @FXML
    private ComboBox<First_Levels_Divisions> DivisionID_combo;

    @FXML
    private TextField customerPostalCode_txt;

    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;

    /*This method returns user to the manage customer screen.**/
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/ManageCustomer.fxml"));
        stage = (Stage)cancel_btn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    First_Level_Divisions_DAO fl = new First_Level_Divisions_DAO();
    ObservableList<First_Levels_Divisions> masterListOfDivisions = fl.getAllDivisionsData();


    /**This method saves new customer data to the database.*/
    @FXML
    private void saveCustomer(ActionEvent event) throws IOException {
        if(customer_Name_txt.getText().isEmpty() || customer_Phone_txt.getText().isEmpty()|| customerPostalCode_txt.getText().isEmpty() ||
                customer_Address_txt.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please fill all fields of the form.");
            alert.showAndWait();
        }
        else {

            String Customer_Name = customer_Name_txt.getText();
            String Customer_Address = customer_Address_txt.getText();
            String Postal_Code = customerPostalCode_txt.getText();
            String Customer_Phone = customer_Phone_txt.getText();
            int chosenDivision = DivisionID_combo.getSelectionModel().getSelectedItem().getDivision_ID();

            Customer_DAO.insertCustomer(Customer_Name, Customer_Address, Postal_Code, Customer_Phone, chosenDivision );

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ManageCustomer.fxml"));
            loader.load();
            stage = (Stage)save_btn.getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    /**
     * This method initializes the controller class.
     */

    @FXML
    private void chooseState(ActionEvent event) {

        ObservableList<First_Levels_Divisions> listOfDivisions = FXCollections.observableArrayList();
        DivisionID_combo.getItems().removeAll(DivisionID_combo.getItems());

        String tempCountry = Country_combo.getValue().toString();
        if (tempCountry == null) {
            DivisionID_combo.setItems(masterListOfDivisions);
        }

        else if (tempCountry.equals("U.S")) {
            DivisionID_combo.setDisable(false);
            for(First_Levels_Divisions fLD : masterListOfDivisions) {
                First_Levels_Divisions tempFLD = fLD;
                if (tempFLD.getCountry_ID() == 1) {
                    listOfDivisions.add(fLD);
                }
            }

            DivisionID_combo.setItems(listOfDivisions);

        }
        else if (tempCountry.equals("UK")) {
            DivisionID_combo.setDisable(false);
            System.out.println(masterListOfDivisions);
            for (First_Levels_Divisions fLD : masterListOfDivisions) {
                First_Levels_Divisions tempFLD = fLD;
                if (tempFLD.getCountry_ID() == 2) {
                        listOfDivisions.add(tempFLD);
                }
            }

            DivisionID_combo.setItems(listOfDivisions);


        }
        else if (tempCountry.equals("Canada")) {
            DivisionID_combo.setDisable(false);
            for (First_Levels_Divisions fLD : masterListOfDivisions) {
                First_Levels_Divisions tempFLD = fLD;
                if (tempFLD.getCountry_ID() == 3) {
                        listOfDivisions.add(tempFLD);
                }
            }

            DivisionID_combo.setItems(listOfDivisions);

        } else {
                System.out.println("Incorrect combobox selection");
            }

        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Country_DAO country_dao = new Country_DAO();
        ObservableList<Country> listOfCountries = country_dao.getAllCountries();
        Country_combo.setItems(listOfCountries);

        First_Level_Divisions_DAO fl = new First_Level_Divisions_DAO();
        ObservableList<First_Levels_Divisions> lOfDivisions = fl.getAllDivisions();
        DivisionID_combo.setItems(lOfDivisions);


    }

}

