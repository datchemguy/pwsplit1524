module org.example.client {
    requires commons;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires spring.web;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens client to javafx.fxml;
    exports client;
}