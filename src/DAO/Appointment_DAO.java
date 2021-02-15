package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.AppointmentReport;
import model.ContactReport;
import utils.DBConnection;
import utils.DBQuery;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.TimeZone;

/**This class was created for CRUD operations on the Appointment table.*/
public class Appointment_DAO {


    /**This method returns appointment details with a given Appointment ID as input parameter.*/
    public Appointment getAppointments(int Appointment_ID ) { //Read from the appointments table.
        Connection conn = DBConnection.startConnection();
        Appointment appointment = null;
        try {
            String selectStatement = "SELECT * FROM appointments WHERE Appointment_ID = ?";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                appointment = new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"),
                        rs.getString("Location"), rs.getString("Type"), rs.getString("Start"),
                        rs.getString("End"), rs.getString("Customer_ID"), rs.getString("Contact_ID"), rs.getString("User_ID"));

                appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setStart(rs.getString("Start"));
                appointment.setEnd(rs.getString("END"));
                appointment.setCustomer_ID(rs.getString("Customer_ID"));
                appointment.setContact_ID(rs.getString("Contact_ID"));
                appointment.setUser_ID(rs.getString("User_ID"));


                return appointment;
            }
            DBConnection.closeConnection();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return appointment;

    }
    /**This method returns all appointments from the appointments table.*/
    public static ObservableList<Appointment> getAllAppointments(){//Read all appointments from appointments table.
        ObservableList<Appointment> observableList = FXCollections.observableArrayList();

        final ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        final DateTimeFormatter datetimeStyle = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(localZoneID);


        try{
            Connection conn = DBConnection.startConnection(); //Start connection to the DB.
            String selectStatement = "SELECT * FROM appointments"; //Create DB query.
            DBQuery.setPreparedStatement(conn, selectStatement); //Pass connection name and DB query to setPreparedStatement.
            PreparedStatement ps = DBQuery.getPreparedStatement(); //Create ps Object and get PreparedStatement after its parameters has been passed in the setPreparedStatement.
            ps.execute(); //Execute Prepared statement.
            ResultSet rs = ps.getResultSet();//Create rs object and ps gets the rs.
            while (rs.next()) {

                LocalDate utcToLocalStartDT = LocalDate.parse(rs.getString("Start").substring(0,10));
                LocalTime utcToLocalStartTime = LocalTime.parse(rs.getString("Start").substring(11, 19));
                LocalDate utcToLocalEndDT = LocalDate.parse(rs.getString("End").substring(0, 10));
                LocalTime utcToLocalEndTime= LocalTime.parse(rs.getString("End").substring(11, 19));

                //convert times in UTC zoneId to local zoneId of user.
                ZonedDateTime UTCZoneStart = ZonedDateTime.of(utcToLocalStartDT, utcToLocalStartTime, ZoneId.of("UTC"));
                ZonedDateTime UTCZoneEnd = ZonedDateTime.of(utcToLocalEndDT, utcToLocalEndTime, ZoneId.of("UTC"));

                //convert ZonedDateTime to a string for insertion into AppointmentsTableView
                String localStartDT = datetimeStyle.format(UTCZoneStart);
                String localEndDT = datetimeStyle.format(UTCZoneEnd);

                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");
                String Start = localStartDT;
                String End = localEndDT;
                String Customer_ID = String.valueOf(rs.getInt("Customer_ID"));
                String Contact_ID = String.valueOf(rs.getInt("Contact_ID"));
                String User_ID = String.valueOf(rs.getInt("User_ID"));
                Appointment appointmentList= new Appointment(Appointment_ID, Title, Description, Location, Type,
                        localStartDT, localEndDT,Customer_ID, User_ID, Contact_ID);

                observableList.add(appointmentList);
            }
            DBConnection.closeConnection();

        }
        catch (Exception ex){System.out.println("Error: " + ex.getMessage());
        }
        return observableList;


    }

    /**This method creates a new appointment in the appointment table.*/
    public void insertAppointment( String Title, String Description, String Location, String Type, String Start, String End, String Customer_ID, String Contact_ID) throws SQLException {
        Connection conn = DBConnection.startConnection();
        try {
            String insertStatement = "INSERT INTO appointments(Title , Description , Location, Type, Start, End, Customer_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(conn, insertStatement); //Create PreparedStatement object.
            PreparedStatement ps = DBQuery.getPreparedStatement();


            //Key value mapping
            ps.setString(1, Title);
            ps.setString(2, Description);
            ps.setString(3, Location);
            ps.setString(4, Type);
            ps.setString(5, Start);
            ps.setString(6, End);
            ps.setString(7, Customer_ID);
            //ps.setInt(8, User_ID);
            ps.setString(8, Contact_ID);

            ps.execute();

            ResultSet rs = ps.getResultSet(); // Created a ResultSet reference rs and passed a statement reference to it.
            DBConnection.closeConnection();
        }
        catch (Exception exception){
            exception.printStackTrace();

        }

    }
    /**This method modifies an existing appointment in the appointment table.*/
    public void updateAppointment( int Appointment_ID, String Title,
                                  String Description, String Location, String Type, String Start, String End, String Customer_ID, String User_ID) {//Update appointment in appointments table.
        Connection conn = DBConnection.startConnection();
        try{
            String updateStatement = ("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?");
            DBQuery.setPreparedStatement(conn, updateStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            Appointment appointment = new Appointment(Title, Description, Location, Type, Start, End, Customer_ID, User_ID);


            ps.setString(1, Title);
            ps.setString(2, Description);
            ps.setString(3, Location);
            ps.setString(4, Type);
            ps.setString(5, Start);
            ps.setString(6, End);
            ps.setString(7, Customer_ID);
            ps.setString(8, User_ID);
            ps.setInt(9, Appointment_ID);


            ps.executeUpdate();

            DBConnection.closeConnection();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();

        }
    }
    /**This method removes an appointment from the appointment table.*/
    public void deleteAppointment(int Appointment_ID) {//Delete appointment from appointments table.
        Connection conn = DBConnection.startConnection();
        try {
            String deleteStatement = "DELETE FROM appointments WHERE appointment_ID = ?";
            DBQuery.setPreparedStatement(conn, deleteStatement); //Create PreparedStatement object.
            PreparedStatement ps = DBQuery.getPreparedStatement();

            //Key value mapping
            ps.setInt(1, Appointment_ID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            DBConnection.closeConnection();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();

        }
    }
    /**This method returns all appointment end time.*/
    public static ObservableList<String> getAllAppointmentEndTimes() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        final DateTimeFormatter datetimeStyle = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final ZoneId localZoneID = ZoneId.systemDefault();
        final ZoneId utcZoneID = ZoneId.of("UTC");
        try{
            Connection conn = DBConnection.startConnection(); //Start connection to the DB.
            String selectStatement = "SELECT End FROM appointments"; //Create DB query.
            DBQuery.setPreparedStatement(conn, selectStatement); //Pass connection name and DB query to setPreparedStatement.
            PreparedStatement ps = DBQuery.getPreparedStatement(); //Create ps Object and get PreparedStatement after its parameters has been passed in the setPreparedStatement.
            ps.execute(); //Execute Prepared statement.
            ResultSet rs = ps.getResultSet();//Create rs object and ps gets the rs.
            while (rs.next()) {
                 LocalDateTime utcToLocalEndDT = LocalDateTime.parse(rs.getString("End"), datetimeStyle);

                ZonedDateTime localZoneEnd = utcToLocalEndDT.atZone(localZoneID).withZoneSameInstant(localZoneID);

                String localEndDT = localZoneEnd.format(datetimeStyle);
                String End = localEndDT;

                observableList.add(End);
            }
            DBConnection.closeConnection();

        }
        catch (Exception ex){System.out.println("Error: " + ex.getMessage());
        }
        return observableList;

    }
    /**This method returns all appointment start time.*/
    public static ObservableList<String> getAllAppointmentStartTimes() {
        ObservableList<String> observableListStartTimes = FXCollections.observableArrayList();
        final DateTimeFormatter datetimeStyle = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final ZoneId localZoneID = ZoneId.systemDefault();
        final ZoneId utcZoneID = ZoneId.of("UTC");
        try {
            Connection conn = DBConnection.startConnection(); //Start connection to the DB.
            String selectStatement = "SELECT Start FROM appointments"; //Create DB query.
            DBQuery.setPreparedStatement(conn, selectStatement); //Pass connection name and DB query to setPreparedStatement.
            PreparedStatement ps = DBQuery.getPreparedStatement(); //Create ps Object and get PreparedStatement after its parameters has been passed in the setPreparedStatement.
            ps.execute(); //Execute Prepared statement.
            ResultSet rs = ps.getResultSet();//Create rs object and ps gets the rs.
            while (rs.next()) {
                LocalDateTime utcToLocalStartDT = LocalDateTime.parse(rs.getString("Start"), datetimeStyle);

                ZonedDateTime localZoneStart = utcToLocalStartDT.atZone(localZoneID).withZoneSameInstant(localZoneID);

                String localStartDT = localZoneStart.format(datetimeStyle);
                String Start = localStartDT;

                observableListStartTimes.add(Start);
            }
            DBConnection.closeConnection();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return observableListStartTimes;

    }

    /**This method sorts and returns all appointment by month and type.*/
    public static ObservableList<AppointmentReport> getFirstReport() {

            ObservableList<AppointmentReport> aptReportdata = FXCollections.observableArrayList();

            try {
                Connection conn = DBConnection.startConnection(); //Start connection to the DB.
                String selectStatement = "SELECT monthname(start) as Month, type, COUNT(Appointment_ID) as Total " +
                        "FROM appointments " +
                        "group by monthname(start), type " +
                        "order by monthname(start), type";
                DBQuery.setPreparedStatement(conn, selectStatement);
                PreparedStatement ps = DBQuery.getPreparedStatement(); //Create ps Object and get PreparedStatement after its parameters has been passed in the setPreparedStatement.
                ps.execute(); //Execute Prepared statement.
                ResultSet rs = ps.getResultSet();

                while(rs.next()) {

                    String month = rs.getString("Month");
                    String type = rs.getString("Type");
                    String total = rs.getString("Total");


                    AppointmentReport ar = new AppointmentReport(month, type, total);
                    aptReportdata.add(ar);
                }

                DBConnection.closeConnection();

            }
            catch(Exception exception) {
                System.out.println("Error: " + exception.getMessage());
            }
            return aptReportdata;

        }

        /**This method returns appointment schedule for each contact.*/
        public ObservableList<ContactReport>getContactAppointments() throws SQLException {
            ObservableList<ContactReport>contactAppointmentList = FXCollections.observableArrayList();

            final ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
            final DateTimeFormatter datetimeStyle = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(localZoneID);



            Connection conn = DBConnection.startConnection();
            try {
                String selectStatement = "SELECT Contact_ID, Title, Type, Description, Start, End, Customer_ID, Appointment_ID FROM appointments ORDER BY Contact_ID";
                DBQuery.setPreparedStatement(conn, selectStatement);
                PreparedStatement ps = DBQuery.getPreparedStatement(); //Create ps Object and get PreparedStatement after its parameters has been passed in the setPreparedStatement.
                ps.execute(); //Execute Prepared statement.
                ResultSet rs = ps.getResultSet();

                while (rs.next()) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String type = rs.getString("Type");
                    String description = rs.getString("Description");

                    //String start = rs.getString("Start");
                    //String end = rs.getString("End");

                    LocalDate utcToLocalStartDT = LocalDate.parse(rs.getString("Start").substring(0,10));
                    LocalTime utcToLocalStartTime = LocalTime.parse(rs.getString("Start").substring(11, 19));
                    LocalDate utcToLocalEndDT = LocalDate.parse(rs.getString("End").substring(0, 10));
                    LocalTime utcToLocalEndTime= LocalTime.parse(rs.getString("End").substring(11, 19));

                    //convert times in UTC zoneId to local zoneId of user.
                    ZonedDateTime UTCZoneStart = ZonedDateTime.of(utcToLocalStartDT, utcToLocalStartTime, ZoneId.of("UTC"));
                    ZonedDateTime UTCZoneEnd = ZonedDateTime.of(utcToLocalEndDT, utcToLocalEndTime, ZoneId.of("UTC"));

                    //convert ZonedDateTime to a string for insertion into AppointmentsTableView
                    String localStartDT = datetimeStyle.format(UTCZoneStart);
                    String localEndDT = datetimeStyle.format(UTCZoneEnd);

                    int customerid = rs.getInt("Customer_ID");
                    int contactid = rs.getInt("Contact_ID");

                    ContactReport contactReport = new ContactReport(apptID, title, type, description, localStartDT, localEndDT, customerid, contactid);

                    contactAppointmentList.add(contactReport);

                }

                DBConnection.closeConnection();
            }
            catch(Exception exception) {
                System.out.println("Error: " + exception.getMessage());

            }

            return contactAppointmentList;

        }




    }




