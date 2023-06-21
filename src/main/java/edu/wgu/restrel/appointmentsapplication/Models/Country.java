package edu.wgu.restrel.appointmentsapplication.Models;

import java.util.ArrayList;

/**
 * Country class for storing country information
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class Country {

    private int countryId;
    private String country;
    private ArrayList<Division> associatedDivisions;

    /**
     * Default constructor
     */
    public Country() {
        associatedDivisions = new ArrayList<Division>();
    }

    /**
     * Constructor
     * 
     * @param countryId Unique identifier for country
     * @param country   Name of country
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
        associatedDivisions = new ArrayList<Division>();
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

    /**
     * Getter for Country
     * 
     * @return country Name of country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for Country
     * 
     * @param country Name of country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for divisions
     * 
     * @return associatedDivisions List of divisions associated with country
     */
    public ArrayList<Division> getAssociatedDivisions() {
        return associatedDivisions;
    }

    /**
     * Setter for divisions
     * 
     * @param associatedDivisions List of divisions associated with country
     */
    public void setAssociatedDivisions(ArrayList<Division> associatedDivisions) {
        this.associatedDivisions = associatedDivisions;
    }

    /**
     * Adds a division to the divisions list
     * 
     * @param division Division to add
     */
    public void addAssociatedDivision(Division division) {
        associatedDivisions.add(division);
    }

    /**
     * finds a division by id
     * 
     * @param id Unique identifier for division
     * @return division; null if not found
     */
    public Division findDivisionById(int id) {
        for (Division division : associatedDivisions) {
            if (division.getDivisionId() == id) {
                return division;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return country;
    }

}