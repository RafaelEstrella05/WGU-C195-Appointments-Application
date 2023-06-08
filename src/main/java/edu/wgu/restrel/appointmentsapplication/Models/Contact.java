package edu.wgu.restrel.appointmentsapplication.Models;

public class Contact {

    private int contactId;
    private String contactName;
    private String email;

    /**
     * Constructor for Contact
     * 
     * @param contactId
     * @param contactName
     * @param email
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Getter for contactId
     * 
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Getter for contactName
     * 
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Getter for email
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for contactId
     * 
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Setter for contactName
     * 
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Setter for email
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return contactName;
    }

}
