package edu.wgu.restrel.appointmentsapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController extends AppController{

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

        // search user table for user name and password
        dbmanager.runQuery("SELECT * FROM client_schedule.users where User_Name = ? and Password = ?", (rs) -> {
            String activity;
            String date = java.time.LocalDate.now().toString();
            String time = java.time.LocalTime.now().toString();

            if (rs.next()) {
                // process each row of the result set
                System.out.println(rs.getString("User_Name"));

                activity = "Login Successful: by " + username + " date: " + date + " " + time;



            } else {

                activity = "Login Failed: by " + username + " date: " + date + " " + time;

                // show an error message if the username and password combination is not found
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect username or password.");
                alert.showAndWait();
            }

            // log the activity in a text file
            TextFileManager.writeToFile("login_activity.txt", activity);

        }, username, password);

        dbmanager.disconnect();
    }

}