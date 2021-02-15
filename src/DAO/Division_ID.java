package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Division_ID {

    /**This method returns all Divisions from the First_Level_Divisions table.**/
    public static ObservableList<String> getAllDivisionIDs() {
        ObservableList<String> divisionData = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT Division  FROM first_level_Division";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                String division = rs.getString("Division");
                String dList = division;
                divisionData.add(dList);
            }

            DBConnection.closeConnection();

        }
        catch (Exception ex) {
            System.out.println("There was an error: " + ex.getMessage());
        }
        return divisionData;
    }

}
