package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;


/*This is the application's main class.**/

public class Main extends Application {
    private final ZoneId userLocalZone = ZoneId.systemDefault();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle("Welcome to Slate, Your local time zone is " + userLocalZone);
        primaryStage.setScene(new Scene(root, 900.0, 402.0));
        primaryStage.show();
    }

        /**This is the application's main method.**/
        public static void main (String[] args) {
            launch(args);
            ResourceBundle resource = ResourceBundle.getBundle("resources.Nat", Locale.getDefault());

            //System.out.println(resource.getString(alertErrorValue));


    }
}
