package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.*;

/**This class was created for CRUD operations on the user table.*/
public class User_DAO {

    /**This method returns a user's details with User_ID and User_name as input parameter.*/
   public User getUser(String User_name, String Password) { //Read from the user table.
       Connection conn = DBConnection.startConnection();
       User user = null;
       try {
           String selectStatement = "SELECT * FROM users WHERE User_Name = " + User_name;
           DBQuery.setPreparedStatement(conn, selectStatement);
           PreparedStatement ps = DBQuery.getPreparedStatement();
           ps.execute();
           ResultSet rs = ps.getResultSet();
           while (rs.next()) {
               user = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"),
                       rs.getString("Create_Date"), rs.getString("Created_By"),
                       rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"));

               user.setUser_ID(rs.getInt("User_ID"));
               user.setUser_Name(rs.getString("User_Name"));
               user.setPassword(rs.getString("Password"));
               user.setCreate_Date(rs.getString("Create_Date"));
               user.setCreated_By(rs.getString("Created_By"));
               user.setLast_Update(rs.getTimestamp("Last_Update"));
               user.setLast_Updated_By(rs.getString("Last_Updated_By"));

               return user;
           }
           DBConnection.closeConnection();

       } catch (SQLException sqlException) {
           sqlException.printStackTrace();
       }
       return user;

   }


   /**This method returns all user's record from the user table.*/
  public void getAllUsers(){//Read all Users from user table.
      ObservableList<User> observableList = FXCollections.observableArrayList();
      Connection conn = DBConnection.startConnection();
      try {
          String selectStatement = "SELECT * FROM users";
          DBQuery.setPreparedStatement(conn, selectStatement);
          PreparedStatement ps = DBQuery.getPreparedStatement();
          ps.execute();
          ResultSet rs = ps.getResultSet();
          while (rs.next()) {
              User user = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"),
                      rs.getString("Create_Date"), rs.getString("Created_By"),
                      rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"));

              int User_ID = rs.getInt("User_ID");
              String User_Name = rs.getString("User_Name");
              String Password = rs.getString("Password");
              String Create_Date = rs.getString("Create_Date");
              String Created_By = rs.getString("Created_By");
              Timestamp Last_Update = rs.getTimestamp("Last_Update");
              String Last_UpdateBy = (rs.getString("Last_Updated_By"));


              User userList = new User(User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_UpdateBy);
              observableList.add(userList);
          }

              DBConnection.closeConnection();
      }

      catch (Exception ex) {
          System.out.println("Error: " + ex.getMessage());
      }




  }


  /*This method creates a new users in the user table.**/
  public void insertUser(User user) {//Create User in user table.
      Connection conn = DBConnection.startConnection();
      try {
          String insertStatement = "INSERT INTO users(User_Name, Password, Create_Date, Created_By, Last_Update, Last_UpdateBy) VALUES(?, ?, ?, ?, ?,)";
          DBQuery.setPreparedStatement(conn, insertStatement);
          PreparedStatement ps = DBQuery.getPreparedStatement();

          //Key value mapping
          ps.setInt(1, user.getUser_ID());
          ps.setString(2, user.getUser_Name());
          ps.setString(3, user.getCreate_Date());
          ps.setString(4, user.getCreated_By());
          ps.setTimestamp(5, user.getLast_Update());
          ps.setString(6, user.getLast_UpdateBy());


          ps.execute();

          ResultSet rs = ps.getResultSet();

          DBConnection.closeConnection();
      }
      catch(SQLException sqlException){
          sqlException.printStackTrace();

      }



  }


  /**This method modifies an existing user's record in the user table.*/
  public void updateUser(User user) {//Update User in user table.
      Connection conn = DBConnection.startConnection();
      try{
          String updateStatement = "UPDATE users SET User_ID = ?, User_Name= ?, WHERE Created_By = ?";
          DBQuery.setPreparedStatement(conn, updateStatement);
          PreparedStatement ps = DBQuery.getPreparedStatement();

          //Key value mapping
          ps.setInt(1, user.getUser_ID());
          ps.setString(2, user.getUser_Name());
          ps.setString(3, user.getCreated_By());

          ps.execute();

          ResultSet rs = ps.getResultSet(); //

          DBConnection.closeConnection();
      }
      catch(SQLException sqlException){
          sqlException.printStackTrace();

      }




  }

  /**This method removes a user's record from the users table.*/
  public void deleteUser(User user) {//Delete User from user table.
          Connection conn = DBConnection.startConnection();
          try {
              String deleteStatement = "DELETE FROM user WHERE User_ID = ?";
              DBQuery.setPreparedStatement(conn, deleteStatement); //Create PreparedStatement object.
              PreparedStatement ps = DBQuery.getPreparedStatement();

              //Key value mapping
              ps.setInt(1, user.getUser_ID());


              ps.execute();

              ResultSet rs = ps.getResultSet();

              DBConnection.closeConnection();
          }
          catch(SQLException sqlException){
              sqlException.printStackTrace();

          }



      }


  /**This method returns a list of all user_ID from the user table.**/
    public static ObservableList<String> getUserList() {
        ObservableList<String> allUsersList = FXCollections.observableArrayList();

        try {


            Connection conn = DBConnection.startConnection();
            String selectStatement = "SELECT User_ID FROM users";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while(rs.next()) {
                String User_ID = rs.getString("User_ID");
                String uList = User_ID;
                allUsersList.add(uList);

            }


            DBConnection.closeConnection();

        }
        catch(Exception ex) {
            System.out.println("There was an error: " + ex.getMessage());
        }
        //return allUsersList;
        return allUsersList;
    }

}


