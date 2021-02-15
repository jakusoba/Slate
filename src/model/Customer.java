package model;

/**This is a plain old java object.*/
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Customer {
    //Customer attributes is encapsulated to make sure sensitive data is hidden from users, so Customer class attributes is declared as private.
    //public get and set methods to access and update the value of each private variable.

    private String Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private LocalDateTime Create_Date;
    private String Created_BY;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private String Division_ID;

    //Creates instances of Customer object by using a constructor.
    /*public Customer(String Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, String Division_ID) {
        //The values of each parameters are assigned to the values of the Customer Object fields or attribute.
        this.Customer_Name  = Customer_Name;
        this.Address = Address;
        this.Customer_ID = Customer_ID;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Division_ID = Division_ID;**/

    //}

    //Constructor for update customer.
    public Customer(String Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, String Division_ID) {
        //The values of each parameters are assigned to the values of the Customer Object fields or attribute.
        this.Customer_Name  = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Division_ID = Division_ID;
        this.Customer_ID = Customer_ID;

    }
    //Customer getters and setters.

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDivision_ID() {
        return Division_ID;
    }


    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    public String getCreated_BY() {
        return Created_BY;
    }

    public void setCreated_BY(String created_BY) {
        Created_BY = created_BY;
    }

    public Timestamp getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(Timestamp last_Update) {
        Last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }
}
