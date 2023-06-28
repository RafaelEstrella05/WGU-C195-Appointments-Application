package edu.wgu.restrel.appointmentsapplication;

import edu.wgu.restrel.appointmentsapplication.Controllers.LoginController;
import edu.wgu.restrel.appointmentsapplication.Controllers.MainController;
import edu.wgu.restrel.appointmentsapplication.AbstractClass.AppController;
import edu.wgu.restrel.appointmentsapplication.Models.Appointment;
import edu.wgu.restrel.appointmentsapplication.Models.Country;
import edu.wgu.restrel.appointmentsapplication.Models.Customer;
import edu.wgu.restrel.appointmentsapplication.Models.Division;
import edu.wgu.restrel.appointmentsapplication.Models.User;
import edu.wgu.restrel.appointmentsapplication.Utils.DatabaseManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX Application for managing appointments
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class AppointmentsApplication extends Application {

    private Stage stage;
    private User user;
    private LoginController loginController;
    private MainController mainController;

    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Country> countries = new ArrayList<Country>();
    private ObservableList<Customer> customers;
    private ObservableList<Appointment> appointments;

    /**
     * Start the application
     * 
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        customers = FXCollections.observableArrayList();
        appointments = FXCollections.observableArrayList();

        // get the users from the database
        getUsersFromDB();

        // Load the login scene
        loginController = (LoginController) setShowScene("login.fxml", "Appointment Manager");
        loginController.setApp(this);

    }

    /**
     * Set the scene to the fxml file and title
     * 
     * @param fxml  the fxml file to load
     * @param title the title of the scene
     * @return AppController
     * @throws IOException
     */
    public AppController setShowScene(String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppointmentsApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

        return fxmlLoader.getController();
    }

    /**
     * Set the main controller
     * 
     * @param mainController the main controller for the application
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Get the main controller
     * 
     * @return MainController
     */
    public MainController getMainController() {
        return mainController;
    }

    /**
     * Set the user
     * 
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the user
     * 
     * @return user User
     */
    public User getUser() {
        return user;
    }

    /**
     * This method looks for a user in the users list based on the ID,
     * returns null if not found
     * 
     * @return user User from appointments list
     */
    public User findUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);

    }

    /**
     * Set the countries
     * 
     * @param countries the countries to set
     */
    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    /**
     * Get the countries
     * 
     * @return countries
     */
    public ArrayList<Country> getCountries() {
        return this.countries;
    }

    /**
     * Set the customers
     * 
     * @param customers the customers to set
     */
    public void setCustomers(ObservableList<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Get the customers
     * 
     * @return customers
     */
    public ObservableList<Customer> getCustomers() {
        return this.customers;
    }

    /**
     * add customer to customers
     * 
     * @param customer the customer to add
     */
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    /**
     * Set the appointments
     * 
     * @param appointments the appointments to set as an ObservableList
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * Get the appointments
     * 
     * @return appointments
     */
    public ObservableList<Appointment> getAppointments() {
        return this.appointments;
    }

    /**
     * Add appointment to appointments
     * 
     * @param appointment the appointment to add
     */
    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    /**
     * Find a division by name
     * 
     * @param name the name of the division
     * @return Division the division found or null
     */
    public Division findDivisionByName(String name) {
        return countries.stream()
                .flatMap(country -> country.getAssociatedDivisions().stream())
                .filter(division -> division.getDivision().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find a country by Name
     * 
     * @param name the name of the country
     * @return Country the country found or null
     */
    public Country findCountryByName(String name) {
        return getCountries().stream()
                .filter(country -> country.getCountry().equals(name))
                .findFirst()
                .orElse(null);
    }

    /*
     * Find a customer by id
     * 
     * @return Customer the customer found or null
     */
    public Customer findCustomerById(int id) {
        return getCustomers().stream()
                .filter(customer -> customer.getCustomerId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Get the appointments from the database based on the date string and or day
     * 
     * @param dateString the date string in format yyyy MMMM
     * @param dayString  the day string in format dd-dd
     */
    public void getAppointmentsFromDB(String dateString, String dayString) {
        // clear the appointments array list
        getAppointments().clear();

        // if date string is not specified, get all appointments
        if (dateString == null && dayString == null) {

            DatabaseManager dbmanager = new DatabaseManager();

            // get appointments from the database
            String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, contacts.Contact_ID, Contact_Name FROM appointments appt INNER JOIN contacts contacts ON contacts.Contact_ID = appt.Contact_ID order by start desc LIMIT 50; ";

            // execute the query
            /*
            
             */
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
                        addAppointment(appointment);

                        System.out.println("appointment added: " + appointment.getTitle());

                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            });

            dbmanager.disconnect();
        } else if (dateString != null && dayString == null) { // if date string is specified, get appointments for that
                                                              // date

            DatabaseManager dbmanager = new DatabaseManager();

            // get appointments from the database
            String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, contacts.Contact_ID, Contact_Name FROM appointments appt INNER JOIN contacts contacts ON contacts.Contact_ID = appt.Contact_ID WHERE DATE_FORMAT(start, '%Y %M') = ? order by start desc;";

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
                        addAppointment(appointment);

                        System.out.println("appointment added: " + appointment.getTitle());

                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }, dateString);

            dbmanager.disconnect();

        } else {
            DatabaseManager dbmanager = new DatabaseManager();

            // get appointments from the database
            String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, contacts.Contact_ID, Contact_Name "
                    +
                    "FROM appointments appt " +
                    "INNER JOIN contacts contacts ON contacts.Contact_ID = appt.Contact_ID " +
                    "WHERE YEAR(Start) = ? AND MONTHNAME(Start) = ? AND DAYOFMONTH(Start) >= ? AND DAYOFMONTH(End) <= ? order by start desc;";

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
                        addAppointment(appointment);

                        System.out.println("appointment added: " + appointment.getTitle());

                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }, dateString.split(" ")[0], dateString.split(" ")[1], dayString.split("-")[0],
                    dayString.split("-")[1]);

            dbmanager.disconnect();
        }

    }

    private void getUsersFromDB() {
        DatabaseManager dbmanager = new DatabaseManager();

        // get appointments from the database
        String query = "SELECT * FROM users;";

        // execute the query
        dbmanager.executeQuery(query, (rs) -> {
            try {
                while (rs.next()) {
                    System.out.println(rs.getString("User_Name"));

                    // create a new user object
                    User user = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"));

                    // add the user to the users array list
                    users.add(user);

                    System.out.println("user added: " + user.getUserName());

                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        });

        dbmanager.disconnect();
    }

    /**
     * print the division information, country and divisions
     */
    public void printDivisions() {
        System.out.println("Countries: " + getCountries().size());
        for (int i = 0; i < getCountries().size(); i++) {
            System.out.println("Country: " + getCountries().get(i).getCountry() + " Divisions: ");

            for (int k = 0; k < getCountries().get(i).getAssociatedDivisions().size(); k++) {
                System.out.print(
                        ", " + getCountries().get(i).getAssociatedDivisions().get(k).getDivision());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}