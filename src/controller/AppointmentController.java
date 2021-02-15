package controller;

import DAO.Appointment_DAO;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/**
 * Appointment FXML Controller class
 *
 * @author Obianuju Akusoba
 */

public class AppointmentController implements Initializable {
    /*Sets parent and stage.**/
    Parent root;
    Stage stage;



    /* FXML ID information**/
    @FXML
    private Label AppointmentScreenLabel;

    @FXML
    private Button UpdateAppointmentButton;

    @FXML
    private Button AddNewAppointmentButton;

    @FXML
    private Button AppointmentsBackButton;

    @FXML
    private Button DeleteAppointmentButton;

    @FXML
    private TableView<Appointment> AppointmentTableView;

    @FXML
    private TableColumn<?, ?> Appointment_IDColumn;

    @FXML
    private TableColumn<?, ?> AppointmentTitleColumn;

    @FXML
    private TableColumn<?, ?> AppointmentDescriptionColumn;

    @FXML
    private TableColumn<?, ?> AppointmentLocationColumn;

    @FXML
    private TableColumn<?, ?> AppointmentTypeColumn;

    @FXML
    private TableColumn<?, ?> AppointmentStartColumn;

    @FXML
    private TableColumn<?, ?> AppointmentEndColumn;

    @FXML
    private TableColumn<?, ?> AppointmentCustomerIDColumn;

    @FXML
    private ToggleGroup selectView;


    @FXML
    private RadioButton AppointmentWeekRadioButton;

    @FXML
    private RadioButton AppointmentMonthRadioButton;

    private final DateTimeFormatter datetimeStyle = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ZoneId localZoneID = ZoneId.systemDefault();
    private final ZoneId utcZoneID = ZoneId.of("UTC");


    /**This method takes user to the add appointment screen. */
    @FXML
    private void AddNewAppointmentButtonHandler(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/view/AddNewAppointment.fxml"));
        stage = (Stage) AddNewAppointmentButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**This method filters appointment by month. This is the first Lambda Expression used to cycle through a filtered ObservableList to filter TableView to a Month View of Appointments. It uses Lambda expression to cycle through a filtered ObservableList to display appointments by months.*/

    @FXML
    private void AppointmentMonthRadioButtonHandler(ActionEvent event) {
        ObservableList<Appointment> apptDetail = appointment_dao.getAllAppointments();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime addOneMonthToNow = now.plusMonths(1);

        /*Lambda expression 1 is used here to cycle through a filtered ObservableList to sort and display appointments by months.**/
        FilteredList<Appointment> filteredData = new FilteredList<>(apptDetail);
        filteredData.setPredicate(row -> {
            LocalDateTime startPeriod = LocalDateTime.parse(row.getStart(), datetimeStyle);
            /*The from() method of ChronoLocalDate interface in java method obtains an instance of ChronoLocalDate from a temporal object.**/
            return startPeriod.isAfter(now.minusDays(1)) && startPeriod.isBefore(addOneMonthToNow);
        });
        /*Loads filtered data to the Appointment table view.**/
        AppointmentTableView.setItems(filteredData);

    }

    /**This method filters appointment by month. This is the second Lambda Expression used to cycle through a filtered ObservableList to filter TableView to a Week View of Appointments. It uses Lambda expression to cycle through a filtered ObservableList to display appointments by week.*/

    @FXML
    private void AppointmentWeekRadioButtonHandler(ActionEvent event) {
        ObservableList<Appointment> apptDetail = appointment_dao.getAllAppointments();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime week = now.plusWeeks(1);
        /**Lambda expression 2 is used here to cycle through a filtered ObservableList to display appointments by week.*/
        FilteredList<Appointment>filteredData = new FilteredList<>(apptDetail);
        filteredData.setPredicate(row -> {
            LocalDateTime startPeriod = LocalDateTime.parse(row.getStart(), datetimeStyle);
            return startPeriod.isAfter(now.minusDays(1)) && startPeriod.isBefore(week);
        });
        /**Loads filtered data to the Appointment table view.*/
        AppointmentTableView.setItems(filteredData);
    }

    /**This method returns user to the main screen.*/
    @FXML
    private void AppointmentsBackButtonHandler(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
        stage = (Stage)AppointmentsBackButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**This method deletes selected appointment.*/
    @FXML
    private void DeleteAppointmentButtonHandler(ActionEvent event) {
        if(AppointmentTableView.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select an appointment to delete.");
            alert.showAndWait();
            return;
        }
        else {
            int appointmentToDelete = AppointmentTableView.getSelectionModel().getSelectedItem().getAppointment_ID();
            String appointmentToDeleteType = AppointmentTableView.getSelectionModel().getSelectedItem().getType();
            Appointment_DAO appointment_dao = new Appointment_DAO();


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Appointment ID " + String.valueOf(appointmentToDelete) + " of " + "type " + appointmentToDeleteType + " will be deleted");
            alert.showAndWait();

            appointment_dao.deleteAppointment(appointmentToDelete);

            ObservableList<Appointment> apptDetail = appointment_dao.getAllAppointments();

            Appointment_IDColumn.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            AppointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
            AppointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
            AppointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
            AppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
            AppointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
            AppointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
            AppointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            AppointmentTableView.setItems(apptDetail);

        }

    }


    /**This method takes user to update appointment screen.*/
    @FXML
    private void UpdateAppointmentButtonHandler(ActionEvent event) throws IOException {
        Appointment appointment = AppointmentTableView.getSelectionModel().getSelectedItem();
        //Verifies that user has selected a part to be updated.
        if(AppointmentTableView.getSelectionModel().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select an appointment to update.");
            alert.showAndWait();
            return;


        }
        else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
            loader.load();

            UpdateAppointmentController Appointmentcontroller = loader.getController();
            Appointmentcontroller.getAppointmentsToForm(appointment);

            stage = (Stage)UpdateAppointmentButton.getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }


    }

    Appointment_DAO appointment_dao = new Appointment_DAO();

    /**This method initializes the controller class.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Loads Data into TableView from the DB query result.
        ObservableList<Appointment> apptDetail = appointment_dao.getAllAppointments();

        Appointment_IDColumn.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        AppointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        AppointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        AppointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        AppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        AppointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        AppointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        AppointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        AppointmentTableView.setItems(apptDetail);


    }
}

