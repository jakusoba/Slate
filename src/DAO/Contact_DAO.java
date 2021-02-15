package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**This class was created for CRUD operations on the contact table.*/
public class Contact_DAO {

    /**This method returns contact records.*/
    public static ObservableList<String> getAllContact() {
        ObservableList<String> contactData = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT Contact_ID  FROM contacts";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                String Contact_ID = rs.getString("Contact_ID");
                String cList = Contact_ID;
                contactData.add(cList);
            }

            DBConnection.closeConnection();

        }
        catch (Exception ex) {
            System.out.println("There was an error: " + ex.getMessage());
        }
        return contactData;
    }

}






