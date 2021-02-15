package model;

import java.sql.Timestamp;

/**This is a plain old java object.*/
public class User {
    private int User_ID;
    private String User_Name;
    private String Password;
    private String Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;

    public User(){
         int User_ID = 0;
         String User_Name = null;
         String Password = null;

    }

    public User(int User_ID, String User_Name, String Password, String Create_Date, String Created_By, Timestamp Last_Update, String Last_UpdateBy){

        this.User_ID = User_ID;
        this.User_Name = User_Name;
        this.Password = Password;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_UpdateBy;

    }
    public User(String Password) {
        this.Password = Password;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String create_Date) {
        Create_Date = create_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public Timestamp getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(Timestamp last_Update) {
        Last_Update = last_Update;
    }

    public String getLast_UpdateBy() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_UpdateBy) {
        Last_Updated_By = last_UpdateBy;
    }
}
