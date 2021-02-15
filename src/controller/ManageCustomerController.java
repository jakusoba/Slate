package controller;

import DAO.Appointment_DAO;
import DAO.Customer_DAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**ManageCustomer FXML Controller class
 @author Obianuju Akusoba
 */
public class ManageCustomerController implements Initializable {

    /**Sets Stage and scene.*/
    Parent root;
    Stage stage;

    /** FXML ID information*/
    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<?, ?> custId_col;

    @FXML
    private TableColumn<?, ?> custName_col;

    @FXML
    private TableColumn<?, ?> custAdd_col;

    @FXML
    private TableColumn<?, ?> custPostalCode_col;

    @FXML
    private TableColumn<?, ?> custPhone_col;

    @FXML
    private TableColumn<?, ?> custDivisionID_col;

    @FXML
    private Button addCustomer_btn;

    @FXML
    private Button updateCustomer_btn;

    @FXML
    private Button deleteCustomer_btn;

    @FXML
    private Button mainMenu_btn;

    /**This method takes user to the Add customer screen.*/
    @FXML
    private void addNewCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage = (Stage) addCustomer_btn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**This method deletes the selected customer.*/
    @FXML
    private void deleteCustomer(ActionEvent event) {

        if(customerTableView.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
            return;

        }


        else {
            String customerToDelete = customerTableView.getSelectionModel().getSelectedItem().getCustomer_ID();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setContentText("This will delete the customer record and appointments associated the customer.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK);
            Customer_DAO customer_dao = new Customer_DAO();
            customer_dao.deleteCustomer(customerToDelete);

            ObservableList<Customer> cusDetail = customer_dao.getAllCustomers();

            custId_col.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            custName_col.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            custAdd_col.setCellValueFactory(new PropertyValueFactory<>("Address"));
            custPostalCode_col.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
            custPhone_col.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            custDivisionID_col.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
            custAdd_col.setCellValueFactory(new PropertyValueFactory<>("Address"));
            customerTableView.setItems(cusDetail);
        }




    }


    /**This method takes user back to the main screen.*/
    @FXML
    private void returnMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
        stage = (Stage)mainMenu_btn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**This method updates the selected customer.*/
    @FXML
    private void updateCustomer(ActionEvent event) throws IOException {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if(customerTableView.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select an customer to update.");
            alert.showAndWait();
            return;
        }

        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
            loader.load();
            UpdateCustomerController Customercontroller = loader.getController();
            Customercontroller.getCustomersToForm(customer);
            stage = (Stage) updateCustomer_btn.getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }


    }

    Customer_DAO customer_dao = new Customer_DAO();
    /**This method initializes the controller and loads customer data into tableview using an observable list.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> custDetail = customer_dao.getAllCustomers();

        custId_col.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        custName_col.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        custAdd_col.setCellValueFactory(new PropertyValueFactory<>("Address"));
        custPostalCode_col.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        custPhone_col.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        custDivisionID_col.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
        customerTableView.setItems(custDetail);

    }
}
