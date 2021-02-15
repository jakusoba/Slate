package model;

/**This is a plain old java object.*/
public class First_Levels_Divisions {
    private int Division_ID;
    private String Division;
    private int Country_ID;

    public First_Levels_Divisions(int Division_ID, String Division, int Country_ID) {
        this.Division_ID = Division_ID;
        this.Division = Division;
        this.Country_ID = Country_ID;
    }

    public First_Levels_Divisions(String Division) {

        this.Division = Division;

    }



    @Override
    public java.lang.String toString() {
        return Division;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }
}
