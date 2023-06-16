package edu.wgu.restrel.appointmentsapplication.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
    private String start; // in UTC
    private String end; // in UTC
    private int customerId;
    private int userId;
    private int contactId;
    private String contact;
    private String startLocal;
    private String endLocal;
    private LocalDate startDate;

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
            String end, int customerId, int userId, int contactId, String contact) {

        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.startLocal = convertUTCStringToLocalString(start);
        this.endLocal = convertUTCStringToLocalString(end);
        setStartDate(start);
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contact = contact;

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
     * Getter for startLocal.
     * 
     * @return startLocal
     */
    public String getStartLocal() {
        return this.startLocal;
    }

    /**
     * Setter for startLocal.
     * 
     * @param startLocal
     */
    public void setStartLocal(String startLocal) {
        this.startLocal = startLocal;
    }

    /**
     * Getter for endLocal.
     * 
     * @return endLocal
     */
    public String getEndLocal() {
        return this.endLocal;
    }

    /**
     * Setter for endLocal.
     * 
     * @param endLocal
     */
    public void setEndLocal(String endLocal) {
        this.endLocal = endLocal;
    }

    /**
     * Getter for startDate in LocalDate format.
     * 
     * @return startDate
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Setter for startDate in LocalDate format.
     * 
     * @param start
     */
    public void setStartDate(String start) {

        // convert start to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("UTC"));
        ZonedDateTime utcDateTime = ZonedDateTime.parse(start, formatter);

        // Convert the UTC ZonedDateTime to the system's default time zone
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localZoneDateTime = utcDateTime.withZoneSameInstant(localZoneId);

        // Extract the LocalDate component from the localZoneDateTime
        LocalDate localStartDate = localZoneDateTime.toLocalDate();

        // set the localStartDate
        this.startDate = localStartDate;

    }

    /**
     * Getter for startTime in LocalTime format.
     * converts from db stored UTC to local time
     * 
     * @return startTime
     */
    public LocalTime getStartTime() {

        // parse the UTC date time string into a ZonedDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("UTC"));
        ZonedDateTime utcDateTime = ZonedDateTime.parse(start, formatter);

        // Convert utcDateTime to the system's default time zone
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localZoneDateTime = utcDateTime.withZoneSameInstant(localZoneId);

        // Extract the local time component from localZoneDateTime
        LocalTime localStartTime = localZoneDateTime.toLocalTime();

        // Return the localStartTime
        return localStartTime;
    }

    /**
     * Getter for endTime in LocalTime format.
     * 
     * @return endTime
     */
    public LocalTime getEndTime() {

        // parse the UTC date time string into a ZonedDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("UTC"));
        ZonedDateTime utcDateTime = ZonedDateTime.parse(end, formatter);

        // Convert endDateTime to the system's default time zone
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localZoneDateTime = utcDateTime.withZoneSameInstant(localZoneId);

        // Extract the local time component from localZoneDateTime
        LocalTime localEndTime = localZoneDateTime.toLocalTime();

        // Return the localEndTime
        return localEndTime;
    }

    /**
     * Converts a UTC date time string to a local date time string from this format
     * ("yyyy-MM-dd HH:mm:ss")
     * to this format ("yyyy-MM-dd HH:mm a")
     * 
     * @param utcDateTimeString
     * @return localDateTimeString
     */
    public String convertUTCStringToLocalString(String utcDateTimeString) {

        // Parse the UTC date time string into a ZonedDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("UTC"));
        ZonedDateTime utcDateTime = ZonedDateTime.parse(utcDateTimeString, formatter);

        // Convert utcDateTime to the system's default time zone
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localZoneDateTime = utcDateTime.withZoneSameInstant(localZoneId);

        // Extract the local time component from localZoneDateTime
        LocalDateTime localDateTime = localZoneDateTime.toLocalDateTime();

        // Convert the localDateTime to a string in 12-hour format with AM/PM
        String localDateTimeString = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));

        // Return the localDateTimeString
        return localDateTimeString;
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

    /**
     * Getter for contact.
     * 
     * @return contact
     */
    public String getContact() {
        return this.contact;
    }

    /**
     * Setter for contact.
     * 
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

}