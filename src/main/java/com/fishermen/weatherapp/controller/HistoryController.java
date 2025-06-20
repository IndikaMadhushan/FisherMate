package com.fishermen.weatherapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import com.fishermen.weatherapp.util.SceneManager;
import com.fishermen.weatherapp.util.UserSession;
import com.fishermen.weatherapp.model.TripHistory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HistoryController implements Initializable {

    @FXML private Button backButton;
    @FXML private Button clearAllButton;
    @FXML private VBox emptyState;
    @FXML private VBox historyContent;
    @FXML private Text historyCountLabel;
    @FXML private VBox historyList;
    @FXML private Button planTripButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTripHistory();
    }

    @FXML
    private void handleBack() {
        SceneManager.switchScene("/fxml/dashboard.fxml", "Fishermen Weather App - Dashboard");
    }

    @FXML
    private void handleClearAll() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear History");
        alert.setHeaderText("Are you sure you want to clear all trip history?");
        alert.setContentText("This action cannot be undone.");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            UserSession.clearTripHistory();
            loadTripHistory();
        }
    }

    @FXML
    private void handlePlanTrip() {
        SceneManager.switchScene("/fxml/dashboard.fxml", "Fishermen Weather App - Dashboard");
    }

    private void loadTripHistory() {
        List<TripHistory> history = UserSession.getTripHistory();
        
        if (history.isEmpty()) {
            emptyState.setVisible(true);
            historyContent.setVisible(false);
            clearAllButton.setVisible(false);
        } else {
            emptyState.setVisible(false);
            historyContent.setVisible(true);
            clearAllButton.setVisible(true);
            
            historyCountLabel.setText("Safe Fishing Trips (" + history.size() + ")");
            
            historyList.getChildren().clear();
            for (TripHistory trip : history) {
                historyList.getChildren().add(createTripCard(trip));
            }
        }
    }

    private VBox createTripCard(TripHistory trip) {
        VBox card = new VBox(10);
        card.getStyleClass().add("history-card");
        card.setPadding(new Insets(15));

        // Header
        HBox header = new HBox(10);
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        ImageView locationIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/location-icon.png")));
        locationIcon.setFitHeight(20);
        locationIcon.setFitWidth(20);

        Text locationText = new Text(formatLocation(trip.getTripData().getLocation()));
        locationText.getStyleClass().add("trip-location");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label safeLabel = new Label("Safe Trip");
        safeLabel.getStyleClass().add("safe-trip-badge");

        Button deleteButton = new Button("🗑");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(e -> deleteTripEntry(trip));

        header.getChildren().addAll(locationIcon, locationText, spacer, safeLabel, deleteButton);

        // Date
        Text dateText = new Text("📅 " + trip.getDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")));
        dateText.getStyleClass().add("trip-date");

        // Details Grid
        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(20);
        detailsGrid.setVgap(10);

        // Trip Details Column
        VBox tripDetails = createDetailsColumn("Trip Details",
            "⏱ " + trip.getTripData().getDuration() + " days",
            "👥 " + trip.getTripData().getCrewSize() + " crew members",
            "🗺 " + trip.getTripData().getDistance() + " nautical miles");

        // Weather Column
        VBox weatherDetails = createDetailsColumn("Weather",
            "🌡 " + trip.getWeatherData().getTemperature() + "°F",
            "💨 " + trip.getWeatherData().getWindSpeed() + " mph",
            "🌊 " + trip.getWeatherData().getWaveHeight() + " ft",
            "☁ " + trip.getWeatherData().getCondition());

        // Resources Column
        VBox resourceDetails = createDetailsColumn("Resources",
            "🍽 " + trip.getCalculation().getFoodRequired() + " kg food",
            "💧 " + trip.getCalculation().getWaterRequired() + " L water",
            "⛽ " + trip.getCalculation().getFuelRequired() + " gal fuel");

        // Safety Column
        VBox safetyDetails = createDetailsColumn("Safety",
            "📊 Score: " + trip.getCalculation().getSafetyScore() + "/100",
            trip.getCalculation().getWarnings().isEmpty() ? 
                "✅ No warnings" : 
                "⚠ " + trip.getCalculation().getWarnings().size() + " warning(s)");

        detailsGrid.add(tripDetails, 0, 0);
        detailsGrid.add(weatherDetails, 1, 0);
        detailsGrid.add(resourceDetails, 2, 0);
        detailsGrid.add(safetyDetails, 3, 0);

        card.getChildren().addAll(header, dateText, detailsGrid);
        return card;
    }

    private VBox createDetailsColumn(String title, String... details) {
        VBox column = new VBox(5);
        
        Text titleText = new Text(title);
        titleText.getStyleClass().add("details-title");
        column.getChildren().add(titleText);

        for (String detail : details) {
            Text detailText = new Text(detail);
            detailText.getStyleClass().add("details-text");
            column.getChildren().add(detailText);
        }

        return column;
    }

    private String formatLocation(String location) {
        location = location.replace("-", " ")
                .replace("_", " ");

        // Capitalize first letter of each word
        Matcher matcher = Pattern.compile("\\b\\w").matcher(location);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group().toUpperCase());
        }
        matcher.appendTail(sb);

        return sb.toString();
    }


    private void deleteTripEntry(TripHistory trip) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Trip");
        alert.setHeaderText("Are you sure you want to delete this trip record?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            UserSession.removeTripFromHistory(trip);
            loadTripHistory();
        }
    }
}
