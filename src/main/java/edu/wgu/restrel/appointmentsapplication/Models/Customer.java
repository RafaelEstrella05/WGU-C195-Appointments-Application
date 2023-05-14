package edu.wgu.restrel.appointmentsapplication.Models;

public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

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
            int divisionId) {
        this.customerId = customerId;
        customerName = customer_Name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * Getter for Customer_ID
     * 
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;

    }

    /**
     * Setter for Customer_ID
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
     * Getter for Division_ID
     * 
     * @return divisionId
     */
    public int getDivisionId() {
        return divisionId;

    }

    /**
     * Setter for Division_ID
     * 
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

}
