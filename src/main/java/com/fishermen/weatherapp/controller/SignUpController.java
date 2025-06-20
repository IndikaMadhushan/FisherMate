package com.fishermen.weatherapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.fishermen.weatherapp.util.SceneManager;
import com.fishermen.weatherapp.util.UserSession;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button signUpButton;
    @FXML private Hyperlink loginLink;
    @FXML private Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize any default values or listeners
    }

    @FXML
    private void handleSignUp() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showStatus("Please fill in all fields.", false);
            return;
        }

        if (!isValidEmail(email)) {
            showStatus("Please enter a valid email address.", false);
            return;
        }

        if (password.length() < 6) {
            showStatus("Password must be at least 6 characters long.", false);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showStatus("Passwords do not match!", false);
            return;
        }

        // Create user session
        UserSession.setCurrentUser(email, name);
        SceneManager.switchScene("/fxml/profile.fxml", "Fishermen Weather App - Profile Setup");
    }

    @FXML
    private void handleLogin() {
        SceneManager.switchScene("/fxml/login.fxml", "Fishermen Weather App - Login");
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
}
