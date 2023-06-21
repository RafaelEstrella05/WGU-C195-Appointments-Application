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
     * @param divisionId Unique identifier for division
     * @param division   Name of division
     * @param countryId  Unique identifier for country
     */
    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Getter for DivisionId
     * 
     * @return DivisionId Unique identifier for division
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Setter for DivisionId
     * 
     * @param divisionId Unique identifier for division
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Getter for Division
     * 
     * @return Division Name of division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter for Division
     * 
     * @param division Name of division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter for CountryId
     * 
     * @return countryId Unique identifier for country
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Setter for CountryId
     * 
     * @param countryId Unique identifier for country
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

}