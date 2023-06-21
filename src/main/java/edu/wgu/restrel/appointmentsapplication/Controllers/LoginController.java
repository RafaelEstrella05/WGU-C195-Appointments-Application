package edu.wgu.restrel.appointmentsapplication.Controllers;

import edu.wgu.restrel.appointmentsapplication.AbstractClass.AppController;
import edu.wgu.restrel.appointmentsapplication.Utils.DatabaseManager;
import edu.wgu.restrel.appointmentsapplication.Utils.FileManager;
import edu.wgu.restrel.appointmentsapplication.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * LoginController class for the login.fxml view
 * User login and authentication is handled here
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class LoginController extends AppController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label signInLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Button submitButton;

    ResourceBundle resources;

    /**
     * login submission button click event handler.
     * Executes the login process by retrieving the username and password,
     * querying the database for matching records, and handling the results.
     *
     * USES LAMBDA EXPRESSION:
     * This lambda expression is used to provide a clear and concise way to process
     * the result set returned from the database query. It promotes code reuse when
     * querying for database records by allowing the developer to pass in a query
     * string, executor function, and MySQL query parameters (if any) without having
     * to declare database connections, prepared statements, and result sets for
     * each query.
     *
     * In this case, the lambda expression is used to query the database for the
     * user's
     * username and password, and checks if they are valid by verifying if the
     * result set
     * is empty or not.
     *
     * @see DatabaseManager#executeQuery(String, ResultSetHandler, Object...)
     */
    public void onSubmitButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        DatabaseManager dbmanager = new DatabaseManager();

        // run mysql query to search users table for user name and password
        dbmanager.executeQuery("SELECT * FROM client_schedule.users where User_Name = ? and Password = ?", (rs) -> {
            String activity;
            String date = java.time.LocalDate.now().toString();
            String time = java.time.LocalTime.now().toString();

            if (rs.next()) {
                // process each row of the result set
                System.out.println(rs.getString("User_Name"));

                activity = "Login Successful: by " + username + " date: " + date + " " + time;

                // set the user
                User user = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"));
                app.setUser(user);

                try {
                    AppController appController = this.getApp().setShowScene("main.fxml", "Appointment Manager");
                    ((MainController) appController).setApp(this.getApp());
                    app.setMainController((MainController) appController);

                    // display data in the table
                    ((MainController) appController).refreshCustomerContent();

                    // get appointments from db
                    getApp().getAppointmentsFromDB(null, null);

                    // check for upcoming appointments
                    ((MainController) appController).checkForUpcomingAppointments();

                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else {

                activity = "Login Failed: by " + username + " date: " + date + " " + time;

                // show an error message if the username and password combination is not found
                alertError(resources.getString("login_error_message"), resources.getString("login_error_title"));
            }

            // log the activity in a text file
            FileManager.writeToTextFile("login_activity.txt", activity);

        }, username, password);

        dbmanager.disconnect();
    }

    public void initialize() {

        // Get the user's default locale
        Locale locale = Locale.getDefault();

        // Load the appropriate resource bundle based on the locale
        resources = ResourceBundle.getBundle("login", locale);

        // set the text for the labels and button
        signInLabel.setText(resources.getString("sign_in_label"));
        userNameLabel.setText(resources.getString("username_label"));
        passwordLabel.setText(resources.getString("password_label"));
        submitButton.setText(resources.getString("submit_button"));

        // Get the user's default time zone
        ZoneId defaultTimeZone = ZoneId.systemDefault();

        // Display the user's time zone in the location label
        locationLabel.setText(resources.getString("location_label") + " " + defaultTimeZone.getId());

    }

}
