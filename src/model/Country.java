package model;

/**This is a plain old java object.*/
public class Country {
    private int Country_ID;
    private String Country;

    public Country(int Country_ID, String Country ){
        this.Country_ID = Country_ID;
        this.Country = Country;
    }

    public Country(String Country ){
        this.Country = Country;
    }

    public Country(){}

    public String toString(){
        return Country;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
