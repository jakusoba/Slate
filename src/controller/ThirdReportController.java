package controller;

import DAO.Customer_DAO;
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
import model.CustomerReport;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 ThirdReport FXML Controller class
  @author Obianuju Akusoba
 */
public class ThirdReportController implements Initializable {
    /**Sets parent and stage.*/
    Parent root;
    Stage stage;

    /**FXML ID information*/
    @FXML
    private Label thirdReportTitle;

    @FXML
    private TableView<CustomerReport> customerReportTableView;

    @FXML
    private TableColumn<?, ?> custNameCol;

    @FXML
    private TableColumn<?, ?> startTimeCol;

    @FXML
    private TableColumn<?, ?> endTimeCol;

    @FXML
    private Button backBTN;

    /**This method displays the reports screen.**/
    @FXML
    private void returnToReports(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
        stage = (Stage)backBTN.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**This method initializes the controller class.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Loads data to the TableView.**/
        Customer_DAO customer_dao = new Customer_DAO();
        ObservableList<CustomerReport> observableList = customer_dao.getCustomerReport();

        custNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("End"));

        customerReportTableView.setItems(observableList);

    }
}


