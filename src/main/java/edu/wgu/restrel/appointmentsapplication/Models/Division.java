package edu.wgu.restrel.appointmentsapplication.Models;

public class Division {

    private int Division_ID;
    private String Division;
    private int Country_ID;

    /**
     * Constructor
     * 
     * @param division_ID
     * @param division
     * @param country_ID
     */
    public Division(int division_ID, String division, int country_ID) {
        Division_ID = division_ID;
        Division = division;
        Country_ID = country_ID;
    }

    /**
     * Getter for Division_ID
     * 
     * @return
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**
     * Setter for Division_ID
     * 
     * @param division_ID
     */
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    /**
     * Getter for Division
     * 
     * @return
     */
    public String getDivision() {
        return Division;
    }

    /**
     * Setter for Division
     * 
     * @param division
     */
    public void setDivision(String division) {
        Division = division;
    }

    /**
     * Getter for Country_ID
     * 
     * @return
     */
    public int getCountry_ID() {
        return Country_ID;
    }

    /**
     * Setter for Country_ID
     * 
     * @param country_ID
     */
    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

}