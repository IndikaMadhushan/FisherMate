package com.fishermen.weatherapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import com.fishermen.weatherapp.util.SceneManager;
import com.fishermen.weatherapp.util.UserSession;
import com.fishermen.weatherapp.model.FishermenProfile;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> experienceCombo;
    @FXML private ComboBox<String> boatTypeCombo;
    @FXML private TextField boatLengthField;
    @FXML private TextField crewSizeField;
    @FXML private TextField licenseField;
    @FXML private TextField homePortField;
    @FXML private TextField emergencyContactField;
    @FXML private TextArea specializationArea;
    @FXML private Button saveButton;
    @FXML private Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ComboBoxes
        experienceCombo.setItems(FXCollections.observableArrayList(
                "0-2 years",
                "3-10 years",
                "11-20 years",
                "20+ years"
        ));

        boatTypeCombo.setItems(FXCollections.observableArrayList(
                "Small Boat (< 20ft)",
                "Medium Boat (20-40ft)",
                "Large Boat (> 40ft)",
                "Trawler"
        ));

        // Pre-fill name if available
        if (UserSession.getCurrentUserName() != null) {
            nameField.setText(UserSession.getCurrentUserName());
        }

        // Load existing profile if available
        FishermenProfile profile = UserSession.getCurrentProfile();
        if (profile != null) {
            loadProfileData(profile);
        }
    }

    @FXML
    private void handleSave() {
        if (!validateForm()) {
            return;
        }

        FishermenProfile profile = new FishermenProfile();
        profile.setName(nameField.getText().trim());
        profile.setExperience(experienceCombo.getValue());
        profile.setBoatType(boatTypeCombo.getValue());
        profile.setBoatLength(boatLengthField.getText().trim());
        profile.setCrewSize(crewSizeField.getText().trim());
        profile.setLicenseNumber(licenseField.getText().trim());
        profile.setHomePort(homePortField.getText().trim());
        profile.setEmergencyContact(emergencyContactField.getText().trim());
        profile.setSpecialization(specializationArea.getText().trim());

        UserSession.setCurrentProfile(profile);
        SceneManager.switchScene("/fxml/dashboard.fxml", "Fishermen Weather App - Dashboard");
    }

    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            showStatus("Please enter your name.", false);
            return false;
        }

        if (experienceCombo.getValue() == null) {
            showStatus("Please select your experience level.", false);
            return false;
        }

        if (boatTypeCombo.getValue() == null) {
            showStatus("Please select your boat type.", false);
            return false;
        }

        return true;
    }

    private void loadProfileData(FishermenProfile profile) {
        nameField.setText(profile.getName());
        experienceCombo.setValue(profile.getExperience());
        boatTypeCombo.setValue(profile.getBoatType());
        boatLengthField.setText(profile.getBoatLength());
        crewSizeField.setText(profile.getCrewSize());
        licenseField.setText(profile.getLicenseNumber());
        homePortField.setText(profile.getHomePort());
        emergencyContactField.setText(profile.getEmergencyContact());
        specializationArea.setText(profile.getSpecialization());
    }

    private void showStatus(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setVisible(true);
        statusLabel.getStyleClass().clear();
        statusLabel.getStyleClass().add(isSuccess ? "status-success" : "status-error");
    }
}
