package edu.wgu.restrel.appointmentsapplication.Controllers;

import edu.wgu.restrel.appointmentsapplication.AbstractClass.AppController;
import edu.wgu.restrel.appointmentsapplication.Models.*;
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
    private Appointment selectedAppointment;

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

    AppointmentDateTimes appointmentDateTimes;

    @FXML
    public void initialize() {
        contacts = new ArrayList<Contact>();

        appointmentDateTimes = new AppointmentDateTimes();

        // get contacts fron the database and populate the contacts list
        getContactsFromDB();

        // populate the contact choice box
        contactChoiceBox.getItems().addAll(contacts.stream().map(Contact::getContactName).toArray());

        // populate the start and end hour choice boxes with hours 0-23 and minutes 0-59
        populateTimeChoiceboxes();

        // populate the labels that indicate the business hours in the local time zone
        // and EST
        populateDateTimeLabels();

        // prefillWithTestData(); // FIX ME: remove when done testing

    }

    /**
     * Handles the on submit button click event, it will validate the form input and
     * if valid, save the appointment to the database
     */
    @FXML
    private void onSubmitButtonClick() {

        try {
            // get the form input validation state
            FormValidationState formValidationState = getFormInputValidationState();

            // if the form is valid, save the customer
            if (formValidationState.isValid()) {

                System.out.println("Form is valid.");

                // save the customer
                DatabaseManager dbmanager = new DatabaseManager();

                // get the appointment data
                String appointmentId = appointmentIdField.getText();
                String title = titleField.getText();
                String description = descriptionField.getText();
                String location = locationField.getText();
                String type = typeField.getText();
                String contactId = Integer
                        .toString(contacts.get(contactChoiceBox.getSelectionModel().getSelectedIndex()).getContactId());
                String userId = Integer.toString(getApp().getUser().getId());
                String customerId = Integer.toString(getSelectedCustomer().getCustomerId());

                // print form data
                System.out.println("Appointment ID: " + appointmentId);
                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
                System.out.println("Location: " + location);
                System.out.println("Type: " + type);
                System.out.println("Contact ID: " + contactId);
                System.out.println("User ID: " + userId);
                System.out.println("Customer ID: " + customerId);

                appointmentDateTimes.setSelectedLocalTimes(startHourChoiceBox.getValue().toString(),
                        startMinuteChoiceBox.getValue().toString(), startAmPmChoiceBox.getValue().toString(),
                        endHourChoiceBox.getValue().toString(), endMinuteChoiceBox.getValue().toString(),
                        endAmPmChoiceBox.getValue().toString());

                appointmentDateTimes.printTimes();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String formattedStartDateTime = appointmentDateTimes.getSelectedStartDateTimeUTC()
                        .format(formatter);
                String formattedEndDateTime = appointmentDateTimes.getSelectedEndDateTimeUTC().format(formatter);

                // if there is a selected appointment, update the appointment
                if (selectedAppointment != null) {
                    System.out.println("Updating appointment...");

                    String updateQuery = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = CURRENT_DATE, Created_By = 'appointmentapp', Last_Update = CURRENT_DATE, Last_Updated_By = 'appointmentapp', Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?;";

                    // run mysql query to search users table for user name and password
                    dbmanager.executeUpdate(updateQuery, (ps) -> {
                        System.out.println("Executed query: " + updateQuery);

                        // switch form to main view
                        try {
                            AppController appController = this.getApp().setShowScene("main.fxml",
                                    "Appointments Manager");
                            ((MainController) appController).setApp(this.getApp());
                            ((MainController) appController).refreshCustomerContent();

                        } catch (IOException e) {
                            System.out.println("IOException: " + e.getMessage());
                        }

                    }, title, description, location, type,
                            formattedStartDateTime,
                            formattedEndDateTime, customerId, userId, contactId, appointmentId);

                } else {
                    System.out.println("Creating new appointment...");
                    // if there is no selected appointment, create a new appointment

                    // reset the selected times to apointmentDateTimes

                    String insertQuery = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)"
                            +
                            "VALUES " +
                            "(?,?,?,?, ?, ?, CURRENT_DATE, 'appointmentapp', CURRENT_DATE, 'appointmentapp', ?, ?, ?);";

                    // run mysql query to search users table for user name and password
                    dbmanager.executeUpdate(insertQuery, (ps) -> {
                        System.out.println("Executed query: " + insertQuery);

                    }, title, description, location, type,
                            formattedStartDateTime,
                            formattedEndDateTime, customerId, userId, contactId);

                }

                // switch form to main view
                try {
                    AppController appController = this.getApp().setShowScene("main.fxml",
                            "Appointments Manager");
                    ((MainController) appController).setApp(this.getApp());
                    ((MainController) appController).refreshCustomerContent();

                } catch (IOException e) {
                    System.out.println("IOException: " + e.getMessage());
                }

            } else {
                // display the form validation state
                formStateLabel.setText(formValidationState.getMessage());
            }
        } catch (FormValidationException e) {
            System.out.println("Error validating form input: " + e.getMessage());

            alertWarning(e.getMessage(), "Error");
        } finally {

        }

    }

    /**
     * Cancels the appointment form and switches to the main view
     */
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

        // if selected appointment is null
        if (selectedAppointment == null) {
            // set the selected appointment to a new appointment
            formStateLabel.setText("Scheduling appointment for " + customer.getCustomerName());
        } else {

            if (getSelectedCustomer() != null) {
                // change the text for formStateLabel to indicate that the form is being updated
                formStateLabel.setText("Modifying appointment for " + getSelectedCustomer().getCustomerName());
            }

        }

        // change the text for formStateLabel

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
     * Set the selected appointment
     * 
     * @param appointment
     */
    public void setSelectedAppointment(Appointment appointment) {
        this.selectedAppointment = appointment;
    }

    /**
     * Get the selected appointment
     * 
     * @return
     */
    public Appointment getSelectedAppointment() {
        return this.selectedAppointment;
    }

    /**
     * Populate the form based on the selected customer
     * This is so that the customer id is automatically populated
     * 
     * @param appointment
     */
    public void populateForm(Appointment appointment) {

        // set the selected appointment
        setSelectedAppointment(appointment);

        // set the selected customer
        setSelectedCustomer(getApp().findCustomerById(appointment.getCustomerId()));

        // populate the form with the appointment data
        appointmentIdField.setText(Integer.toString(appointment.getAppointmentId()));
        titleField.setText(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        typeField.setText(appointment.getType());

        // set the contact choice box to the contact for the appointment
        contactChoiceBox.getSelectionModel().select(appointment.getContact());

        // set the date picker to the appointment date
        datePicker.setValue(appointment.getStartDate());

        // set the start and end times in the choice boxes
        startHourChoiceBox.getSelectionModel().select(appointment.getStartTime().getHour() % 12 - 1);
        startMinuteChoiceBox.getSelectionModel().select(appointment.getStartTime().getMinute());
        startAmPmChoiceBox.getSelectionModel().select(appointment.getStartTime().getHour() < 12 ? 0 : 1);

        endHourChoiceBox.getSelectionModel().select(appointment.getEndTime().getHour() % 12 - 1);
        endMinuteChoiceBox.getSelectionModel().select(appointment.getEndTime().getMinute());
        endAmPmChoiceBox.getSelectionModel().select(appointment.getEndTime().getHour() < 12 ? 0 : 1);

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

        // populate the start and end hour choice boxes with hours 0-12
        for (int i = 1; i <= 12; i++) {

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

        // preset the date picker to the current date
        datePicker.setValue(LocalDate.now());

        // Get the current time
        LocalTime currentTime = LocalTime.now();

        System.out.println("Current Time: " + currentTime);

        // Calculate the start time by adding an hour to the current time
        LocalTime startTime = currentTime;
        int startHour = startTime.getHour();
        int startMinute = startTime.getMinute();
        int startAmPm = startHour < 12 ? 0 : 1; // 0 for AM, 1 for PM

        // Calculate the end time by adding an hour and a half to the current time
        LocalTime endTime = currentTime.plusMinutes(30);
        int endHour = endTime.getHour();
        int endMinute = endTime.getMinute();
        int endAmPm = endHour < 12 ? 0 : 1; // 0 for AM, 1 for PM

        // Set the start time in the choice boxes
        startHourChoiceBox.getSelectionModel().select(startHour % 12);
        startMinuteChoiceBox.getSelectionModel().select(startMinute);
        startAmPmChoiceBox.getSelectionModel().select(startAmPm);

        // Set the end time in the choice boxes
        endHourChoiceBox.getSelectionModel().select(endHour % 12);
        endMinuteChoiceBox.getSelectionModel().select(endMinute);
        endAmPmChoiceBox.getSelectionModel().select(endAmPm);

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

        businessHoursLabel.setText(estStr + "\n" + localStr);

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

        if (getSelectedCustomer() != null && getSelectedAppointment() != null
                && appointmentIdField.getText().isEmpty()) {
            isValid = false;
            errorMessage += "Appointment ID cannot be empty.\n";
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

        if (isValid) {// if valid at this point keep checking
            // based on the start and end date and time, check if the appointment is within
            // business hours

            String startHour = startHourChoiceBox.getValue().toString();
            String startMinute = startMinuteChoiceBox.getValue().toString();
            String startAmPm = startAmPmChoiceBox.getValue().toString();

            String endHour = endHourChoiceBox.getValue().toString();
            String endMinute = endMinuteChoiceBox.getValue().toString();
            String endAmPm = endAmPmChoiceBox.getValue().toString();

            // create a LocalDateTime object for the start and end times to help with
            appointmentDateTimes = new AppointmentDateTimes();
            appointmentDateTimes.setSelectedLocalTimes(startHour, startMinute, startAmPm, endHour, endMinute,
                    endAmPm);

            // get date from date picker and set the selected date on the
            // appointmentDateTimes object
            LocalDate selectedDate = datePicker.getValue();
            appointmentDateTimes.setSelectedDateLocal(selectedDate);

            appointmentDateTimes.printBusinessHours();
            appointmentDateTimes.printTimes();

            // Check if the start time is before the end time
            if (appointmentDateTimes.getSelectedStartTimeLocal()
                    .isAfter(appointmentDateTimes.getSelectedEndTimeLocal())) {
                isValid = false;
                errorMessage += "Start time cannot be after end time \n";
            }

            // start time cannot be before business start time
            if (appointmentDateTimes.getSelectedStartTimeLocal().isBefore(AppointmentDateTimes.BUSINESS_START_LOCAL)) {
                isValid = false;
                errorMessage += "Start time cannot be before business hours\n";
            }

            // start time cannot be after business end time
            if (appointmentDateTimes.getSelectedStartTimeLocal().isAfter(AppointmentDateTimes.BUSINESS_END_LOCAL)) {
                isValid = false;
                errorMessage += "Start time cannot be after business hours \n";
            }

            // end time cannot be before business start time
            if (appointmentDateTimes.getSelectedEndTimeLocal().isBefore(AppointmentDateTimes.BUSINESS_START_LOCAL)) {
                isValid = false;
                errorMessage += "End time cannot be before business hours \n";
            }

            // end time cannot be after business end time
            if (appointmentDateTimes.getSelectedEndTimeLocal().isAfter(AppointmentDateTimes.BUSINESS_END_LOCAL)) {
                isValid = false;
                errorMessage += "End time cannot be after business hours \n";
            }

            // check if the appointment overlaps with another appointment for the same
            if (isAppointmentOverlapping()) {
                isValid = false;
                errorMessage += "Appointment time is taken, please select another time \n";
            }

            // if current date is the same as appointment date and the appointment start or
            // end time is before the current time
            if (appointmentDateTimes.getSelectedDateLocal().equals(LocalDate.now())
                    && (appointmentDateTimes.getSelectedStartTimeLocal().isBefore(LocalTime.now())
                            || appointmentDateTimes.getSelectedEndTimeLocal().isBefore(LocalTime.now()))) {
                isValid = false;
                errorMessage += "Appointment time cannot be in the past \n";
            }

            if (selectedAppointment != null) {

                /*
                // if the appointment date and time are different the selected appointment date
                // and time
                if (!appointmentDateTimes.getSelectedStartDateTimeLocal()
                        .equals(selectedAppointment.getStartDateLocalTime())
                        || !appointmentDateTimes.getSelectedEndDateTimeLocal()
                                .equals(selectedAppointment.getEndDateLocalTime())) {
                    // if current date is the same as appointment date and the appointment start or
                    // end time is before the current time
                    if (appointmentDateTimes.getSelectedDateLocal().equals(LocalDate.now())
                            && (appointmentDateTimes.getSelectedStartTimeLocal().isBefore(LocalTime.now())
                                    || appointmentDateTimes.getSelectedEndTimeLocal().isBefore(LocalTime.now()))) {
                        isValid = false;
                        errorMessage += "Appointment time cannot be in the past \n";
                    }
                }
                
                 */
            }

            // check if start and end are not equal
            if (appointmentDateTimes.getSelectedStartTimeLocal()
                    .equals(appointmentDateTimes.getSelectedEndTimeLocal())) {
                isValid = false;
                errorMessage += "Start time and end time cannot be the same \n";
            }

        }

        if (!isValid) {
            throw new FormValidationException(errorMessage);
        }

        formValidationState = new FormValidationState(isValid, errorMessage);

        return formValidationState;
    }

    /**
     * This method checks the database to check if the appointment times are
     * overlapping any other appointment times.
     * 
     * @return isOverlapping
     */
    private boolean isAppointmentOverlapping() {
        boolean isOverlapping = false;
        getApp().getAppointmentsFromDB(null, null); // refresh the appointments from the database

        // get the start and end times for the appointment
        LocalTime selectedStartTimeLocal = appointmentDateTimes.getSelectedStartTimeLocal();
        LocalTime selectedEndTimeLocal = appointmentDateTimes.getSelectedEndTimeLocal();

        // get date from appointmentDateTimes object
        LocalDate selectedDateLocal = appointmentDateTimes.getSelectedDateLocal();

        // convert the selected date and time to a LocalDateTime object
        LocalDateTime selectedStartDateTimeLocal = LocalDateTime.of(selectedDateLocal, selectedStartTimeLocal);
        LocalDateTime selectedEndDateTimeLocal = LocalDateTime.of(selectedDateLocal, selectedEndTimeLocal);

        // look through all of the app's appointments
        for (Appointment appointment : getApp().getAppointments()) {

            // if the appointment is the same as the selected appointment, skip it
            if (selectedAppointment != null
                    && appointment.getAppointmentId() == selectedAppointment.getAppointmentId()) {
                continue;
            }

            // get the start and end times for the appointment
            String appointmentStartTimeLocalStr = appointment.getStart();
            String appointmentEndTimeLocalStr = appointment.getEnd();

            // convert strings to utc zone date time
            LocalDateTime appointmentStartDateTimeLocalUTC = LocalDateTime
                    .parse(appointmentStartTimeLocalStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime appointmentEndDateTimeLocalUTC = LocalDateTime
                    .parse(appointmentEndTimeLocalStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // convert the appointment start and end times from utc to the user's local time
            // zone
            LocalDateTime appointmentStartDateTimeLocal = appointmentStartDateTimeLocalUTC
                    .atZone(ZoneOffset.UTC)
                    .withZoneSameInstant(defaultTimeZone)
                    .toLocalDateTime();
            LocalDateTime appointmentEndDateTimeLocal = appointmentEndDateTimeLocalUTC
                    .atZone(ZoneOffset.UTC)
                    .withZoneSameInstant(defaultTimeZone)
                    .toLocalDateTime();

            // check if the selected start time is between the start and end times of the
            // appointment and the User_ID is the same
            if (selectedStartDateTimeLocal.isAfter(appointmentStartDateTimeLocal)
                    && selectedStartDateTimeLocal.isBefore(appointmentEndDateTimeLocal)
                    && appointment.getUserId() == getApp().getUser().getId()) {
                isOverlapping = true;
                break;
            }

            // check if the selected end time is between the start and end times of the
            // appointment and the User_ID is the same
            if (selectedStartDateTimeLocal.isBefore(appointmentEndDateTimeLocal)
                    && appointmentEndDateTimeLocal.isBefore(appointmentStartDateTimeLocal)
                    && appointment.getUserId() == getApp().getUser().getId()) {
                isOverlapping = true;
                break;
            }

            // check if selected end time is not between the start and end times of the
            // appointment and the User_ID is the same
            if (selectedEndDateTimeLocal.isBefore(appointmentEndDateTimeLocal)
                    && selectedStartDateTimeLocal.isAfter(appointmentEndDateTimeLocal)
                    && appointment.getUserId() == getApp().getUser().getId()) {
                isOverlapping = true;
                break;
            }

            // check if selected start time is not between the start and end times of the
            // appointment and the User_ID is the same
            if (selectedEndDateTimeLocal.isAfter(appointmentStartDateTimeLocal)
                    && selectedStartDateTimeLocal.isBefore(appointmentStartDateTimeLocal)
                    && appointment.getUserId() == getApp().getUser().getId()) {
                isOverlapping = true;
                break;
            }

        }

        return isOverlapping;
    }

    private void prefillWithTestData() {
        // pre fill with test appointment data FIX ME: remove when done testing
        titleField.setText("Test Appointment");
        descriptionField.setText("Test Appointment Description");
        locationField.setText("Test Appointment Location");
        typeField.setText("Test Appointment Type");
        contactChoiceBox.getSelectionModel().selectFirst();

    }
}
