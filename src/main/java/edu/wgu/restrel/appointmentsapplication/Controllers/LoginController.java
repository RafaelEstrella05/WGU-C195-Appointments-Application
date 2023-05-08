package edu.wgu.restrel.appointmentsapplication.Controllers;

import edu.wgu.restrel.appointmentsapplication.Interfaces.AppController;
import edu.wgu.restrel.appointmentsapplication.Utils.DatabaseManager;
import edu.wgu.restrel.appointmentsapplication.Utils.FileManager;
import edu.wgu.restrel.appointmentsapplication.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends AppController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    /**
     * login submission button click event handler
     */
    public void onSubmitButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        DatabaseManager dbmanager = new DatabaseManager();

        // run mysql query to search users table for user name and password
        dbmanager.runQuery("SELECT * FROM client_schedule.users where User_Name = ? and Password = ?", (rs) -> {
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
                    AppController appController = app.setShowScene("main.fxml", "Appointment Manager");
                    app.setMainController((MainController) appController);

                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else {

                activity = "Login Failed: by " + username + " date: " + date + " " + time;

                // show an error message if the username and password combination is not found
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect username or password.");
                alert.showAndWait();
            }

            // log the activity in a text file
            FileManager.writeToTextFile("login_activity.txt", activity);

        }, username, password);

        dbmanager.disconnect();
    }

    /**
     * temporary login credentials for testing, remove when done
     */
    public void tempLoginPass() {
        usernameField.setText("Admin");
        passwordField.setText("Admin");

        onSubmitButtonClick();
    }

}