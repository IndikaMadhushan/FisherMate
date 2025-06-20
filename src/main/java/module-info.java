module fishermen.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires org.apache.httpcomponents.httpclient;

    exports com.fishermen.weatherapp;
    exports com.fishermen.weatherapp.controller;
    exports com.fishermen.weatherapp.model;
    exports com.fishermen.weatherapp.service;
    exports com.fishermen.weatherapp.util;
    opens com.fishermen.weatherapp to javafx.fxml;
    opens com.fishermen.weatherapp.controller to javafx.fxml;
    opens com.fishermen.weatherapp.model to javafx.fxml;
    opens com.fishermen.weatherapp.util to javafx.fxml;
    opens com.fishermen.weatherapp.service to javafx.fxml;
}
