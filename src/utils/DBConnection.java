package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import com.mysql.jdbc.Connnection;

public class DBConnection {
    /*This is the JDBC URL parts.**/
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ServerName = "//wgudb.ucertify.com/WJ07sNI";

    /*This is the jdbc URL.**/
    private static final String jdbcURL=  protocol + vendorName + ServerName;

    /*This is the MYSQLJDBCDriver Interface reference.**/
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null; //import sql connection

    private static final String username = "U07sNI"; //Username
    private static final String password = "53689121050";//Password

    /*This method starts the connection to the Database.**/
    public static Connection startConnection() {
        try{
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");

        }
        catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch(SQLException e) {
            System.out.println("Error: " +e.getMessage());
        }

        return conn;


    }
    /*This method closes the connection to the Database.**/
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }


}

