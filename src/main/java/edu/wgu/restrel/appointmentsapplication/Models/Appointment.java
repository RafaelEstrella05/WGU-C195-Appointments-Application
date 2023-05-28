package edu.wgu.restrel.appointmentsapplication.Models;

/**
 * This is the Appointment class. It is used to keep track of appointment
 * information related to a customer.
 */
public class Appointment {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * Constructor for Appointment class.
     * 
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, String start,
            String end, int customerId, int userId, int contactId) {

        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;

    }

    /**
     * Getter for appointmentId.
     * 
     * @return appointmentId
     */
    public int getAppointmentId() {
        return this.appointmentId;
    }

    /**
     * Setter for appointmentId.
     * 
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Getter for title.
     * 
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Setter for title.
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for description.
     * 
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for description.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for location.
     * 
     * @return location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Setter for location.
     * 
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for type.
     * 
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Setter for type.
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for start.
     * 
     * @return start
     */
    public String getStart() {
        return this.start;
    }

    /**
     * Setter for start.
     * 
     * @param start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Getter for end.
     * 
     * @return end
     */
    public String getEnd() {
        return this.end;
    }

    /**
     * Setter for end.
     * 
     * @param end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Getter for customerId.
     * 
     * @return customerId
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * Setter for customerId.
     * 
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for userId.
     * 
     * @return userId
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * Setter for userId.
     * 
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for contactId.
     * 
     * @return contactId
     */
    public int getContactId() {
        return this.contactId;
    }

    /**
     * Setter for contactId.
     * 
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

}