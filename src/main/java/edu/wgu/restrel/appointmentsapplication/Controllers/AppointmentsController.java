package edu.wgu.restrel.appointmentsapplication.Controllers;

import edu.wgu.restrel.appointmentsapplication.AbstractClass.AppController;
import edu.wgu.restrel.appointmentsapplication.Models.Appointment;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentsController extends AppController {
    private ZoneId defaultTimeZone = ZoneId.systemDefault();

    // ...

    /*
     * public void populateForm(Appointment appointment) {
     * // Convert UTC start and end timestamps to the user's local time zone
     * ZonedDateTime startDateTime =
     * appointment.getStartDateTime().withZoneSameInstant(defaultTimeZone);
     * ZonedDateTime endDateTime =
     * appointment.getEndDateTime().withZoneSameInstant(defaultTimeZone);
     * 
     * // Display the appointment information in the user's local time zone
     * DateTimeFormatter formatter =
     * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     * String startDateTimeString = startDateTime.format(formatter);
     * String endDateTimeString = endDateTime.format(formatter);
     * 
     * // Populate the form fields with the appointment data
     * appointmentIdField.setText(appointment.getAppointmentId());
     * titleField.setText(appointment.getTitle());
     * descriptionField.setText(appointment.getDescription());
     * // ... populate other fields
     * 
     * // Display the converted appointment start and end timestamps in the local
     * time
     * // zone
     * startDateTimeField.setText(startDateTimeString);
     * endDateTimeField.setText(endDateTimeString);
     * 
     * // Disable the Appointment_ID field
     * appointmentIdField.setDisable(true);
     * }
     */

    // ...
}
