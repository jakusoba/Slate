package model;


/**This is a plain old Java object.*/
public class CustomerReport {
    private String Customer_Name;
    private String Start;
    private String End;

    public CustomerReport(String customer_Name, String start, String end) {
        this.Customer_Name = customer_Name;
        this.Start = start;
        this.End = end;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
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
