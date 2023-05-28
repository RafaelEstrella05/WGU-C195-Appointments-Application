package edu.wgu.restrel.appointmentsapplication.Models;

public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private String division;
    private int countyId;
    private String country;

    // default constructor
    public Customer() {
    }

    /**
     * Constructor
     * 
     * @param customerId
     * @param customer_Name
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionId
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
        this.countyId = countryId;
        this.country = country;
    }

    /**
     * Getter for CustomerId
     * 
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;

    }

    /**
     * Setter for CustomerId
     * 
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;

    }

    /**
     * Getter for Customer_Name
     * 
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter for Customer_Name
     * 
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for Address
     * 
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for Address
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for Postal_Code
     * 
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for Postal_Code
     * 
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter for Phone
     * 
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for Phone
     * 
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for DivisionId
     * 
     * @return divisionId
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
     * @return division
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
     * @return countryId
     */
    public void getCountryId(int countryId) {
        this.countyId = countryId;
    }

    /**
     * Setter for CountryId
     * 
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countyId = countryId;
    }

    /**
     * Getter for Country
     * 
     * @return country
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

}
