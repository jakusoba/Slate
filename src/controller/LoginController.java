package controller;

        import DAO.User_DAO;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.collections.transformation.FilteredList;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.input.MouseEvent;
        import javafx.stage.Stage;
        import model.Appointment;
        import model.User;
        import utils.DBConnection;
        import utils.DBQuery;

        import java.io.BufferedWriter;
        import java.util.ResourceBundle;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.net.URL;
        import java.sql.*;
        import java.time.Clock;
        import java.time.LocalDateTime;
        import java.time.ZoneId;
        import java.time.ZonedDateTime;
        import java.time.format.DateTimeFormatter;
        import java.time.format.FormatStyle;
        import java.util.*;
        import java.util.Date;


/** Login FXML controller
 * @author obianuju akusoba */

/*This is the Login FXML Controller class.**/

public class LoginController implements Initializable {

    /*Sets the Stage and Scene.**/
    Parent root;
    Stage stage;


    /* FXML ID information**/
    @FXML
    private TextField UsernameField;

    @FXML
    private Label LoginUsernameLabel;

    @FXML
    private PasswordField PasswordTextField;

    @FXML
    private Label LoginPasswordLabel;

    @FXML
    private Button LoginButton;

    @FXML
    private Label alertTitleLabel;

    @FXML
    private Label alertErrorValue;


    @FXML
    private Label LoginLabel;

    public static String loggedInUser = "";
    private final DateTimeFormatter formatterTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private final ZoneId userLocalZone = ZoneId.systemDefault();
    private ObservableList<Appointment>appointmentsReminderList = FXCollections.observableArrayList();


    /**This method fetches appointments within 15 minutes of user's login and adds it to a list.*/
    private void reminderAppointmentNotice() {
        LocalDateTime timeAtLogin = LocalDateTime.now();
        User user = new User();
        Connection conn = DBConnection.startConnection();
        try {
            String selectStatement = "SELECT appointments.Appointment_ID, appointments.Description, appointments.Start, appointments.End FROM appointments, customers WHERE appointments.Customer_ID = customers.Customer_ID AND Start >= curdate()AND Start <= date_add(curdate(), interval 1 day)";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                int Appointment_ID = rs.getInt("Appointment_ID");
                Timestamp startTM = rs.getTimestamp("Start");
                Timestamp endTM = rs.getTimestamp("End");
                LocalDateTime localStartTime = startTM.toLocalDateTime();
                LocalDateTime localEndTime = endTM.toLocalDateTime();
                String Description = rs.getString("Description");

                /*Begin checking for appointments within the next 15 minutes window and add it to the Reminder List.**/
                if (localStartTime.isBefore(timeAtLogin.plusMinutes(15)) && localStartTime.isAfter(timeAtLogin) || localStartTime.isEqual(timeAtLogin.plusMinutes(15))) {
                 Appointment appointment = new Appointment(Appointment_ID, Description, localStartTime.toString(), localEndTime.toString());
                 appointmentsReminderList.add(appointment);

                 }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    /**This method alerts the user of an upcoming appointment if there is one or it displays a custom message if there is no upcoming appointment within 15 minutes of logging in.*/
    private void appointmentAlert () {
        if (appointmentsReminderList.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" Appointment Reminder");
            alert.setHeaderText("No upcoming appointments.");
            alert.setContentText("You have no appointment scheduled within the next 15 minutes.");
            alert.showAndWait();
            System.out.println("No upcoming appointment alerts.");
        } else {
            int Appointment_ID = appointmentsReminderList.get(0).getAppointment_ID();
            String start = appointmentsReminderList.get(0).getStart().substring(0, 16);
            LocalDateTime localDateTime = LocalDateTime.parse(start);
            //ZonedDateTime startZonedTime = localDateTime.atZone(ZoneId.of("UTC"));
            ZonedDateTime startZonedTime = localDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime localStart = startZonedTime.withZoneSameInstant(userLocalZone);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointment Reminder");
            alert.setHeaderText("Reminder - You have an appointment scheduled within the next 15 minutes.");
            alert.setContentText("Your upcoming appointment ID is " + Appointment_ID
                    + ", and it is scheduled to begin at " + localStart.format(formatterTime));
            alert.showAndWait();
        }
        System.out.println("**** End appointmentAlert ****");
    }
        /**This method logs user into the application after handling exception control.*/
        @FXML
        private void LoginBtnHandler (MouseEvent event) throws SQLException, IOException {
            ResourceBundle resource = ResourceBundle.getBundle("resources/Nat", Locale.getDefault());
            if (UsernameField.getText().length() == 0 && PasswordTextField.getText().length() == 0) {
                if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(resource.getString("incorrect"));
                    alert.setTitle(resource.getString("alertTitleLabel"));
                    alert.setContentText(resource.getString("errorValue"));
                    alert.showAndWait();
                    return;
                }
            }

            Connection conn = DBConnection.startConnection();
            try {
                String selectStatement = "SELECT * FROM users";
                DBQuery.setPreparedStatement(conn, selectStatement);
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.getResultSet();

                boolean isLoggedIn = false;
                while (rs.next() && !(isLoggedIn)) {
                    User user = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"),
                            rs.getString("Create_Date"), rs.getString("Created_By"),
                            rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"));

                    String providedUserName = UsernameField.getText();
                    String providedPassword = PasswordTextField.getText();

                    if (providedUserName.toLowerCase().equals(rs.getString("User_Name")) && (providedPassword.toLowerCase().equals(rs.getString("Password")))) {
                        isLoggedIn = true;
                        System.out.println("Login successful");

                        reminderAppointmentNotice();
                        appointmentAlert();

                        root = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
                        stage = (Stage) LoginButton.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                        Date date = new Date();
                        long time = date.getTime();
                        Timestamp ts = new Timestamp(time);

                        loggedInUser = providedUserName;
                        try {
                            String fileName = "login_activity.txt";
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
                            bufferedWriter.append("Attempted login by " + loggedInUser + " " + ts + " " + "was successful" + "\n");
                            System.out.println("Attempted login successful.");
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e) {
                            System.out.println(e);
                        }

                    } else {
                        System.out.println("Login error");
                        ResourceBundle rs1 = ResourceBundle.getBundle("resources/Nat", Locale.getDefault());
                        if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(rs1.getString("incorrect"));
                            alert.setTitle(resource.getString("alertTitleLabel"));
                            alert.setContentText(rs1.getString("login_error"));
                            Optional<ButtonType> result = alert.showAndWait();

                            Date date = new Date();
                            long time = date.getTime();
                            Timestamp ts = new Timestamp(time);
                            loggedInUser = providedUserName;

                            try {
                                String fileName = "login_activity.txt";
                                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
                                bufferedWriter.append("Attempted login by " + loggedInUser + " at " + ts + " " + "failed. " + "\n");
                                System.out.println("Attempted login failed.");
                                bufferedWriter.flush();
                                bufferedWriter.close();
                            } catch (IOException e) {
                                System.out.println(e);
                            }

                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }

        }

        @FXML
        void PasswordTextFieldHandler (MouseEvent event){

        }

        @FXML
        private void UserNameFieldHandler (MouseEvent event){

        }
        /**This method sets the application login screen based on system language settings(en or fr).*/
        public void userLanguageOption () {
            //The resource bundle class contain locale specific objects. When the program needs a local specific resource, e.g a string,
            // the program loads it from the resource bundle specific to the current user's locale.
            ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/Nat", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {
                LoginLabel.setText(resourceBundle.getString("title_label"));
                UsernameField.setPromptText(resourceBundle.getString("username_label"));
                PasswordTextField.setPromptText(resourceBundle.getString("password_label"));
                LoginButton.setText(resourceBundle.getString("login_btn"));
            } else {
                System.out.println("Cannot find resource.");
            }

        }

        /**This method initializes the controller class.
         */
        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){

            userLanguageOption();

        }


    }



