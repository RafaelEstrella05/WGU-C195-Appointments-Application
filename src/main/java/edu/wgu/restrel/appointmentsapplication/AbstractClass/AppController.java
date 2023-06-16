package edu.wgu.restrel.appointmentsapplication.AbstractClass;

import edu.wgu.restrel.appointmentsapplication.AppointmentsApplication;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public abstract class AppController {
    protected AppointmentsApplication app;

    /**
     * Sets a reference to the app from any Form Controller so that other Form
     * Controllers can be referenced if necessary
     * 
     * @param app
     */
    public void setApp(AppointmentsApplication app) {
        this.app = app;
    }

    /**
     * Getter for app
     */
    public AppointmentsApplication getApp() {
        return this.app;
    }

    /**
     * Alert dialog for warning messages
     * 
     * @param message
     * @param type
     */
    public void alertWarning(String message, String type) {
        System.out.println(message);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(type);

        // Create a TextArea to display the error message
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        // Set the content of the Alert to the TextArea
        alert.getDialogPane().setContent(textArea);

        alert.showAndWait();
    }

    /**
     * Alert dialog for error messages
     * 
     * @param message
     * @param type
     */
    public void alertError(String message, String type) {
        System.out.println(message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(type);

        // Create a TextArea to display the error message
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        // Set the content of the Alert to the TextArea
        alert.getDialogPane().setContent(textArea);

        alert.showAndWait();
    }

    /**
     * Alert Dialog for success messages
     */
    public void alertSuccess(String message, String type) {
        System.out.println(message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(type);

        // Create a TextArea to display the error message
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        // Set the content of the Alert to the TextArea
        alert.getDialogPane().setContent(textArea);

        alert.showAndWait();
    }

}
