package controller;

import DAO.Country_DAO;
import DAO.Customer_DAO;
import DAO.First_Level_Divisions_DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.First_Levels_Divisions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**Update appointment FXML Controller class
@author Obianuju Akusoba*/

public class UpdateCustomerController implements Initializable {
    /**Sets Stage and scene.*/
    Parent root;
    Stage stage;

    @FXML
    private TextField customerId_txt;

    @FXML
    private TextField customerName_txt;

    @FXML
    private ComboBox<Country> country_combo;

    @FXML
    private TextField customerPhone_txt;

    @FXML
    private TextField customerAddress_txt;

    @FXML
    private ComboBox<First_Levels_Divisions> DivisionID_combo;

    @FXML
    private TextField customerPostalCode_txt;

    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;

    First_Level_Divisions_DAO fl = new First_Level_Divisions_DAO();
    ObservableList<First_Levels_Divisions> masterListOfDivisions = fl.getAllDivisionsData();

    /**This method loads the manage customer screen.*/
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/ManageCustomer.fxml"));
        stage = (Stage) cancel_btn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**This method saves updated customer information.*/
    @FXML
    private void saveCustomer(ActionEvent event) throws IOException {
        String Customer_ID = customerId_txt.getText();
        String Customer_Name = customerName_txt.getText();
        String Customer_Address = customerAddress_txt.getText();
        String Postal_Code = customerPostalCode_txt.getText();
        String Customer_Phone = customerPhone_txt.getText();
        //String chosenDivision = String.valueOf(DivisionID_combo.getSelectionModel().getSelectedItem());
        String chosenDivision = String.valueOf(DivisionID_combo.getSelectionModel().getSelectedItem().getDivision_ID());

        Customer_DAO customer_dao = new Customer_DAO();

        customer_dao.updateCustomer(Customer_ID, Customer_Name, Customer_Address, Postal_Code, Customer_Phone, chosenDivision );

        /**FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ManageCustomer.fxml"));
        loader.load();
        stage = (Stage)save_btn.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();*/

        root = FXMLLoader.load(getClass().getResource("/view/ManageCustomer.fxml"));
        stage = (Stage) save_btn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**This method filters the Divisions based on the selected country.*/
    @FXML
    private void chooseState(ActionEvent event) {

        ObservableList<First_Levels_Divisions> listOfDivisions = FXCollections.observableArrayList();
        DivisionID_combo.getItems().removeAll(DivisionID_combo.getItems());

        String tempCountry = country_combo.getValue().toString();
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


    /**This method initializes the controller class.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Loads customer data into combo box using an observable list.*/
        Country_DAO country_dao = new Country_DAO();
        ObservableList<Country> listOfCountries = country_dao.getAllCountries();
        country_combo.setItems(listOfCountries);


        First_Level_Divisions_DAO fl = new First_Level_Divisions_DAO();
        ObservableList<First_Levels_Divisions> lOfDivisions = fl.getAllDivisions();
        DivisionID_combo.setItems(lOfDivisions);

    }


    /**This method loads all of the original customer information on the update form.*/
    public void getCustomersToForm(Customer customer) {
        customerId_txt.setText(String.valueOf(customer.getCustomer_ID()));
        customerName_txt.setText(customer.getCustomer_Name());
        customerAddress_txt.setText(customer.getAddress());
        customerPostalCode_txt.setText(customer.getPostal_Code());
        customerPhone_txt.setText(customer.getPhone());
        //DivisionID_combo.setValue(customer.getDivision_ID().toString());

    }


}

