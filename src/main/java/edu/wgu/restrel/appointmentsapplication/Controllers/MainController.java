package edu.wgu.restrel.appointmentsapplication.Controllers;

import edu.wgu.restrel.appointmentsapplication.Interfaces.AppController;
import edu.wgu.restrel.appointmentsapplication.Models.Country;
import edu.wgu.restrel.appointmentsapplication.Models.Customer;
import edu.wgu.restrel.appointmentsapplication.Models.Division;
import edu.wgu.restrel.appointmentsapplication.Utils.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MainController extends AppController {

    /* Customers TableView Components */

    /*
     * <TableView fx:id="customerTable" prefHeight="200.0" prefWidth="200.0">
     * <columns>
     * <!--customer name, address, postal code, phone number, division, country-->
     * <TableColumn fx:id="customerIdColumn" text="Customer ID"/>
     * <TableColumn fx:id="customerNameColumn" text="Customer Name"/>
     * <TableColumn fx:id="addressColumn" text="Address"/>
     * <TableColumn fx:id="postalCodeColumn" text="Postal Code"/>
     * <TableColumn fx:id="phoneNumberColumn" text="Phone Number"/>
     * <TableColumn fx:id="divisionColumn" text="Division"/>
     * <TableColumn fx:id="countryColumn" text="Country"/>
     * </columns>
     * </TableView>
     */

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

    /* attributes */
    // private ArrayList<Customer> customers;
    // observable list of customers

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

        // table columns
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionId"));

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
    }

    /**
     * Button click event handler for the modify customer button
     * this method will open the modify customer form so that an existing customer
     * can be modified
     */
    @FXML
    private void onModifyCustomerButtonClick() {
        System.out.println("Modify customer button clicked");
    }

    /**
     * Button click event handler for the delete customer button
     * this method will prompt the user to confirm that they want to delete the
     * selected customer record from the database and then delete it
     */
    @FXML
    private void onDeleteCustomerButtonClick() {
        System.out.println("Delete customer button clicked");
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

    /**
     * This method will get the countries from the database and populate the
     * countries in the countries array list in the app
     */
    public void getCountriesFromDB() {
        DatabaseManager dbmanager = new DatabaseManager();

        // get all divisions from countries from the database
        String query = "SELECT c.Country_ID, c.Country, Division_ID, Division FROM first_level_divisions dv INNER JOIN countries c on c.Country_ID = dv.Country_ID order by Country_ID, Division_ID;";
        dbmanager.runQuery(query, (rs) -> {
            try {
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
    public void getCustomersFromDB() {
        // clear the customers array list
        getApp().getCustomers().clear();

        DatabaseManager dbmanager = new DatabaseManager();

        // get customers from the database
        String query = "SELECT c.Customer_ID, Customer_Name, Address, Postal_Code, Phone, f.Division_ID, Division, cnt.Country_ID, Country FROM customers c INNER JOIN first_level_divisions f on f.Division_ID = c.Division_ID INNER JOIN countries cnt on cnt.Country_ID = f.Country_ID;";

        dbmanager.runQuery(query, (rs) -> {
            try {
                while (rs.next()) {
                    System.out.println(rs.getString("Customer_Name"));

                    // Customer(int customerId, String customer_Name, String address, String
                    // postalCode, String phone, int divisionId)

                    // create a new customer object
                    Customer customer = new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"),
                            rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"),
                            rs.getInt("Division_ID"));

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
     * This method will display the customers in the customers table view based on
     * the customers array list in the main controller
     */
    public void displayCustomersInTable() {

        System.out.println("displaying customers table");

        if (getApp().getCustomers() != null) {

            // loop and print customers
            for (Customer customer : getApp().getCustomers()) {
                System.out.println(customer.getCustomerName());
            }

            // Clear existing data from the table
            customersTable.getItems().clear();

            // Add the customers to the table
            // customersTable.setItems(getApp().getCustomers());
            customersTable.setItems(getApp().getCustomers());
        } else {
            System.out.println("customers is null");
        }
    }

}
