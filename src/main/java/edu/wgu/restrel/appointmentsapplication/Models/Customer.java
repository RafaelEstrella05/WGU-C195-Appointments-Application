package edu.wgu.restrel.appointmentsapplication.Models;

/**
 * Customer class for storing customer information
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private String division;
    private int countryId;
    private String country;

    // default constructor
    public Customer() {
    }

    /**
     * Constructor
     * 
     * @param customerId    Unique identifier for customer
     * @param customer_Name Name of customer
     * @param address       Address of customer
     * @param postalCode    Postal code of customer
     * @param phone         Phone number of customer
     * @param divisionId    Unique identifier for division
     */
    public Customer(int customerId, String customer_Name, String address, String postalCode, String phone,
            int divisionId, String division, int countryId, String country) {
        this.customerId = customerId;
        customerName = customer_Name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * Getter for CustomerId
     * 
     * @return customerId Unique identifier for customer
     */
    public int getCustomerId() {
        return customerId;

    }

    /**
     * Setter for CustomerId
     * 
     * @param customerId Unique identifier for customer
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;

    }

    /**
     * Getter for Customer_Name
     * 
     * @return customerName Name of customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter for Customer_Name
     * 
     * @param customerName Name of customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for Address
     * 
     * @return address Address of customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for Address
     * 
     * @param address Address of customer
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for Postal_Code
     * 
     * @return postalCode Postal code of customer
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for Postal_Code
     * 
     * @param postalCode Postal code of customer
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter for Phone
     * 
     * @return phone Phone number of customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for Phone
     * 
     * @param phone Phone number of customer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for DivisionId
     * 
     * @return divisionId Unique identifier for division
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
     * @return division Name of division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter for Division
     * 
     * @param division Division of customer
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter for CountryId
     * 
     * @param countryId Unique identifier for country
     */
    public void getCountryId(int countryId) {
        this.countryId = countryId;
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

}
