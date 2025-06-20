package com.fishermen.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.fishermen.weatherapp.util.SceneManager;


public class FishermenWeatherApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
       SceneManager.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Fishermen Weather App");
//        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/anchor-icon.png")));

        // Load the login scene first
        SceneManager.switchScene("/fxml/login.fxml", "Fishermen Weather App - Login");
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
//        Scene scene = new Scene(loader.load());
//        primaryStage.setTitle("Fishermen Weather App - Login");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(true);
//        primaryStage.setMinWidth(800);
//        primaryStage.setMinHeight(600);
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
