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
        import model.ContactReport;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.ResourceBundle;
/**
  SecondReport FXML Controller class
 @author Obianuju Akusoba
 */
public class SecondReportController implements Initializable {


    /**Sets Stage and scene.*/
    Parent root;
    Stage stage;

    /**FXML ID information.*/
    @FXML
    private Label secondReportTitle;

    @FXML
    private TableView<ContactReport> contactsTableView;

    @FXML
    private TableColumn<?, ?> appIDColumn;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> descColumn;

    @FXML
    private TableColumn<?, ?> startColumn;

    @FXML
    private TableColumn<?, ?> endColumn;

    @FXML
    private TableColumn<?, ?> custColumn;

    @FXML
    private TableColumn<?, ?> contactColumn;

    @FXML
    private Button backBTN;

    /**This method displays the reports screen.**/
    @FXML
    private void returnReports(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
        stage = (Stage)backBTN.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**This method initializes the controller class and loads data to the TableView.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Loads data to the TableView.**/
        Appointment_DAO appointment_dao = new Appointment_DAO();
        ObservableList<ContactReport>aptByContactData = null;
        try {
            aptByContactData = appointment_dao.getContactAppointments();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        appIDColumn.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        custColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        contactsTableView.setItems(aptByContactData);





    }
}

