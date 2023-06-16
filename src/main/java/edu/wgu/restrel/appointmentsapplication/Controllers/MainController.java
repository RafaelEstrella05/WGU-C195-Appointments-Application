package edu.wgu.restrel.appointmentsapplication.Controllers;

import edu.wgu.restrel.appointmentsapplication.AbstractClass.AppController;
import edu.wgu.restrel.appointmentsapplication.Models.*;
import edu.wgu.restrel.appointmentsapplication.Utils.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MainController extends AppController {

    /* Customers TableView Components */

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, String> postalCodeColumn;

    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;

    @FXML
    private TableColumn<Customer, Integer> divisionColumn;

    @FXML
    private TableColumn<Customer, String> countryColumn;

    /* Appointments TableView Components */
    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentContactColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentStartColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerIdColumn;

    @FXML
    private TableColumn<Appointment, Integer> appointmentUserIdColumn;

    /* nav bar buttons */
    @FXML
    private Button customersButton;

    @FXML
    private Button appointmentsButton;

    @FXML
    private Button reportsButton;

    /* customer buttons */
    @FXML
    private Button addCustomerButton;

    @FXML
    private Button modifyCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    /* appointment buttons */
    @FXML
    private Button addAppointmentButton;

    @FXML
    private Button modifyAppointmentButton;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private ChoiceBox monthChoiceBox;
    @FXML
    private ChoiceBox weekChoiceBox;

    /* exit */
    @FXML
    private Button exitButton;

    ArrayList<Button> navButtons;

    /* vbox elements */
    @FXML
    private VBox customersVBox;

    @FXML
    private VBox appointmentsVBox;

    @FXML
    private VBox reportsVBox;

    ArrayList<VBox> vboxes;

    private int monthScrollIndex = 0; // is used to determine if the user is scrolling forward or backward through the
                                      // months

    // Map month names to numbers
    Map<String, String> monthMap = new HashMap<>();

    /**
     * This method is called by the FXMLLoader when initialization is complete
     * it initializes the navButtons and vboxes array and sets the default selected
     * button
     */
    @FXML
    private void initialize() {

        // Map month names to numbers
        monthMap.put("JANUARY", "01");
        monthMap.put("FEBRUARY", "02");
        monthMap.put("MARCH", "03");
        monthMap.put("APRIL", "04");
        monthMap.put("MAY", "05");
        monthMap.put("JUNE", "06");
        monthMap.put("JULY", "07");
        monthMap.put("AUGUST", "08");
        monthMap.put("SEPTEMBER", "09");
        monthMap.put("OCTOBER", "10");
        monthMap.put("NOVEMBER", "11");
        monthMap.put("DECEMBER", "12");

        // Disable automatic hiding of popup

        // initialize the button array
        navButtons = new ArrayList<Button>();
        navButtons.add(customersButton);
        navButtons.add(appointmentsButton);
        navButtons.add(reportsButton);

        // initialize the vbox array
        vboxes = new ArrayList<VBox>();
        vboxes.add(customersVBox);
        vboxes.add(appointmentsVBox);
        vboxes.add(reportsVBox);

        // set the default selected button (customers)
        selectNavButton(customersButton);

        // columns for customers table
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));

        // set the width of the columns
        customerNameColumn.prefWidthProperty().bind(appointmentsTable.widthProperty().multiply(0.1));
        phoneNumberColumn.prefWidthProperty().bind(appointmentsTable.widthProperty().multiply(0.1));
        addressColumn.prefWidthProperty().bind(appointmentsTable.widthProperty().multiply(0.15));

        // columns for appointments table
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        appointmentDescriptionColumn
                .setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contact"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startLocal"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("endLocal"));
        appointmentCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        appointmentUserIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));

        appointmentTitleColumn.prefWidthProperty().bind(appointmentsTable.widthProperty().multiply(0.1));
        appointmentDescriptionColumn.prefWidthProperty().bind(appointmentsTable.widthProperty().multiply(0.15));
        appointmentStartColumn.prefWidthProperty().bind(appointmentsTable.widthProperty().multiply(0.15));
        appointmentEndColumn.prefWidthProperty().bind(appointmentsTable.widthProperty().multiply(0.15));

        refreshMonthsInChoiceBox();

    }

    /**
     * display the months in the choice box for the last 6 months and the
     * next 6 months including the year, so that the user can select a month filter
     * for the appointments table
     */
    private void refreshMonthsInChoiceBox() {

        // clear the choice box
        monthChoiceBox.getItems().clear();

        LocalDate scrolledDate = null;

        // find the scrolled date based on the offset of the monthScrollIndex
        if (monthScrollIndex < 0) {

            scrolledDate = LocalDate.now().minusMonths(Math.abs(monthScrollIndex));

        } else {

            scrolledDate = LocalDate.now().plusMonths(monthScrollIndex);

        }

        // add default value to month choice box
        monthChoiceBox.getItems().add("By Month");

        // select that value
        monthChoiceBox.getSelectionModel().selectFirst();

        // add a value that will indicate that the user wants to look at 6 more previous
        // months
        monthChoiceBox.getItems().add("<Previous 6 Months>");

        // loop through the last 6 months and add them to the choice box
        for (int i = 6; i > 0; i--) {
            LocalDate date = scrolledDate.minusMonths(i);
            monthChoiceBox.getItems().add(date.getYear() + " " + date.getMonth().toString());
        }

        // loop through the next 6 months and add them to the choice box
        for (int i = 0; i < 6; i++) {
            LocalDate date = scrolledDate.plusMonths(i);
            monthChoiceBox.getItems().add(date.getYear() + " " + date.getMonth().toString());
        }

        // add a value that will indicate that the user wants to look at 6 more future
        // months
        monthChoiceBox.getItems().add("<Next 6 Months>");

    }

    /**
     * Button click event handler for the customers button
     * This method will toggle the visibility of the customer section of the main
     * view
     */
    @FXML
    private void onCustomersButtonClick() {
        System.out.println("Customers button clicked");
        selectNavButton(customersButton);

        // refresh the customers table
        this.refreshCustomerContent();
    }

    /**
     * Button click event handler for the appointments button
     * This method will toggle the visibility of the appointment section of the main
     * view
     */
    @FXML
    private void onAppointmentsButtonClick() {
        System.out.println("Appointments button clicked");
        selectNavButton(appointmentsButton);

        // refresh the appointments table
        refreshAppointmentContent();

        // in the monthChoiceBox, select first item
        monthChoiceBox.getSelectionModel().selectFirst();
    }

    /**
     * Button click event handler for the reports button
     * This method will toggle the visibility of the reports section of the main
     * view
     */
    @FXML
    private void onReportsButtonClick() {
        System.out.println("Reports button clicked");
        selectNavButton(reportsButton);
    }

    /**
     * Button click event handler for the add customer button
     * this method will open the add customer form so that a new customer can be
     * added
     */
    @FXML
    private void onAddCustomerButtonClick() {
        System.out.println("Add customer button clicked");

        try {
            AppController appController = this.getApp().setShowScene("customer.fxml", "Customer Form");
            ((CustomerController) appController).setApp(this.getApp());
            // based on the countrys list from the app controller populate the combo box
            ((CustomerController) appController).populateCountryComboBox(this.getApp().getCountries());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    /**
     * Button click event handler for the modify customer button
     * this method will open the modify customer form so that an existing customer
     * can be modified
     */
    @FXML
    private void onModifyCustomerButtonClick() {
        System.out.println("Modify customer button clicked");

        // get the selected customer from the table
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        // if no customer is selected then return
        if (selectedCustomer == null) {
            alertWarning("Please select a customer to modify", "Modify Customer");

        } else {
            try {
                AppController appController = this.getApp().setShowScene("customer.fxml", "Customer Form");
                ((CustomerController) appController).setApp(this.getApp());

                // based on the countrys list from the app controller populate the combo box
                ((CustomerController) appController).populateCountryComboBox(this.getApp().getCountries());

                // populate the form with the selected customer
                ((CustomerController) appController).populateForm(selectedCustomer);

            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Button click event handler for the delete customer button
     * this method will prompt the user to confirm that they want to delete the
     * selected customer record from the database and then delete it
     */
    @FXML
    private void onDeleteCustomerButtonClick() {
        System.out.println("Delete customer button clicked");

        System.out.println("attempting to delete customer");

        // get the selected customer from the table
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        // request validation for delete
        FormValidationState formValidationState = requestCustomerDeletionValidation(selectedCustomer);

        if (formValidationState.isValid()) {
            System.out.println("validation passed");

            // prompt the user to confirm that they want to delete the customer
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
            alert.showAndWait();

            if (alert.getResult().getText().equals("Cancel")) {
                return;
            } else {
                // delete the customer from the database
                DatabaseManager dbmanager = new DatabaseManager();

                String query = "DELETE FROM customers WHERE Customer_ID = ? ;";

                // execute the query
                dbmanager.executeUpdate(query, (rs) -> {
                    try {
                        System.out.println("customer deleted");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                }, selectedCustomer.getCustomerId());

                dbmanager.disconnect();

                // refresh the customers table
                this.refreshCustomerContent();

            }

        } else {

            alertError(formValidationState.getMessage(), "Delete Customer");

            return;

        }

    }

    /**
     * This method handles the add appointment button click event
     * It opens up the form where an appointment can be scheduled and sets the
     * selected customer in the form
     */
    @FXML
    private void onAddAppointmentButtonClick() {
        System.out.println("Add appointment button clicked");

        try {

            // get the selected customer from the table
            Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

            // if not null then set the selected customer in the form
            if (selectedCustomer != null) {
                AppController appController = this.getApp().setShowScene("appointments.fxml", "Appointment Form");
                ((AppointmentsController) appController).setApp(this.getApp());

                ((AppointmentsController) appController).setSelectedCustomer(selectedCustomer);
            } else {
                alertWarning("Please select a customer to schedule an appointment with", "Add Appointment");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    /**
     * This method handles the modify appointment button click event
     * It opens up the form where an appointment can be modified
     */
    @FXML
    private void onModifyAppointmentButtonClick() {
        System.out.println("Modify appointment button clicked");

        try {

            // get the selected appointment from the table
            Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

            // if not null then set the selected appointment in the form
            if (selectedAppointment != null) {
                AppController appController = this.getApp().setShowScene("appointments.fxml", "Appointment Form");
                ((AppointmentsController) appController).setApp(this.getApp());

                ((AppointmentsController) appController).setSelectedAppointment(selectedAppointment);

                // find customer based on customer id of selected appointment
                Customer selectedCustomer = getApp().getCustomers().stream()
                        .filter(customer -> customer.getCustomerId() == selectedAppointment.getCustomerId())
                        .findFirst()
                        .orElse(null);

                // set the selected customer in the form
                ((AppointmentsController) appController).setSelectedCustomer(selectedCustomer);

                // populate form
                ((AppointmentsController) appController).populateForm(selectedAppointment);
            } else {
                alertWarning("Please select an appointment to modify", "Modify Appointment");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method handles the delete appointment button click event
     * It prompts the user to confirm that they want to delete the selected
     * appointment and then deletes it from the database
     */
    @FXML
    private void onDeleteAppointmentButtonClick() {
        System.out.println("Delete appointment button clicked");

        // get the selected appointment from the table
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        // ask the user to confirm that they want to delete the appointment
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
        alert.showAndWait();

        if (alert.getResult().getText().equals("Cancel")) {
            return;
        } else {
            // delete the appointment from the database
            DatabaseManager dbmanager = new DatabaseManager();

            String query = "DELETE FROM appointments WHERE Appointment_ID = ? ;";

            // execute the query
            dbmanager.executeUpdate(query, (rs) -> {
                try {
                    System.out.println("appointment deleted");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }, selectedAppointment.getAppointmentId());

            dbmanager.disconnect();

            // alert the user that the appointment was deleted, include the appointment id,
            // and appointment type that was deleted
            alertSuccess("Appointment ID: " + selectedAppointment.getAppointmentId() + "\n Type: "
                    + selectedAppointment.getType(), "Appointment Successfully Cancelled");

            // refresh the appointments table
            this.refreshAppointmentContent();

        }

    }

    /**
     * Handles the month choice box change event
     * This method will populate the week choice box based on the month selected and
     * also display the appointments for the selected month in the appointments
     * table
     */
    @FXML
    private void onMonthChoiceBoxChange() {
        System.out.println("Month choice box changed");

        // set the week choice box to the default value
        weekChoiceBox.getSelectionModel().selectFirst();

        // get value of the selected item
        String monthChoiceValue = (String) monthChoiceBox.getSelectionModel().getSelectedItem();

        // if the user selects the default value, then return
        if (monthChoiceValue.equals("By Month")) {
            return;
        }

        // if the user selects the go back 6 months value, then go back 6 months
        else if (monthChoiceValue.equals("<Previous 6 Months>")) {
            monthScrollIndex -= 6;
            refreshMonthsInChoiceBox();
        }

        // if the user selects the go forward 6 months value, then go forward 6 months
        else if (monthChoiceValue.equals("<Next 6 Months>")) {
            monthScrollIndex += 6;
            refreshMonthsInChoiceBox();
        } else {

            // refresh appointments from db
            getApp().getAppointmentsFromDB(monthChoiceValue, null);

            // displayed new appointments
            displayAppointmentsTable();

            // populate the week choice box with a list of all of the weeks in the selected
            // month.
            populateWeeksChoicBox(monthChoiceValue);

        }

    }

    /**
     * This method will populate the week choice box with a list of all of the weeks
     * that are within the month
     * The format of the text is set as "2023-06-19 - 2023-06-25", "2023-06-26 -
     * 2023-07-02", "2023-07-03 - 2023-07-09" so that the user can easily see the
     * date range. Not all weeks will have 7 days in them.
     * 
     * @param monthChoiceValue
     */
    private void populateWeeksChoicBox(String monthChoiceValue) {

        // add default value to week choice box
        weekChoiceBox.getItems().clear();
        weekChoiceBox.getItems().add("By Week");
        // select the default value
        weekChoiceBox.getSelectionModel().selectFirst();

        // Split the monthChoiceValue into year and month
        String[] parts = monthChoiceValue.split(" ");
        String year = parts[0];
        String month = parts[1]; // convert string month to number month

        // Get the numeric month value
        String numericMonth = monthMap.get(month);

        // Get the first day of the month as a local date based on the selected month
        LocalDate firstDayOfMonth = LocalDate.parse(year + "-" + numericMonth + "-01");

        System.out.println("First day of month: " + firstDayOfMonth);

        // Find out the day of the week for the first day
        DayOfWeek dayOfWeek = firstDayOfMonth.getDayOfWeek();
        int dayOfWeekValue = dayOfWeek.getValue(); // 1 (Monday) to 7 (Sunday)

        System.out.println("Day of the week: " + dayOfWeek);

        // Calculate the start and end dates for each week
        LocalDate startDate = firstDayOfMonth;
        while (startDate.getMonthValue() == Integer.parseInt(numericMonth)) {
            LocalDate endDate = startDate.plusDays(6 - dayOfWeekValue);

            int startDay = startDate.getDayOfMonth();
            int endDay = endDate.getDayOfMonth();

            String weekRange = startDay + " - " + endDay;
            System.out.println("Week range: " + weekRange);

            // Add week range to choice box
            weekChoiceBox.getItems().add(weekRange);

            startDate = endDate.plusDays(1);
            dayOfWeekValue = 0; // Reset day of the week value for the next week
        }

        System.out.println("");
    }

    /**
     * Handles the week choice box change event
     * This method will display the appointments for the selected week in the table
     */
    @FXML
    private void onWeekChoiceBoxChange() {
        System.out.println("Week choice box changed");
        // get value of the selected item of the week
        String weekChoiceValue = (String) weekChoiceBox.getSelectionModel().getSelectedItem();

        // if the user selects the default value, then return
        if (weekChoiceValue != null && weekChoiceValue.equals("By Week")) {
            return;
        } else {

            // get value of the selected item of the month
            String monthChoiceValue = (String) monthChoiceBox.getSelectionModel().getSelectedItem();

            getApp().getAppointmentsFromDB(monthChoiceValue, weekChoiceValue);

            // displayed new appointments
            displayAppointmentsTable();
        }

    }

    @FXML
    private void onGetReportButtonClick() {
        System.out.println("Get report button clicked");
    }

    @FXML
    private void onExitButtonClick() {
        System.out.println("Exit button clicked");
    }

    /**
     * This method styles the navbar buttons to show which one is selected
     * according to
     * the button passed in and displays or hides the appropriate vbox
     */
    private void selectNavButton(Button button) {

        // set the nav buttons to the default state
        for (Button btn : navButtons) {
            btn.getStyleClass().remove("selected");
        }

        // change the class name of the button
        button.getStyleClass().add("selected");

        // set the visibility of the vboxes
        for (VBox vbox : vboxes) {
            vbox.setVisible(false);
        }

        // set the visibility of the selected vbox
        if (button == customersButton) {
            customersVBox.setVisible(true);

        } else if (button == appointmentsButton) {
            appointmentsVBox.setVisible(true);

        } else if (button == reportsButton) {
            reportsVBox.setVisible(true);

        }
    }

    /*
     * this method will refresh the content of the main view when called
     */
    public void refreshCustomerContent() {
        System.out.println("refreshing content");

        this.getCountriesFromDB();
        this.getCustomersFromDB();
        this.displayCustomersInTable();

    }

    /**
     * This method will get the appointments from the database and populate the
     * appointments table
     */
    public void refreshAppointmentContent() {
        System.out.println("refreshing appointment content");

        this.getCountriesFromDB();
        getApp().getAppointmentsFromDB(null, null);
        this.displayAppointmentsTable();
    }

    /**
     * This method will get the countries from the database and populate the
     * countries in the countries array list in the app
     */
    private void getCountriesFromDB() {
        DatabaseManager dbmanager = new DatabaseManager();

        // get all divisions from countries from the database
        String query = "SELECT c.Country_ID, c.Country, Division_ID, Division FROM first_level_divisions dv INNER JOIN countries c on c.Country_ID = dv.Country_ID order by Country_ID, Division_ID;";
        dbmanager.executeQuery(query, (rs) -> {
            try {

                getApp().getCountries().clear();

                Country country = null;
                while (rs.next()) {
                    if (country == null || country.getCountryId() != rs.getInt("Country_ID")) {
                        // add the previous country to the list, if it exists
                        if (country != null) {
                            getApp().getCountries().add(country);
                        }
                        // create a new country object
                        country = new Country();
                        country.setCountryId(rs.getInt("Country_ID"));
                        country.setCountry(rs.getString("Country"));
                    }
                    // add the current division to the country
                    country.addAssociatedDivision(
                            new Division(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID")));
                }
                // add the last country to the list
                if (country != null) {
                    getApp().getCountries().add(country);
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            this.getApp().printDivisions();

        });

        dbmanager.disconnect();
    }

    /**
     * This method will get the customers from the database and populate the
     * customers in the customers array list in the main controller
     */
    private void getCustomersFromDB() {
        // clear the customers array list
        getApp().getCustomers().clear();

        DatabaseManager dbmanager = new DatabaseManager();

        // get customers from the database
        String query = "SELECT c.Customer_ID, Customer_Name, Address, Postal_Code, Phone, f.Division_ID, Division, cnt.Country_ID, Country FROM customers c INNER JOIN first_level_divisions f on f.Division_ID = c.Division_ID INNER JOIN countries cnt on cnt.Country_ID = f.Country_ID order by Customer_ID;";

        // execute the query
        dbmanager.executeQuery(query, (rs) -> {
            try {
                while (rs.next()) {
                    System.out.println(rs.getString("Customer_Name"));

                    // Customer(int customerId, String customer_Name, String address, String
                    // postalCode, String phone, int divisionId)

                    // create a new customer object
                    Customer customer = new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"),
                            rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"),
                            rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"),
                            rs.getString("Country"));

                    // add the customer to the customers array list
                    getApp().addCustomer(customer);

                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // display the customers on the table view

        });

        dbmanager.disconnect();
    }

    /**
     * This method will check the database to see if there are any appointments
     * associated with the customer.
     * If there are, the customer will not be deleted and an error message will be
     * displayed.
     * If there are no appointments associated with the customer, the customer will
     * be deleted from the database
     * 
     * @param customer
     * @return validationState
     */
    private FormValidationState requestCustomerDeletionValidation(Customer customer) {

        if (customer != null) {

            FormValidationState formValidationState;
            AtomicBoolean isValid = new AtomicBoolean(true);
            AtomicReference<String> errorMessage = new AtomicReference<>("");

            // make a query to the database to see if there are any appointments associated
            // with the customer
            DatabaseManager dbmanager = new DatabaseManager();

            // get customers from the database
            String query = "SELECT Customer_ID FROM appointments WHERE Customer_ID = ? LIMIT 1;";

            // execute the query
            dbmanager.executeQuery(query, (rs) -> {
                try {
                    if (rs.next()) {

                        // if there are appointments associated with the customer, set isValid to false
                        isValid.set(false);
                        errorMessage.set(
                                "Please delete all customer associated appointments ");

                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }, customer.getCustomerId());

            formValidationState = new FormValidationState(isValid.get(), errorMessage.get());

            return formValidationState;

        } else {
            return new FormValidationState(false, "Please select a customer to delete");
        }

    }

    /**
     * This method will display the customers in the customers table view based on
     * the customers array list in the app
     */
    private void displayCustomersInTable() {

        System.out.println("displaying customers table");

        if (getApp().getCustomers() != null) {

            // Add the customers to the table
            // customersTable.setItems(getApp().getCustomers());
            customersTable.setItems(getApp().getCustomers());
        } else {
            System.out.println("customers is null");
        }
    }

    /**
     * This method will display the appointments in the appointments table view
     * based on the appointments array list in the app
     */
    private void displayAppointmentsTable() {

        System.out.println("displaying appointments table");

        if (getApp().getAppointments() != null) {

            // loop and print appointments
            for (Appointment appointment : getApp().getAppointments()) {
                System.out.println(appointment.getTitle());
            }

            // Add the appointments to the table
            appointmentsTable.setItems(getApp().getAppointments());

        } else {
            System.out.println("appointments is null");
        }
    }

}
