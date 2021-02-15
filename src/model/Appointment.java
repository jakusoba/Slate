package model;

/**This is a plain old java object.*/
public class Appointment {
    private int Appointment_ID;
    private String Title;
    private String description;
    private String Location;
    private String Type;
    private String Start;
    private String End;
    private String Customer_ID;
    private String Contact_ID;
    private String User_ID;

    public Appointment(int Appointment_ID, String Title, String description, String Location, String Type, String Start, String End, String Customer_ID, String User_ID, String Contact_ID) {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.description = description;
        this.Location = Location;
        this.Type = Type;
        this.User_ID = User_ID;
        this.Customer_ID = Customer_ID;
        this.Start = Start;
        this.End = End;
        this.Contact_ID = Contact_ID;
    }

    //Add new Appointment constructor
    public Appointment( String Title, String description, String Location, String Type, String Start, String End) {
        this.Title = Title;
        this.description = description;
        this.Location = Location;
        this.Type = Type;
        this.Start = Start;
        this.End = End;

    }
    public Appointment( int Appointment_ID, String description,  String Start, String End, String Customer_ID) {
        this.Appointment_ID = Appointment_ID;
        this.description = description;
        this.Start = Start;
        this.End = End;
        this.Customer_ID = Customer_ID;

    }


    //Update Appointment constructor
    public Appointment(String Title, String description, String Location, String Type, String Start, String End, String User_ID, String Customer_ID) {
        //this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.description = description;
        this.Location = Location;
        this.Type = Type;
        this.User_ID = User_ID;
        this.Start = Start;
        this.End = End;
        this.Customer_ID = Customer_ID;
    }

    public Appointment(int Appointment_id, String Description, String Start, String End) {
        this.Appointment_ID = Appointment_id;
        this.description = Description;
        this.Start = Start;
        this.End = End;
    }


    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        this.Appointment_ID = appointment_ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(String contact_ID) {
        Contact_ID = contact_ID;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }
}
