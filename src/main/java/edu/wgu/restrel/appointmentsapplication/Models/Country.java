package edu.wgu.restrel.appointmentsapplication.Models;

import java.util.ArrayList;

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
     * @param countryId
     * @param country
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
        associatedDivisions = new ArrayList<Division>();
    }

    /**
     * Getter for Country_ID
     * 
     * @return
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Setter for Country_ID
     * 
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Getter for Country
     * 
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for Country
     * 
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for divisions
     * 
     * @return
     */
    public ArrayList<Division> getAssociatedDivisions() {
        return associatedDivisions;
    }

    /**
     * Setter for divisions
     * 
     * @param associatedDivisions
     */
    public void setAssociatedDivisions(ArrayList<Division> associatedDivisions) {
        this.associatedDivisions = associatedDivisions;
    }

    /**
     * Adds a division to the divisions list
     * 
     * @param division
     */
    public void addAssociatedDivision(Division division) {
        associatedDivisions.add(division);
    }

    /**
     * finds a division by id
     * 
     * @param id
     * @return division; null if not found
     */
    public Division findDivisionById(int id) {
        for (Division division : associatedDivisions) {
            if (division.getDivision_ID() == id) {
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