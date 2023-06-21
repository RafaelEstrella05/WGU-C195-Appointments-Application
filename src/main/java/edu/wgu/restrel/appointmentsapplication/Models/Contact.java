package edu.wgu.restrel.appointmentsapplication.Models;

/**
 * Contact class for storing contact information
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class Contact {

    private int contactId;
    private String contactName;
    private String email;

    /**
     * Constructor for Contact
     * 
     * @param contactId   Unique identifier for contact
     * @param contactName Name of contact
     * @param email       Email address of contact
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Getter for contactId
     * 
     * @return contactId Unique identifier for contact
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Getter for contactName
     * 
     * @return contactName Name of contact
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Getter for email
     * 
     * @return email Email address of contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for contactId
     * 
     * @param contactId Unique identifier for contact
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Setter for contactName
     * 
     * @param contactName Name of contact
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Setter for email
     * 
     * @param email Email address of contact
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return contactName;
    }

}
