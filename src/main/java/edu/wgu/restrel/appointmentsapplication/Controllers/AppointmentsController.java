package edu.wgu.restrel.appointmentsapplication.Controllers;

import edu.wgu.restrel.appointmentsapplication.AbstractClass.AppController;
import edu.wgu.restrel.appointmentsapplication.Models.Contact;
import edu.wgu.restrel.appointmentsapplication.Models.Customer;
import edu.wgu.restrel.appointmentsapplication.Models.FormValidationException;
import edu.wgu.restrel.appointmentsapplication.Models.FormValidationState;
import edu.wgu.restrel.appointmentsapplication.Utils.DatabaseManager;
import edu.wgu.restrel.appointmentsapplication.interfaces.FormValidation;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AppointmentsController extends AppController implements FormValidation {
    private ZoneId defaultTimeZone = ZoneId.systemDefault();

    List<Contact> contacts;

    private Customer selectedCustomer;

    @FXML
    private Label formStateLabel;

    @FXML
    private TextField appointmentIdField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField typeField;

    @FXML
    private ChoiceBox contactChoiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox startHourChoiceBox;

    @FXML
    private ChoiceBox startMinuteChoiceBox;

    @FXML
    private ChoiceBox startAmPmChoiceBox;

    @FXML
    private ChoiceBox endHourChoiceBox;

    @FXML
    private ChoiceBox endMinuteChoiceBox;

    @FXML
    private ChoiceBox endAmPmChoiceBox;

    @FXML
    private Label businessHoursLabel;

    @FXML
    public void initialize() {
        contacts = new ArrayList<Contact>();

        // get contacts fron the database and populate the contacts list
        getContactsFromDB();

        // populate the contact choice box
        contactChoiceBox.getItems().addAll(contacts.stream().map(Contact::getContactName).toArray());

        // populate the start and end hour choice boxes with hours 0-23 and minutes 0-59
        populateTimeChoiceboxes();

        // populate the labels that indicate the business hours in the local time zone
        // and EST
        populateDateTimeLabels();

        prefillWithTestData(); // FIX ME: remove when done testing

    }

    @FXML
    private void onSubmitButtonClick() {

        try {
            // get the form input validation state
            FormValidationState formValidationState = getFormInputValidationState();

            // if the form is valid, save the customer
            if (formValidationState.isValid()) {
                // save the customer
                // saveCustomer();
                // close the form
                // closeForm();

                System.out.println("Form is valid.");

            } else {
                // display the form validation state
                formStateLabel.setText(formValidationState.getMessage());
            }
        } catch (FormValidationException e) {
            System.out.println("Error validating form input: " + e.getMessage());

            alertWarning(e.getMessage(), "Error");
        }

    }

    @FXML
    private void onCancelButtonClick() {

        try {
            AppController appController = this.getApp().setShowScene("main.fxml", "Appointments Manager");
            ((MainController) appController).setApp(this.getApp());
            ((MainController) appController).refreshCustomerContent();

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Set the selected customer
     * 
     * @param customer
     */
    public void setSelectedCustomer(Customer customer) {
        this.selectedCustomer = customer;
    }

    /**
     * Get the selected customer
     * 
     * @return
     */
    public Customer getSelectedCustomer() {
        return this.selectedCustomer;
    }

    /**
     * Populate the form based on the selected customer
     * This is so that the customer id is automatically populated
     * 
     * @param customer
     */
    public void populateForm(Customer customer) {

    }

    /**
     * Get the contacts from the database and populates the contacts list belonging
     * to this controller
     */
    private void getContactsFromDB() {

        String query = "SELECT * FROM contacts; ";

        // create DatabaseManager instance
        DatabaseManager dbmanager = new DatabaseManager();

        // run mysql query to search users table for user name and password
        dbmanager.executeQuery(query, (rs) -> {
            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact contact = new Contact(contactId, contactName, email);

                this.contacts.add(contact);
            }
        });

    }

    /**
     * Populates the start and end time choice boxes with hours 0-23 and minutes
     * 0-59
     */
    private void populateTimeChoiceboxes() {

        // populate the start and end hour choice boxes with hours 0-23
        for (int i = 0; i < 24; i++) {

            String hour = String.format("%02d", i);
            startHourChoiceBox.getItems().add(hour);
            endHourChoiceBox.getItems().add(hour);
        }

        // populate the start and end minute choice boxes with minutes 0-59
        for (int i = 0; i < 60; i++) {

            String minute = String.format("%02d", i);
            startMinuteChoiceBox.getItems().add(minute);
            endMinuteChoiceBox.getItems().add(minute);
        }

        // populate the start and end am/pm choice boxes
        startAmPmChoiceBox.getItems().addAll("AM", "PM");
        endAmPmChoiceBox.getItems().addAll("AM", "PM");

        // select the first hour and minute in the choice boxes
        startHourChoiceBox.getSelectionModel().selectFirst();
        startMinuteChoiceBox.getSelectionModel().selectFirst();
        endHourChoiceBox.getSelectionModel().selectFirst();
        endMinuteChoiceBox.getSelectionModel().selectFirst();

    }

    private void populateDateTimeLabels() {
        // get the business hours in the EST so that it can be converted to the user's
        // local time zone
        ZoneId estZone = ZoneId.of("America/New_York");
        ZonedDateTime businessStartEST = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), estZone);
        ZonedDateTime businessEndEST = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), estZone);

        // convert the business hours to the user's local time zone
        ZonedDateTime businessStartLocal = businessStartEST.withZoneSameInstant(defaultTimeZone);
        ZonedDateTime businessEndLocal = businessEndEST.withZoneSameInstant(defaultTimeZone);

        // display the business hours in EST plus the time zone
        String estStr = ("Business Hours: \n" + formatTime(businessStartEST.toLocalTime()) + " - "
                + formatTime(businessEndEST.toLocalTime()) + " " + estZone.getDisplayName(TextStyle.SHORT, Locale.US));

        // display the business hours in the user's local time zone plus the time zone
        String localStr = ("" + formatTime(businessStartLocal.toLocalTime()) + " - "
                + formatTime(businessEndLocal.toLocalTime()) + " "
                + defaultTimeZone.getDisplayName(TextStyle.SHORT, Locale.getDefault()) + " (local time)");

        String utcStr = ("UTC: \n" + formatTime(businessStartEST.withZoneSameInstant(ZoneOffset.UTC).toLocalTime())
                + " - "
                + formatTime(businessEndEST.withZoneSameInstant(ZoneOffset.UTC).toLocalTime()) + " UTC");

        businessHoursLabel.setText(estStr + "   /   " + localStr + "   /   " + utcStr);

    }

    private String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    /**
     * Validates the form input for appointment data
     */
    @Override
    public FormValidationState getFormInputValidationState() throws FormValidationException {

        FormValidationState formValidationState;

        boolean isValid = true;
        String errorMessage = "";

        if (selectedCustomer != null && appointmentIdField.getText().isEmpty()) {
            isValid = false;
            errorMessage += "Appointment ID cannot be empty.\n";
        }

        if (titleField.getText().isEmpty()) {
            isValid = false;
            errorMessage += "Title cannot be empty.\n";
        }

        if (descriptionField.getText().isEmpty()) {
            isValid = false;
            errorMessage += "Description cannot be empty \n";
        }

        if (locationField.getText().isEmpty()) {
            isValid = false;
            errorMessage += "Location cannot be empty \n";
        }

        if (typeField.getText().isEmpty()) {
            isValid = false;
            errorMessage += "Type cannot be empty \n";
        }

        if (contactChoiceBox.getValue() == null) {
            isValid = false;
            errorMessage += "Contact cannot be empty \n";
        }

        // is startDateTimePicker empty
        if (datePicker.getValue() == null) {
            isValid = false;
            errorMessage += "Start date cannot be empty \n";
        }

        // is startHourChoiceBox empty
        if (startHourChoiceBox.getValue() == null) {
            isValid = false;
            errorMessage += "Start hour cannot be empty \n";
        }

        // is startMinuteChoiceBox empty
        if (startMinuteChoiceBox.getValue() == null) {
            isValid = false;
            errorMessage += "Start minute cannot be empty \n";
        }

        // is endHourChoiceBox empty
        if (endHourChoiceBox.getValue() == null) {
            isValid = false;
            errorMessage += "End hour cannot be empty \n";
        }

        // is endMinuteChoiceBox empty
        if (endMinuteChoiceBox.getValue() == null) {
            isValid = false;
            errorMessage += "End minute cannot be empty \n";
        }

        if (isValid) {// has to be valid since datePicker should not be null
            // based on the start and end date and time, check if the appointment is within
            // business hours
            LocalDate startDate = datePicker.getValue();

            LocalTime startTimeLocal = LocalTime.of(Integer.parseInt(startHourChoiceBox.getValue().toString()),
                    Integer.parseInt(startMinuteChoiceBox.getValue().toString()));

            LocalTime endTimeLocal = LocalTime.of(Integer.parseInt(endHourChoiceBox.getValue().toString()),
                    Integer.parseInt(endMinuteChoiceBox.getValue().toString()));

            System.out.println("Start time local: " + startTimeLocal);

            ZoneId utcZone = ZoneId.of("UTC");
            ZonedDateTime startDateTimeLocal = ZonedDateTime.of(startDate, startTimeLocal, defaultTimeZone);
            ZonedDateTime startDateTimeUTC = startDateTimeLocal.withZoneSameInstant(utcZone);

            ZonedDateTime endDateTimeLocal = ZonedDateTime.of(startDate, endTimeLocal, defaultTimeZone);
            ZonedDateTime endDateTimeUTC = endDateTimeLocal.withZoneSameInstant(utcZone);

            // alert startDateTimeUTC
            System.out.println("Start time in UTC: " + startDateTimeUTC);
            System.out.println("End time in UTC: " + endDateTimeUTC);

            // get business hours in EST
            ZoneId estZone = ZoneId.of("America/New_York");
            ZonedDateTime businessStartEST = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), estZone);
            ZonedDateTime businessEndEST = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), estZone);

            // convert the business hours to the UTC
            ZonedDateTime businessStartLocal = businessStartEST.withZoneSameInstant(defaultTimeZone);
            ZonedDateTime businessEndLocal = businessEndEST.withZoneSameInstant(defaultTimeZone);

            LocalTime businessStartTime = businessStartLocal.toLocalTime();
            LocalTime businessEndTime = businessEndLocal.toLocalTime();

            // check if the start and end time are within business hours
            if (startTimeLocal.isBefore(businessStartTime) || startTimeLocal.isAfter(businessEndTime)) {
                isValid = false;
                errorMessage += "Start time is not within business hours \n";
            }

            if (endTimeLocal.isBefore(businessStartTime) || endTimeLocal.isAfter(businessEndTime)) {
                isValid = false;
                errorMessage += "End time is not within business hours \n";
            }

            // Check if the start time is before the end time
            if (startTimeLocal.isAfter(endTimeLocal)) {
                isValid = false;
                errorMessage += "Start time cannot be after end time \n";
            }

        }

        if (!isValid) {
            throw new FormValidationException(errorMessage);
        }

        formValidationState = new FormValidationState(isValid, errorMessage);

        return formValidationState;
    }

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

    private void prefillWithTestData() {
        // pre fill with test appointment data FIX ME: remove when done testing
        titleField.setText("Test Appointment");
        descriptionField.setText("Test Appointment Description");
        locationField.setText("Test Appointment Location");
        typeField.setText("Test Appointment Type");
        contactChoiceBox.getSelectionModel().selectFirst();

        // set the start date to tomorrow
        datePicker.setValue(LocalDate.now().plusDays(1));

        // set the start time to 8:01 AM
        startHourChoiceBox.getSelectionModel().select(8);
        startMinuteChoiceBox.getSelectionModel().select(1);
        startAmPmChoiceBox.getSelectionModel().select(0);

        // set the end time to 8:31 AM
        endHourChoiceBox.getSelectionModel().select(8);
        endMinuteChoiceBox.getSelectionModel().select(31);
        endAmPmChoiceBox.getSelectionModel().select(0);

    }
}
