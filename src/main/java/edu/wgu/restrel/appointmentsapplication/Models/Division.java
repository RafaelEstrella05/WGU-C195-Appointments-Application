package edu.wgu.restrel.appointmentsapplication.Models;

/**
 * Division class for storing division information
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class Division {

    private int divisionId;
    private String division;
    private int countryId;

    /**
     * Constructor
     * 
     * @param divisionId
     * @param division
     * @param countryId
     */
    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Getter for DivisionId
     * 
     * @return
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Setter for DivisionId
     * 
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Getter for Division
     * 
     * @return Division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter for Division
     * 
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter for CountryId
     * 
     * @return
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Setter for CountryId
     * 
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

}