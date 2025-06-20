package com.fishermen.weatherapp.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlFile));
            Parent root = loader.load(); // Load once

            Scene scene = new Scene(root);
            scene.getStylesheets().add(SceneManager.class.getResource("/css/styles.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlFile);
            e.printStackTrace();
        }
    }
}