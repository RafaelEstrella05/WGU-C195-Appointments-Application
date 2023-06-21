package edu.wgu.restrel.appointmentsapplication.Models;

import java.time.*;

/**
 * FormAppointmentDateTimes class for managing date and times on the
 * appointments form
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class FormAppointmentDateTimes {

    // static value for business hours start and end times 8am - 10pm EST
    public static final LocalTime BUSINESS_START_LOCAL;
    public static final LocalTime BUSINESS_END_LOCAL;

    static {
        // set business hours
        ZoneId estZone = ZoneId.of("America/New_York");
        ZonedDateTime estStartTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), estZone);
        ZonedDateTime estEndTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), estZone);

        // Convert to local time dynamically
        ZoneId localZone = ZoneId.systemDefault();
        ZonedDateTime localStartTime = estStartTime.withZoneSameInstant(localZone);
        ZonedDateTime localEndTime = estEndTime.withZoneSameInstant(localZone);

        BUSINESS_START_LOCAL = localStartTime.toLocalTime();
        BUSINESS_END_LOCAL = localEndTime.toLocalTime();
    }

    private LocalDate selectedDateLocal;
    private LocalTime selectedStartTimeLocal;
    private LocalTime selectedEndTimeLocal;

    /**
     * Constructor for DateTimeAppointmentManager
     */
    public FormAppointmentDateTimes() {
    }

    /**
     * Getter for selectedDateLocal
     * 
     * @return selectedDateLocal
     */
    public LocalDate getSelectedDateLocal() {
        return selectedDateLocal;
    }

    /**
     * Getter for selectedStartTimeLocal
     * 
     * @return selectedStartTimeLocal
     */
    public LocalTime getSelectedStartTimeLocal() {
        return selectedStartTimeLocal;
    }

    /**
     * Gets the selected start date and time in local format based on
     * selectedDateLocal and selectedStartTimeLocal.
     * 
     * @return LocalDateTime
     */
    public LocalDateTime getSelectedStartDateTimeLocal() {
        return LocalDateTime.of(selectedDateLocal, selectedStartTimeLocal);
    }

    /**
     * Gets the selected end date and time in local format based on
     * selectedDateLocal and selectedEndTimeLocal.
     * 
     * @return LocalDateTime
     */
    public LocalDateTime getSelectedEndDateTimeLocal() {
        return LocalDateTime.of(selectedDateLocal, selectedEndTimeLocal);
    }

    /**
     * Gets the selected start date and time in UTC format.
     * 
     * @return ZonedDateTime
     */
    public ZonedDateTime getSelectedStartDateTimeUTC() {
        LocalDateTime localDateTime = LocalDateTime.of(selectedDateLocal, selectedStartTimeLocal);
        ZoneId localZone = ZoneId.systemDefault();
        ZoneOffset localOffset = localZone.getRules().getOffset(localDateTime);
        return localDateTime.atOffset(localOffset).toInstant().atZone(ZoneOffset.UTC);
    }

    // getSelectedEndDateTimeUTC
    /**
     * Gets the selected end date and time in UTC format.
     * 
     * @return ZonedDateTime
     */
    public ZonedDateTime getSelectedEndDateTimeUTC() {
        LocalDateTime localDateTime = LocalDateTime.of(selectedDateLocal, selectedEndTimeLocal);
        ZoneId localZone = ZoneId.systemDefault();
        ZoneOffset localOffset = localZone.getRules().getOffset(localDateTime);
        return localDateTime.atOffset(localOffset).toInstant().atZone(ZoneOffset.UTC);
    }

    /**
     * Getter for selectedEndTimeLocal
     * 
     * @return selectedEndTimeLocal
     */
    public LocalTime getSelectedEndTimeLocal() {
        return selectedEndTimeLocal;
    }

    /**
     * Setter for dateLocal
     * 
     * @param dateLocal
     */
    public void setSelectedDateLocal(LocalDate dateLocal) {
        this.selectedDateLocal = dateLocal;
    }

    /**
     * Set the start and end times for the appointment based on LocalTime objects
     */
    public void setSelectedLocalTimes(LocalTime selectedStartTimeLocal, LocalTime selectedEndTimeLocal) {
        this.selectedStartTimeLocal = selectedStartTimeLocal;
        this.selectedEndTimeLocal = selectedEndTimeLocal;
    }

    /**
     * sets the selected Start and End times based on the string values for hour,
     * minute, and am/pm
     * 
     * @param startHour
     * @param startMinute
     * @param startAmPm
     * @param endHour
     * @param endMinute
     * @param endAmPm
     */
    public void setSelectedLocalTimes(String startHour, String startMinute, String startAmPm, String endHour,
            String endMinute, String endAmPm) {

        int startHourValue = Integer.parseInt(startHour);
        int endHourValue = Integer.parseInt(endHour);

        if (startAmPm.equals("PM") && startHourValue != 12) {
            startHourValue += 12;
        } else if (startAmPm.equals("AM") && startHourValue == 12) {
            startHourValue = 0;
        }

        if (endAmPm.equals("PM") && endHourValue != 12) {
            endHourValue += 12;
        } else if (endAmPm.equals("AM") && endHourValue == 12) {
            endHourValue = 0;
        }

        LocalTime startTimeLocal = LocalTime.of(startHourValue, Integer.parseInt(startMinute));
        LocalTime endTimeLocal = LocalTime.of(endHourValue, Integer.parseInt(endMinute));

        System.out.println("Start time local: " + startTimeLocal);
        System.out.println("End time local: " + endTimeLocal);

        setSelectedLocalTimes(startTimeLocal, endTimeLocal);

    }

    // print business hours
    public void printBusinessHours() {
        System.out.println("Business hours: " + BUSINESS_START_LOCAL + " - " + BUSINESS_END_LOCAL);
    }

    public void printTimes() {
        System.out.println("Selected date: " + selectedDateLocal);
        System.out.println("Selected start time: " + selectedStartTimeLocal);
        System.out.println("Selected end time: " + selectedEndTimeLocal);
    }

}
