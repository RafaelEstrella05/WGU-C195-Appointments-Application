module edu.wgu.restrel.appointmentsapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens edu.wgu.restrel.appointmentsapplication to javafx.fxml;
    exports edu.wgu.restrel.appointmentsapplication;
    exports edu.wgu.restrel.appointmentsapplication.Models;
    opens edu.wgu.restrel.appointmentsapplication.Models to javafx.fxml;
    exports edu.wgu.restrel.appointmentsapplication.Controllers;
    opens edu.wgu.restrel.appointmentsapplication.Controllers to javafx.fxml;
    exports edu.wgu.restrel.appointmentsapplication.Utils;
    opens edu.wgu.restrel.appointmentsapplication.Utils to javafx.fxml;
    exports edu.wgu.restrel.appointmentsapplication.Interfaces;
    opens edu.wgu.restrel.appointmentsapplication.Interfaces to javafx.fxml;
}