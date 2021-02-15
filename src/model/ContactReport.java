package model;
/**This is a plain old java object.*/
public class ContactReport {
    private int Appointment_ID;
    private String Title;
    private String Type;
    private String Description;
    private String Start;
    private String End;
    private int Customer_ID;
    private int Contact_ID;

    public ContactReport(int appointment_ID, String title, String type, String description, String start, String end, int customer_ID, int contact_ID) {
        Appointment_ID = appointment_ID;
        Title = title;
        Type = type;
        Description = description;
        Start = start;
        End = end;
        Customer_ID = customer_ID;
        Contact_ID = contact_ID;
    }

    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }
}
