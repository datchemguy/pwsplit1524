module org.example.client {
    requires commons;

    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires spring.web;
    requires com.fasterxml.jackson.databind;

    opens client to javafx.fxml;
    exports client;
}