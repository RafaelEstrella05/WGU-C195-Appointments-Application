package edu.wgu.restrel.appointmentsapplication.Controllers;

import edu.wgu.restrel.appointmentsapplication.AbstractClass.AppController;
import edu.wgu.restrel.appointmentsapplication.Models.*;
import edu.wgu.restrel.appointmentsapplication.Utils.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * This method is called by the FXMLLoader when initialization is complete
     * it initializes the navButtons and vboxes array and sets the default selected
     * button
     */
    @FXML
    private void initialize() {

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

        // columns for appointments table
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        appointmentDescriptionColumn
                .setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contact"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("end"));
        appointmentCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        appointmentUserIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));

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
            // alert the user that they need to select a customer
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer to modify");
            alert.showAndWait();

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

        // prompt the user to confirm that they want to delete the customer
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
        alert.showAndWait();

        if (alert.getResult().getText().equals("Cancel")) {
            return;
        } else {
            System.out.println("attempting to delete customer");

            // get the selected customer from the table
            Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

            // request validation for delete
            ValidationState validationState = requestCustomerDeletionValidation(selectedCustomer);

            if (validationState.isValid()) {
                System.out.println("validation passed");

                // delete the customer from the database
                DatabaseManager dbmanager = new DatabaseManager();

                String query = "DELETE FROM customers WHERE Customer_ID = ? ;";

            } else {

                // alert the user that they need to select a customer
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, validationState.getMessage());
                errorAlert.showAndWait();

                return;

            }

        }

    }

    /**
     *
     */
    @FXML
    private void onAddAppointmentButtonClick() {
        System.out.println("Add appointment button clicked");
    }

    @FXML
    private void onModifyAppointmentButtonClick() {
        System.out.println("Modify appointment button clicked");
    }

    @FXML
    private void onDeleteAppointmentButtonClick() {
        System.out.println("Delete appointment button clicked");
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
     * the button passed in.
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
        this.getAppointmentsFromDB();
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

    // getAppointmentsFromDB
    private void getAppointmentsFromDB() {
        // clear the appointments array list
        getApp().getAppointments().clear();

        DatabaseManager dbmanager = new DatabaseManager();

        // get appointments from the database
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, contacts.Contact_ID, Contact_Name FROM appointments appt INNER JOIN contacts contacts ON contacts.Contact_ID = appt.Contact_ID; ";

        // execute the query
        dbmanager.executeQuery(query, (rs) -> {
            try {
                while (rs.next()) {
                    System.out.println(rs.getString("Title"));

                    // create a new appointment object
                    Appointment appointment = new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"),
                            rs.getString("Description"), rs.getString("Location"), rs.getString("Type"),
                            rs.getString("Start"), rs.getString("End"), rs.getInt("Customer_ID"),
                            rs.getInt("User_ID"), rs.getInt("Contact_ID"), rs.getString("Contact_Name"));

                    // add the appointment to the appointments array list
                    getApp().addAppointment(appointment);

                    System.out.println("appointment added: " + appointment.getTitle());

                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // display the appointments on the table view

        });

        dbmanager.disconnect();
    }

    /**
     * This method will check the database to see if there are any appointments
     * associated with the customer.
     * If there are, the customer will not be deleted and an error message will be
     * displayed.
     * If there are no appointments associated with the customer, the customer will
     * be deleted from the database and data refreshed.
     * 
     * @param customer
     * @return validationState
     */
    private ValidationState requestCustomerDeletionValidation(Customer customer) {

        ValidationState validationState;
        AtomicBoolean isValid = new AtomicBoolean(true);
        AtomicReference<String> errorMessage = new AtomicReference<>("");

        // make a query to the database to see if there are any appointments associated
        // with the customer
        DatabaseManager dbmanager = new DatabaseManager();

        // get customers from the database
        String query = "SELECT top 1 Customer_ID FROM appointments WHERE Customer_ID = ? ;";

        // execute the query
        dbmanager.executeQuery(query, (rs) -> {
            try {
                while (rs.next()) {
                    System.out.println(rs.getString("Title"));

                    // if there are appointments associated with the customer, set isValid to false
                    isValid.set(false);
                    errorMessage.set(
                            "Cannot delete customer. There are appointments associated with this customer that need to be deleted first.");

                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        }, customer.getCustomerId());

        validationState = new ValidationState(isValid.get(), errorMessage.get());

        return validationState;

    }

    /**
     * This method will display the customers in the customers table view based on
     * the customers array list in the app
     */
    private void displayCustomersInTable() {

        System.out.println("displaying customers table");

        if (getApp().getCustomers() != null) {

            // loop and print customers
            for (Customer customer : getApp().getCustomers()) {
                System.out.println(customer.getCustomerName());
            }

            // Clear existing data from the table
            // customersTable.getItems().clear();

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

            // Clear existing data from the table
            // appointmentsTable.getItems().clear();

            // Add the appointments to the table
            appointmentsTable.setItems(getApp().getAppointments());
        } else {
            System.out.println("appointments is null");
        }
    }

}
