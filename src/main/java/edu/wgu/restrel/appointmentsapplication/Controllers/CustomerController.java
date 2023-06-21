package edu.wgu.restrel.appointmentsapplication.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.wgu.restrel.appointmentsapplication.Models.*;
import edu.wgu.restrel.appointmentsapplication.AbstractClass.AppController;
import edu.wgu.restrel.appointmentsapplication.Utils.DatabaseManager;
import edu.wgu.restrel.appointmentsapplication.interfaces.FormValidation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * This controller handles the customer view and actions for the application
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class CustomerController extends AppController implements FormValidation {

    private Customer selectedCustomer;

    @FXML
    private Label formStateLabel;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private ChoiceBox countryChoiceBox;

    @FXML
    private ChoiceBox divisionChoiceBox;

    // initialize method
    public void initialize() {
        System.out.println("CustomerController initialized");

    }

    /**
     * Submit button click event handler: handles both add and update
     */
    public void onSubmitButtonClick() {
        System.out.println("Submit button clicked");

        try {
            FormValidationState formValidationState = getFormInputValidationState();

            if (selectedCustomer == null) { // if selectedCustomer is null, we are adding a new customer
                if (formValidationState.isValid()) {
                    // collect all data from fields and create a new customer object
                    Customer submittedCustomer = new Customer();
                    submittedCustomer.setCustomerName(customerNameField.getText());
                    submittedCustomer.setAddress(addressField.getText());
                    submittedCustomer.setPostalCode(postalCodeField.getText());
                    submittedCustomer.setPhone(phoneNumberField.getText());

                    // get the selected division
                    String selectedDivisionName = divisionChoiceBox.getSelectionModel().getSelectedItem().toString();
                    Division selectedDivision = getApp().findDivisionByName(selectedDivisionName);
                    submittedCustomer.setDivisionId(selectedDivision.getDivisionId());

                    // add the customer to the database
                    addCustomerToDatabase(submittedCustomer);
                }
            } else { // if selectedCustomer is not null, we are updating an existing customer
                if (formValidationState.isValid()) {
                    // collect all data from fields and create a new customer object with existing
                    // customer id
                    Customer submittedCustomer = new Customer();
                    submittedCustomer.setCustomerId(selectedCustomer.getCustomerId());
                    submittedCustomer.setCustomerName(customerNameField.getText());
                    submittedCustomer.setAddress(addressField.getText());
                    submittedCustomer.setPostalCode(postalCodeField.getText());
                    submittedCustomer.setPhone(phoneNumberField.getText());

                    // get the selected division
                    String selectedDivisionName = divisionChoiceBox.getSelectionModel().getSelectedItem().toString();
                    Division selectedDivision = getApp().findDivisionByName(selectedDivisionName);
                    submittedCustomer.setDivisionId(selectedDivision.getDivisionId());

                    // update the customer in the database
                    updateCustomerInDatabase(submittedCustomer);
                }
            }

            // go back to main controller and refresh the main view
            AppController appController = this.getApp().setShowScene("main.fxml", "Appointments Manager");
            ((MainController) appController).setApp(this.getApp());
            ((MainController) appController).refreshCustomerContent();
        } catch (FormValidationException e) {
            // handle the validation exception
            System.out.println("Validation error: " + e.getMessage());
            alertWarning(e.getMessage(), "Validation Error");

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * This method is in charge of checking the form input for errors or missing
     * data and returning a ValidationState object with the results
     * 
     * @return validationState
     */

    public FormValidationState getFormInputValidationState() throws FormValidationException {

        FormValidationState formValidationState;

        boolean isValid = true;
        String errorMessage = "";

        if (customerNameField.getText().isEmpty()) {
            isValid = false;
            errorMessage += "Customer name is required.\n";
        }

        if (addressField.getText().isEmpty()) {
            isValid = false;
            errorMessage += "Address is required.\n";
        }

        if (postalCodeField.getText().isEmpty()) {
            isValid = false;
            errorMessage += "Postal code is required.\n";
        }

        if (phoneNumberField.getText().isEmpty()) {
            isValid = false;
            errorMessage += "Phone number is required.\n";
        }

        if (divisionChoiceBox.getSelectionModel().isEmpty()) {
            isValid = false;
            errorMessage += "Division and Country are required.\n";
        }

        // if phone number

        if (!isValid) {
            throw new FormValidationException(errorMessage);
        } else {
            formValidationState = new FormValidationState(isValid, errorMessage);

            return formValidationState;
        }

    }

    /**
     * Handler for the country selection drop down change event
     * when the country selection changes, the division selection list should change
     * to reflect the divisions in the selected country
     */
    @FXML
    private void countryChoiceBoxChanged() {
        System.out.println("Country choice box changed");

        try {
            // get the selected country
            String selectedCountryName = countryChoiceBox.getSelectionModel().getSelectedItem().toString();

            System.out.println("Selected country: " + selectedCountryName);

            Country selectedCountry = getApp().findCountryByName(selectedCountryName);

            // populate the division choice box
            populateDivisionChoiceBox(selectedCountry.getAssociatedDivisions());

        } catch (ClassCastException e) {
            System.out.println("ClassCastException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }

    /**
     * Handler for the division selection drop down change event
     */
    @FXML
    private void divisionChoiceBoxChanged() {

        System.out.println("Division choice box changed");

    }

    /**
     * Cancel button click event handler
     */
    public void onCancelButtonClick() {
        System.out.println("Cancel button clicked");

        try {
            AppController appController = this.getApp().setShowScene("main.fxml", "Appointments Manager");
            ((MainController) appController).setApp(this.getApp());
            ((MainController) appController).refreshCustomerContent();

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

    }

    /**
     * Setter for selected customer
     * 
     * @param customer
     */
    public void setSelectedCustomer(Customer customer) {

        this.selectedCustomer = customer;
    }

    /**
     * Getter for selected customer
     * 
     * @return selectedCustomer
     */
    public Customer getSelectedCustomer() {
        return this.selectedCustomer;
    }

    /**
     * populates the fields with the selected customer data
     * 
     * @throws Exception
     */
    public void populateForm(Customer customer) throws Exception {

        // change label to indicate we are editing an existing customer
        formStateLabel.setText("Modify Customer");

        setSelectedCustomer(customer);

        if (customer != null) {
            customerIdField.setText(Integer.toString(selectedCustomer.getCustomerId()));
            customerNameField.setText(selectedCustomer.getCustomerName());
            addressField.setText(selectedCustomer.getAddress());
            postalCodeField.setText(selectedCustomer.getPostalCode());
            phoneNumberField.setText(selectedCustomer.getPhone());

            // select the country in the country choice box that matches the selected
            // customer's country
            countryChoiceBox.getSelectionModel().select(selectedCustomer.getCountry());

            // populate the division choice box that matches the selected customer's
            // division
            divisionChoiceBox.getSelectionModel().select(selectedCustomer.getDivision());

        } else {
            throw new Exception("selectedCustomer is null: cannot populate fields");
        }
    }

    /**
     * This method populates the country choice box to its default
     * state.
     * 
     * @param countries
     */
    public void populateCountryComboBox(ArrayList<Country> countries) {
        List<String> countryNames = countries.stream()
                .map(Country::getCountry)
                .collect(Collectors.toList());

        countryChoiceBox.getItems().clear();
        countryChoiceBox.getItems().addAll(countryNames);
        divisionChoiceBox.setDisable(true);
    }

    /**
     * This method populates the division choice box with the divisions associated
     * with the selected country
     * 
     * @param divisions
     */
    private void populateDivisionChoiceBox(ArrayList<Division> divisions) {

        // enable the division choice box
        divisionChoiceBox.setDisable(false);

        // clear the division choice box
        divisionChoiceBox.getItems().clear();

        // iterate through the divisions
        for (Division division : divisions) {
            // add the division to the division choice box
            divisionChoiceBox.getItems().add(division.getDivision());

        }

    }

    /**
     * Adds a customer to the database.
     *
     * @param customer The customer object to be added.
     *
     *                 USES LAMBDA EXPRESSION:
     *                 This lambda expression is used to provide a clear and concise
     *                 way to process
     *                 an UPDATE or INSERT using a MySQL statement. It promotes code
     *                 reuse when
     *                 updating or inserting database records by allowing the
     *                 developer to pass in
     *                 a query statement string, executor function, and MySQL query
     *                 parameters (if any)
     *                 without having to declare database connections, prepared
     *                 statements, and result
     *                 sets for each query.
     *
     *                 In this case, the lambda expression is used to insert a new
     *                 customer into the database
     *                 and handle when the query is complete and returns a result
     *                 set.
     *
     * @see DatabaseManager#executeUpdate(String, ResultSetHandler, Object...)
     */
    private void addCustomerToDatabase(Customer customer) {
        System.out.println("addCustomerToDatabase() called");

        // create a database manager
        DatabaseManager dbmanager = new DatabaseManager();

        // run the insert query
        /*
         * USES LAMBDA EXPRESSION:
         * This lambda expression is used to provide a clear and concise way to process
         * an UPDATE or INSERT using a mysql statement.
         * It promotes code reuse when
         * updateing or inserting database records by allowing the developer to pass in
         * a query statement
         * string, executor function, and mysql query parameters (if any)
         * without having to declare db connections, prepared statements, and result
         * sets for each query.
         * 
         * In this case, it is used to insert a new customer into the database and
         * handle when the query is complete and returns a result set.
         */
        dbmanager.executeUpdate(
                "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)",
                (rs) -> {
                    System.out.println("Insert query executed");

                    // return to the main view
                    try {
                        AppController appController = this.getApp().setShowScene("main.fxml", "Appointments Manager");
                        ((MainController) appController).setApp(this.getApp());

                    } catch (IOException e) {
                        System.out.println("IOException: " + e.getMessage());
                    }

                }, customer.getCustomerName(), customer.getAddress(), customer.getPostalCode(), customer.getPhone(),
                app.getUser().getUserName(), app.getUser().getUserName(), customer.getDivisionId());

        // close the database connection
        dbmanager.disconnect();

    }

    /**
     * Updates a customer in the database
     * 
     * @param customer
     */
    // updateCustomerInDatabase
    private void updateCustomerInDatabase(Customer customer) {
        System.out.println("updateCustomerInDatabase() called");

        // create a database manager
        DatabaseManager dbmanager = new DatabaseManager();

        // run the update query
        dbmanager.executeUpdate(
                "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?",
                (rs) -> {
                    System.out.println("Update query executed");

                    // return to the main view
                    try {
                        AppController appController = this.getApp().setShowScene("main.fxml", "Appointments Manager");
                        ((MainController) appController).setApp(this.getApp());

                    } catch (IOException e) {
                        System.out.println("IOException: " + e.getMessage());
                    }

                }, customer.getCustomerName(), customer.getAddress(), customer.getPostalCode(), customer.getPhone(),
                app.getUser().getUserName(), customer.getDivisionId(), customer.getCustomerId());

        // close the database connection
        dbmanager.disconnect();

    }

}
