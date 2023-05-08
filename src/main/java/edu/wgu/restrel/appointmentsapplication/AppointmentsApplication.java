package edu.wgu.restrel.appointmentsapplication;

import edu.wgu.restrel.appointmentsapplication.Controllers.LoginController;
import edu.wgu.restrel.appointmentsapplication.Controllers.MainController;
import edu.wgu.restrel.appointmentsapplication.Interfaces.AppController;
import edu.wgu.restrel.appointmentsapplication.Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentsApplication extends Application {

    Stage stage;
    User user;
    LoginController loginController;
    MainController mainController;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        // Load the login scene
        loginController = (LoginController) setShowScene("login.fxml", "Appointment Manager");
        loginController.setApp(this);

        loginController.tempLoginPass(); // FIX ME: remove when done testing
    }

    public AppController setShowScene(String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppointmentsApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

        return fxmlLoader.getController();
    }

    // set main controller
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // get main controller
    public MainController getMainController() {
        return mainController;
    }

    // set user
    public void setUser(User user) {
        this.user = user;
    }

    // get user
    public User getUser() {
        return user;
    }

    public static void main(String[] args) {
        launch();
    }
}