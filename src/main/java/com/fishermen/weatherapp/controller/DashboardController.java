package com.fishermen.weatherapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.concurrent.Task;
import javafx.collections.FXCollections;
import javafx.application.Platform;
import com.fishermen.weatherapp.util.SceneManager;
import com.fishermen.weatherapp.util.UserSession;
import com.fishermen.weatherapp.model.*;
import com.fishermen.weatherapp.service.WeatherService;
import com.fishermen.weatherapp.service.TripCalculatorService;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private TextField durationField;
    @FXML private TextField crewSizeField;
    @FXML private TextField distanceField;
    @FXML private ComboBox<String> locationCombo;
    @FXML private Button calculateButton;
    @FXML private Button historyButton;
    @FXML private Button logoutButton;

    // Weather Card
    @FXML private VBox weatherCard;
    @FXML private GridPane weatherGrid;

    // Safety Card
    @FXML private VBox safetyCard;
    @FXML private ImageView safetyIcon;
    @FXML private Label safetyScoreLabel;
    @FXML private Label safetyStatusLabel;
    @FXML private VBox warningsBox;
    @FXML private VBox warningsList;

    // Resources Card
    @FXML private VBox resourcesCard;
    @FXML private Label foodLabel;
    @FXML private Label waterLabel;
    @FXML private Label fuelLabel;

    private WeatherService weatherService;
    private TripCalculatorService calculatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        weatherService = new WeatherService();
        calculatorService = new TripCalculatorService();

        // Initialize location ComboBox
        locationCombo.setItems(FXCollections.observableArrayList(
                "Gulf of Mexico",
                "Atlantic Coast",
                "Pacific Coast",
                "Great Lakes",
                "Caribbean"
        ));

        // Set welcome message
        if (UserSession.getCurrentUserName() != null) {
            welcomeLabel.setText("Welcome, " + UserSession.getCurrentUserName());
        }

        // Pre-fill crew size from profile
        FishermenProfile profile = UserSession.getCurrentProfile();
        if (profile != null && profile.getCrewSize() != null && !profile.getCrewSize().isEmpty()) {
            crewSizeField.setText(profile.getCrewSize());
        }
    }

    @FXML
    private void handleCalculate() {
        if (!validateTripData()) {
            return;
        }

        calculateButton.setDisable(true);
        calculateButton.setText("Calculating...");

        TripData tripData = new TripData();
        tripData.setDuration(Integer.parseInt(durationField.getText()));
        tripData.setCrewSize(Integer.parseInt(crewSizeField.getText()));
        tripData.setDistance(Integer.parseInt(distanceField.getText()));
        tripData.setLocation(locationCombo.getValue());

        Task<Void> calculationTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Fetch weather data
                WeatherData weather = weatherService.getWeatherData(tripData.getLocation());

                // Calculate trip requirements
                TripCalculation calculation = calculatorService.calculateTrip(
                        tripData, weather, UserSession.getCurrentProfile());

                // Update UI on JavaFX Application Thread
                Platform.runLater(() -> {
                    displayWeatherData(weather);
                    displaySafetyAssessment(calculation);
                    displayResourceRequirements(calculation);

                    // Save to history if safe
                    if (calculation.isSafe()) {
                        UserSession.addTripToHistory(tripData, weather, calculation);
                    }

                    calculateButton.setDisable(false);
                    calculateButton.setText("Calculate Trip Requirements");
                });

                return null;
            }
        };

        new Thread(calculationTask).start();
    }

    @FXML
    private void handleHistory() {
        SceneManager.switchScene("/fxml/history.fxml", "Fishermen Weather App - Trip History");
    }

    @FXML
    private void handleDashboard() {
        SceneManager.switchScene("/fxml/dashboard.fxml", "Weather Dashboard");
    }

    @FXML
    private void handleLogout() {
        UserSession.clearSession();
        SceneManager.switchScene("login.fxml", "Fishermen Weather App - Login");
    }

    private boolean validateTripData() {
        if (durationField.getText().trim().isEmpty() ||
                crewSizeField.getText().trim().isEmpty() ||
                distanceField.getText().trim().isEmpty() ||
                locationCombo.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Data");
            alert.setHeaderText("Please fill in all trip details");
            alert.showAndWait();
            return false;
        }

        try {
            Integer.parseInt(durationField.getText());
            Integer.parseInt(crewSizeField.getText());
            Integer.parseInt(distanceField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please enter valid numbers for duration, crew size, and distance");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    private void displayWeatherData(WeatherData weather) {
        weatherGrid.getChildren().clear();

        addWeatherRow(0, "Temperature:", weather.getTemperature() + "°F");
        addWeatherRow(1, "Wind Speed:", weather.getWindSpeed() + " mph");
        addWeatherRow(2, "Wave Height:", weather.getWaveHeight() + " ft");
        addWeatherRow(3, "Visibility:", weather.getVisibility() + " miles");
        addWeatherRow(4, "Condition:", weather.getCondition());

        weatherCard.setVisible(true);
    }

    private void addWeatherRow(int row, String label, String value) {
        Text labelText = new Text(label);
        labelText.getStyleClass().add("weather-label");

        Text valueText = new Text(value);
        valueText.getStyleClass().add("weather-value");

        weatherGrid.add(labelText, 0, row);
        weatherGrid.add(valueText, 1, row);
    }

    private void displaySafetyAssessment(TripCalculation calculation) {
        // Set safety icon - using placeholder if images not available
        try {
            String iconPath = calculation.isSafe() ? "/images/check-icon.png" : "/images/warning-icon.png";
            safetyIcon.setImage(new Image(getClass().getResourceAsStream(iconPath)));
        } catch (Exception e) {
            // If images not available, just skip the icon
            safetyIcon.setVisible(false);
        }

        // Set safety score
        safetyScoreLabel.setText(calculation.getSafetyScore() + "/100");
        safetyScoreLabel.getStyleClass().clear();
        safetyScoreLabel.getStyleClass().add(calculation.isSafe() ? "safety-score-good" : "safety-score-bad");

        // Set safety status
        String statusText = calculation.isSafe() ?
                "✅ This trip is SAFE to proceed based on current conditions." :
                "⚠️ This trip is NOT SAFE. Consider postponing or changing plans.";
        safetyStatusLabel.setText(statusText);
        safetyStatusLabel.getStyleClass().clear();
        safetyStatusLabel.getStyleClass().add(calculation.isSafe() ? "safety-status-good" : "safety-status-bad");

        // Display warnings
        if (!calculation.getWarnings().isEmpty()) {
            warningsList.getChildren().clear();
            for (String warning : calculation.getWarnings()) {
                Text warningText = new Text("⚠ " + warning);
                warningText.getStyleClass().add("warning-text");
                warningsList.getChildren().add(warningText);
            }
            warningsBox.setVisible(true);
        } else {
            warningsBox.setVisible(false);
        }

        safetyCard.setVisible(true);
    }

    private void displayResourceRequirements(TripCalculation calculation) {
        foodLabel.setText(calculation.getFoodRequired() + " kg");
        waterLabel.setText(calculation.getWaterRequired() + " liters");
        fuelLabel.setText(calculation.getFuelRequired() + " gallons");

        resourcesCard.setVisible(true);
    }
}
