package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.First_Levels_Divisions;

/**This class was created for CRUD operations on the First_Level_Divisions table.*/

public class First_Level_Divisions_DAO {
    /**This method returns all Divisions from the First_Level_Divisions table.*/
    public static ObservableList<First_Levels_Divisions> getAllDivisions(){
        ObservableList<First_Levels_Divisions> divisionData = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT Division FROM first_level_divisions";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                String divisions = rs.getString("Division");



                First_Levels_Divisions division = new First_Levels_Divisions(divisions);
                divisionData.add(division);

            }

            DBConnection.closeConnection();

        }
            catch (Exception ex) {
            System.out.println("There was an error: " + ex.getMessage());
        }
            return divisionData;
    }

    /**This method returns all Division records from the First_Level_Divisions table.*/
    public static ObservableList<First_Levels_Divisions> getAllDivisionsData(){
        ObservableList<First_Levels_Divisions> allDivisionData = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT * FROM first_level_divisions";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                String divisions = rs.getString("Division");
                int division_ID = rs.getInt("Division_ID");
                int country_ID = rs.getInt("Country_ID");


                First_Levels_Divisions division = new First_Levels_Divisions(division_ID, divisions, country_ID);
                allDivisionData.add(division);

            }

            DBConnection.closeConnection();
            System.out.println(allDivisionData);

        }
        catch (Exception ex) {
            System.out.println("There was an error: " + ex.getMessage());
        }
        return allDivisionData;
    }

    /**This method returns all Division_ID from the First_Level_Divisions table.*/
    public static ObservableList<Integer> getAllDivisonID(){
        ObservableList<Integer> divisionIDList = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT Division_ID  FROM first_level_divisions";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int divisionsId = rs.getInt("Division_ID");
                int DIdList = divisionsId;
                divisionIDList.add(DIdList);
            }

            DBConnection.closeConnection();

            System.out.println(divisionIDList);

        }
        catch (Exception ex) {
            System.out.println("There was an error: " + ex.getMessage());
        }
        return divisionIDList;
    }

}
