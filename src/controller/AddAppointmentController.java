package controller;

import DAO.Appointment_DAO;
import DAO.Contact_DAO;
import DAO.User_DAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;
/**
 * AddAppointment FXML Controller class
 *
 * @author Obianuju Akusoba
 */
public class AddAppointmentController implements Initializable {

    /* Sets stage and parent.**/
    Parent root;
    Stage stage;


    /* FXML ID information**/
    @FXML
    private ComboBox<String> contactID_combo;

    @FXML
    private ComboBox<String> userID_combo;

    //@FXML
    //private int apptID_txt;

    @FXML
    private TextField apptTitle_txt;

    @FXML
    private TextField apptDesc_txt;

    @FXML
    private TextField apptLocation_txt;

    @FXML
    private TextField apptType_txt;

    @FXML
    private TextField customerID;

    @FXML
    private TextField start_timeV;

    @FXML
    private TextField end_timeV;

    @FXML
    private DatePicker startDate_dp;


    @FXML
    private DatePicker endDate_dp;


    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;

    /**This method returns user to the main screen.*/
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        stage = (Stage)cancel_btn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**This method checks for overlapping appointment.*/
    public boolean isOverlap() {
        Appointment_DAO appointment_dao = new Appointment_DAO();
        ObservableList<Appointment> appointmentList = appointment_dao.getAllAppointments();
        for(Appointment appointment : appointmentList) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime appointmentListStartDate = LocalDateTime.parse(appointment.getStart(), dateTimeFormatter);
            LocalDateTime appointmentListEndDate = LocalDateTime.parse(appointment.getEnd(), dateTimeFormatter);

            LocalDate chosenStartDate = startDate_dp.getValue();
            LocalDate chosenEndDate = endDate_dp.getValue();
            LocalTime chosenStartTime = LocalTime.parse(start_timeV.getText());
           LocalTime chosenEndTime = LocalTime.parse(end_timeV.getText());

            LocalDateTime localDateTimeStartDate = LocalDateTime.of(chosenStartDate, chosenStartTime);
            LocalDateTime localDateTimeEndDate = LocalDateTime.of(chosenEndDate, chosenEndTime);

            //Starts comparision to check for overlapping appointment.
            int startCompare = localDateTimeStartDate.compareTo(appointmentListStartDate);
            int startEndCompare = localDateTimeStartDate.compareTo(appointmentListEndDate);
            int endCompare = localDateTimeEndDate.compareTo(appointmentListEndDate);
            int endStartCompare = localDateTimeEndDate.compareTo(appointmentListStartDate);

            if (startCompare >= 0 && startEndCompare <= 0 || endCompare <= 0 && endStartCompare >= 0) {
                return true;
            }
        }

    return false;

    }

    /**This method saves new appointment after handling any errors.*/
    @FXML
    private void saveAppt(ActionEvent event) throws IOException, SQLException {
        String Customer_ID = customerID.getText();
        String chosenStartDate = String.valueOf(startDate_dp.getValue());
        String chosenStartTime = start_timeV.getText();
        String chosenEndDate = String.valueOf(endDate_dp.getValue());
        String chosenEndTime = end_timeV.getText();

        LocalDate startDateValue = LocalDate.parse(chosenStartDate);
        LocalDate endDateValue = LocalDate.parse(chosenEndDate);
        DateTimeFormatter timeDTF = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime startTimeValue = LocalTime.parse(chosenStartTime, timeDTF);
        LocalTime endTimeValue = LocalTime.parse(chosenEndTime, timeDTF);

        DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime localStartDateTime = ZonedDateTime.of(startDateValue, startTimeValue, localZoneId);
        ZonedDateTime localEndDateTime = ZonedDateTime.of(endDateValue, endTimeValue, localZoneId);

        String UTCStartDTString = formatterUTC.format(localStartDateTime);
        String UTCEndDTString = formatterUTC.format(localEndDateTime);

        int startValue = startTimeValue.compareTo(LocalTime.parse("08:00:00"));
        int endValue = endTimeValue.compareTo(LocalTime.parse("22:00:00"));
        int endTimeAfterStart = endTimeValue.compareTo(startTimeValue);
        int endDateAfterStart = endDateValue.compareTo(startDateValue);


        if (startValue < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Appointment start time falls outside of our business Hours. Please choose a start time between 8 AM and 10 PM Local Time.");
            alert.showAndWait();
            return;
        } else if (endValue > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Appointment end time falls outside of our business Hours. Please choose a start time between 8 AM and 10 PM Local Time.");
            alert.showAndWait();
            return;
        } else if (endTimeAfterStart <= 0 || endDateAfterStart < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Appointment end Date and Time can not be before appointment start Date and Time.");
            alert.showAndWait();
            return;

        }  else if (isOverlap() == true) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Your Appointment overlaps with another appointment, please select a different date and/or time.");
            alert.showAndWait();
            return;
        } else {
            Appointment_DAO appointment_dao = new Appointment_DAO();



            appointment_dao.insertAppointment( apptTitle_txt.getText(), apptDesc_txt.getText(),
                    apptLocation_txt.getText(), apptType_txt.getText(),UTCStartDTString, UTCEndDTString, customerID.getText(), String.valueOf(contactID_combo.getSelectionModel().getSelectedItem()));

            root = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
            stage = (Stage)save_btn.getScene().getWindow();
            Scene  scene= new Scene(root);
            stage.setScene(scene);
            stage.show();

        }


    }

    /**This method initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment_DAO appointment_dao = new Appointment_DAO();
        User_DAO user_dao = new User_DAO();
        Contact_DAO contact_dao = new Contact_DAO();

        ObservableList<String> userData = user_dao.getUserList();
        ObservableList<String> contactData = contact_dao.getAllContact();

        contactID_combo.setItems(contactData);
        userID_combo.setItems(userData);



    }





}

