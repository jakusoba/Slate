package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.CustomerReport;
import model.Country;
import model.First_Levels_Divisions;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**This class was created for CRUD operations on the customer table.*/
public class Customer_DAO {

    /*This method returns customer details with a given customer ID as input parameter.**/
    /**public Customer getCustomerName(String Customer_Name) { //Read from the Customer table.
        Connection conn = DBConnection.startConnection();
        Customer customer = null;
        try {
            String selectStatement = "SELECT * FROM customers WHERE Customer_Name = " + Customer_Name;
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                customer = new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"),
                        rs.getString("Postal_Code"), rs.getString("Phone"), rs.getInt("Division_ID"));
                customer.setCustomer_Name(rs.getString("Customer_Name"));
                customer.setAddress(rs.getString("Address"));
                customer.setCustomer_ID(rs.getInt("Customer_ID"));
                customer.setPostal_Code(rs.getString("Postal_Code"));
                customer.setPhone(rs.getString("Phone"));
                customer.setDivision_ID(rs.getInt("Division_ID"));


            }
            DBConnection.closeConnection();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


        return customer;


    }**/

    /**This method returns all customer records from the customer table.*/
    public ObservableList<Customer> getAllCustomers() {//Read all Customers from Customer table.
        ObservableList<Customer> observableList = FXCollections.observableArrayList();
        Connection conn = DBConnection.startConnection();
        try {
            String selectStatement = "SELECT * FROM customers";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                //Customer customer = new Customer(rs.getString("Customer_ID"),rs.getString("Customer_Name"), rs.getInt("Address"),
                //rs.getString("Postal_Code"), rs.getString("Phone"), rs.getInt("Division_ID"));

                String Customer_ID = rs.getString("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                String Division_ID=  rs.getString("Division_ID");

                //return (ObservableList<Customer>) customer;
                Customer customerList = new Customer(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID);
                observableList.add(customerList);
                //System.out.println(Customer_ID);
            }

            DBConnection.closeConnection();
        } catch (Exception exception) {
            System.out.println("All customer list error: " + exception.getMessage());
        }
        return observableList;

    }


    /**This method creates a new customer record in the customer table.*/
    public static void insertCustomer(String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID) {//Create a Customer in Customer table.
        Connection conn = DBConnection.startConnection();
        try {
            String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";


            DBQuery.setPreparedStatement(conn, insertStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            //Key value mapping
            ps.setString(1, Customer_Name);//This method expects the value index and the value itself.
            ps.setString(2, Address);
            ps.setString(3, Postal_Code);
            ps.setString(4, Phone);
            ps.setInt(5, Division_ID);

            ps.execute();

            DBConnection.closeConnection();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

        }

    }

    /**This method modifies an existing customer record in the customer table.*/
    public void updateCustomer(String Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, String Division_ID) {//Update Customer in Customer table.
        Connection conn = DBConnection.startConnection();
        try {
            String updateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(conn, updateStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            Customer customer = new Customer(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID);

            //Key value mapping
            ps.setString(1, customer.getCustomer_Name());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostal_Code());
            ps.setString(4, customer.getPhone());
            ps.setString(5, Division_ID);
            ps.setString(6, customer.getCustomer_ID());

            ps.executeUpdate();

            DBConnection.closeConnection();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

        }

    }

        /**This method removes a customer record from the customer table.*/
        public void deleteCustomer ( String Customer_ID){//Delete Customer from customer table.
            Connection conn = DBConnection.startConnection();
            try {
                String deleteStatementOne = "DELETE FROM customers WHERE Customer_ID = ?";
                String deleteStatementTwo = "DELETE FROM appointments WHERE Customer_ID = ?";

                DBQuery.setPreparedStatement(conn, deleteStatementOne);//Create PreparedStatement object.
                PreparedStatement ps1 = DBQuery.getPreparedStatement();

                //Key value mapping
                ps1.setString(1, Customer_ID);

                ps1.execute();

                ResultSet rs1 = ps1.getResultSet();

                //Key value mapping
                ps1.setString(1, Customer_ID);


                DBQuery.setPreparedStatement(conn, deleteStatementTwo);
                PreparedStatement ps2 = DBQuery.getPreparedStatement();

                //Key value mapping
                ps2.setString(1, Customer_ID);


                ps2.execute();

                ResultSet rs2 = ps2.getResultSet();

                DBConnection.closeConnection();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();

            }

        }
        /**This method reads all customer records from the customer table.*/
        public static ObservableList<CustomerReport> getCustomerReport () {
            ObservableList<CustomerReport> customerReportObservableList = FXCollections.observableArrayList();

            final ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
            final DateTimeFormatter datetimeStyle = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(localZoneID);


            Connection conn = DBConnection.startConnection();

            try {
                String selectStatement = "SELECT cT.Customer_Name, aT.Start, aT.End" +
                        "                       FROM appointments aT, customers cT" +
                        "                       WHERE aT.Customer_ID = cT.Customer_ID" +
                        "                       ORDER BY Start";


                DBQuery.setPreparedStatement(conn, selectStatement);
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.getResultSet();

                while (rs.next()) {

                    String Customer = rs.getString("cT.Customer_Name");
                    //String Start = rs.getString("aT.Start");
                    //String End = rs.getString("aT.end");

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


                    CustomerReport customerReport = new CustomerReport(Customer, localStartDT, localEndDT);
                    customerReportObservableList.add(customerReport);
                }

                DBConnection.closeConnection();

            } catch (Exception exception) {
                System.out.println("Customer Report error: " + exception.getMessage());
            }
            return customerReportObservableList;

        }


    }


