package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class was created for CRUD operations on the countries table.*/
    /**This method returns contact records.*/
public class Country_DAO {

    /**This method returns all country records from the countries table.*/
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countryData = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT Country FROM countries";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                String country = rs.getString("Country");

                Country country1 = new Country(country);

                countryData.add(country1);
            }

            DBConnection.closeConnection();
        }
        catch (Exception ex) {
            System.out.println("There was an error: " + ex.getMessage());
        }
        return countryData;
    }
    /**This method creates a new country record in the countries table.*/
    public static void insertCountry(String Country_Name) {
        Connection conn = DBConnection.startConnection();
        try {
            String insertStatement = "INSERT INTO countries(Country) VALUES(?)";


            DBQuery.setPreparedStatement(conn, insertStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            //Key value mapping
            ps.setString(1, Country_Name);//This method expects the value index and the value itself.
            ps.execute();
            DBConnection.closeConnection();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();

        }

    }
}
