package edu.wgu.restrel.appointmentsapplication;

import edu.wgu.restrel.appointmentsapplication.Controllers.LoginController;
import edu.wgu.restrel.appointmentsapplication.Controllers.MainController;
import edu.wgu.restrel.appointmentsapplication.Interfaces.AppController;
import edu.wgu.restrel.appointmentsapplication.Models.Country;
import edu.wgu.restrel.appointmentsapplication.Models.Customer;
import edu.wgu.restrel.appointmentsapplication.Models.Division;
import edu.wgu.restrel.appointmentsapplication.Models.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AppointmentsApplication extends Application {

    private Stage stage;
    private User user;
    private LoginController loginController;
    private MainController mainController;

    private ArrayList<Country> countries = new ArrayList<Country>();
    private ObservableList<Customer> customers;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        customers = FXCollections.observableArrayList();

        // Load the login scene
        loginController = (LoginController) setShowScene("login.fxml", "Appointment Manager");
        loginController.setApp(this);

        loginController.tempLoginPass(); // FIX ME: remove when done testing
    }

    /**
     * Set the scene to the fxml file and title
     * 
     * @param fxml
     * @param title
     * @return
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
     * @param mainController
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
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    // get user
    public User getUser() {
        return user;
    }

    /**
     * Set the countries
     * 
     * @param countries
     */
    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    /**
     * Get the countries
     * 
     * @return ArrayList<Country>
     */
    public ArrayList<Country> getCountries() {
        return this.countries;
    }

    /**
     * Set the customers
     * 
     * @param customers
     */
    public void setCustomers(ObservableList<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Get the customers
     * 
     * @return ArrayList<Customer>
     */
    public ObservableList<Customer> getCustomers() {
        return this.customers;
    }

    /**
     * add customer to customers
     * 
     * @param customer
     */
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    /**
     * Find a division by id
     * 
     * @param id
     * @return Division
     */
    public Division findDivisionById(int id) {
        for (Country country : countries) {
            // use country.findDivisionById(id) to find division
            Division division = country.findDivisionById(id);
            if (division != null) {
                return division;
            }
        }
        return null;
    }

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