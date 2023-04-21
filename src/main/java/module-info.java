module edu.wgu.restrel.appointmentsapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens edu.wgu.restrel.appointmentsapplication to javafx.fxml;
    exports edu.wgu.restrel.appointmentsapplication;
}