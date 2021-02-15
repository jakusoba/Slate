package utils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import utils.DBConnection;

public class DBQuery {

    /*This method creates statement reference.**/
    private static PreparedStatement statement;


    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);

    }

    /*This method creates a getter to return statement object.**/
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }








}








