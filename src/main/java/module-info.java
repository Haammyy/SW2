module com.example.sw2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;


    opens Model to java.base;
    opens application to javafx.fxml;
    exports application;
    exports controllers;
    exports Model;
    opens controllers to javafx.fxml;
}