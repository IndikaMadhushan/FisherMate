package com.fishermen.weatherapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.fishermen.weatherapp.util.SceneManager;
import com.fishermen.weatherapp.util.UserSession;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public BorderPane borderPane;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink signUpLink;
    @FXML private Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize any default values or listeners
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showStatus("Please fill in all fields.", false);
            return;
        }

        if (!isValidEmail(email)) {
            showStatus("Please enter a valid email address.", false);
            return;
        }

        // Simulate login validation (in real app, this would be proper authentication)
        if (password.length() >= 6) {
            UserSession.setCurrentUser(email, extractNameFromEmail(email));
            SceneManager.switchScene("dashboard.fxml", "Fishermen Weather App - Dashboard");
        } else {
            showStatus("Invalid credentials. Please try again.", false);
        }
    }

    @FXML
    private void handleSignUp() throws IOException {
       SceneManager.switchScene("/fxml/signup.fxml", "Fishermen Weather App - Sign Up");
//
    }

    private void showStatus(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setVisible(true);
        statusLabel.getStyleClass().clear();
        statusLabel.getStyleClass().add(isSuccess ? "status-success" : "status-error");
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private String extractNameFromEmail(String email) {
        return email.substring(0, email.indexOf("@")).replace(".", " ");
    }

}


