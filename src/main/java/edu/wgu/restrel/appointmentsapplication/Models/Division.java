package edu.wgu.restrel.appointmentsapplication.Models;

public class Division {

    private int DivisionId;
    private String Division;
    private int CountryId;

    /**
     * Constructor
     * 
     * @param divisionId
     * @param division
     * @param countryId
     */
    public Division(int divisionId, String division, int countryId) {
        DivisionId = divisionId;
        Division = division;
        CountryId = countryId;
    }

    /**
     * Getter for DivisionId
     * 
     * @return
     */
    public int getDivisionId() {
        return DivisionId;
    }

    /**
     * Setter for DivisionId
     * 
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        DivisionId = divisionId;
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
     * Getter for CountryId
     * 
     * @return
     */
    public int getCountryId() {
        return CountryId;
    }

    /**
     * Setter for CountryId
     * 
     * @param countryId
     */
    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

}