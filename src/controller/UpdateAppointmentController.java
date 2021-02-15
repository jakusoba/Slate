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
/** UpdateAppointment FXML Controller class
 @author Obianuju Akusoba
 */

/**Sets Stage and scene.*/
public class UpdateAppointmentController implements Initializable {

    /**Sets Stage and scene.*/
    Parent root;
    Stage stage;

    @FXML
    private ComboBox<String> contactID_combo;

    @FXML
    private ComboBox<String> userID_combo;

    @FXML
    private TextField appointment_ID;

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
    private DatePicker startDate_dp;

    @FXML
    private ComboBox<String> start_combo;

    @FXML
    private DatePicker endDate_dp;

    @FXML
    private ComboBox<String> end_combo;

    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;


    /**This method takes user back to the appointment screen.*/
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        stage = (Stage) cancel_btn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**This method takes checks for overlapping appointment.*/
    public boolean isOverlap() {
        Appointment_DAO appointment_dao = new Appointment_DAO();
        ObservableList<Appointment> appointmentList = appointment_dao.getAllAppointments();
        for (Appointment appointment : appointmentList) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime appointmentListStartDate = LocalDateTime.parse(appointment.getStart(), dateTimeFormatter);
            LocalDateTime appointmentListEndDate = LocalDateTime.parse(appointment.getEnd(), dateTimeFormatter);

            LocalDate chosenStartDate = startDate_dp.getValue();
            LocalDate chosenEndDate = endDate_dp.getValue();
            LocalTime chosenStartTime = LocalTime.parse(start_combo.getSelectionModel().getSelectedItem());
            LocalTime chosenEndTime = LocalTime.parse(end_combo.getSelectionModel().getSelectedItem());

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
    /**This method loads all of the original appointment information on the update form.*/
    public void getAppointmentsToForm(Appointment appointment){
        String aptStartDT = appointment.getStart();
        String aptEndDT = appointment.getEnd();

        LocalDate startDateOfApt = LocalDate.parse(aptStartDT.substring(0,10));
        String aptStart = aptStartDT.substring(11,19);
        LocalDate endDateOfApt = LocalDate.parse(aptEndDT.substring(0,10));
        String aptEndT = aptEndDT.substring(11, 19);
        appointment_ID.setText(Integer.toString(appointment.getAppointment_ID()));
        apptTitle_txt.setText(appointment.getTitle());
        apptDesc_txt.setText(appointment.getDescription());
        apptLocation_txt.setText(appointment.getLocation());
        apptType_txt.setText(appointment.getType());
        startDate_dp.setValue(startDateOfApt);
        endDate_dp.setValue(endDateOfApt);
        start_combo.setValue(aptStart);
        end_combo.setValue(aptEndT);
        customerID.setText(String.valueOf(appointment.getCustomer_ID()));
        userID_combo.setValue(appointment.getUser_ID());
        contactID_combo.setValue(appointment.getContact_ID());


    }

    /**This method saves updated appointments.*/
    @FXML
    private void saveAppt(ActionEvent event) throws IOException, SQLException {
        String CustomerID = customerID.getText();
        int Appointment_ID = Integer.parseInt(appointment_ID.getText());
        String Title = apptTitle_txt.getText();
        String Description = apptDesc_txt.getText();
        String Location = apptLocation_txt.getText();
        String Type = apptType_txt.getText();
        String chosenUserID = String.valueOf(userID_combo.getSelectionModel().getSelectedItem());
        String chosenStartDate = String.valueOf(startDate_dp.getValue());
        String chosenStartTime = start_combo.getSelectionModel().getSelectedItem();
        String chosenEndDate = String.valueOf(endDate_dp.getValue());
        String chosenEndTime = end_combo.getSelectionModel().getSelectedItem();

        LocalDate startDateValue = LocalDate.parse(chosenStartDate);
        LocalTime startTimeValue = LocalTime.parse(chosenStartTime);
        LocalDate endDateValue = LocalDate.parse(chosenEndDate);
        LocalTime endTimeValue = LocalTime.parse(chosenEndTime);

        DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime localStartDateTime = ZonedDateTime.of(startDateValue, startTimeValue, localZoneId);
        ZonedDateTime localEndDateTime = ZonedDateTime.of(endDateValue, endTimeValue, localZoneId);

        String UTCStartDTString = formatterUTC.format(localStartDateTime);
        String UTCEndDTString = formatterUTC.format(localEndDateTime);


        int startValue = LocalTime.parse(chosenStartTime).compareTo(LocalTime.parse("08:00:00"));
        int endValue = LocalTime.parse(chosenEndTime).compareTo(LocalTime.parse("22:00:00"));
        int endTimeAfterStart = chosenEndTime.compareTo(chosenStartTime);
        int endDateAfterStart = chosenEndDate.compareTo(chosenStartDate);


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
        } else if (endTimeAfterStart <= 0 || endDateAfterStart < 0){
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
        }else {
            Appointment_DAO appointment_dao = new Appointment_DAO();

            appointment_dao.updateAppointment(Appointment_ID, Title, Description, Location, Type, UTCStartDTString,
            UTCEndDTString, CustomerID, chosenUserID);


            root = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
            stage = (Stage) save_btn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }


    }

    /**This method initializes the controller.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment_DAO appointment_dao = new Appointment_DAO();
        User_DAO user_dao = new User_DAO();
        Contact_DAO contact_dao = new Contact_DAO();

        ObservableList<String> observableList = appointment_dao.getAllAppointmentEndTimes();
        ObservableList<String> observableListStartTimes = appointment_dao.getAllAppointmentStartTimes();
        ObservableList<String> userData = user_dao.getUserList();
        ObservableList<String> contactData = contact_dao.getAllContact();

        contactID_combo.setItems(contactData);
        userID_combo.setItems(userData);
        start_combo.setItems(observableListStartTimes);
        end_combo.setItems(observableList);

    }
}