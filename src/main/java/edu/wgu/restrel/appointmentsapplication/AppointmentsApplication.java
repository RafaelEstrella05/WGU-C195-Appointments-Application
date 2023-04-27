package edu.wgu.restrel.appointmentsapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentsApplication extends Application {

    Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        setShowScene("login.fxml", "Appointment Manager");
    }

    public void setShowScene(String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppointmentsApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}