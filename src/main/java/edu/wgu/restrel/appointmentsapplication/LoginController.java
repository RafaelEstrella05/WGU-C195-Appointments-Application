package edu.wgu.restrel.appointmentsapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    public void onSubmitButtonClick(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        DatabaseManager dbmanager = new DatabaseManager();

        // search user table for user name and password
        dbmanager.runQuery("SELECT * FROM client_schedule.users where User_Name = ? and Password = ?", (rs) -> {
            if (rs.next()) {
                // process each row of the result set
                System.out.println(rs.getString("User_Name"));

            } else {
                // show an error message if the username and password combination is not found
                System.out.println("Incorrect username or password.");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect username or password.");
                alert.showAndWait();
            }
        }, username, password);

        dbmanager.disconnect();
    }

}