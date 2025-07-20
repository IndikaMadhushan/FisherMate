package org.example.fishermatenew;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import org.example.fishermatenew.controller.HistoryController;
import org.example.fishermatenew.dao.WeatherDataUpdater;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.fishermatenew.dao.DBconnection;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 500);
        stage.initStyle(StageStyle.UNIFIED);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/example/fishermatenew/Images/login.png")));
        stage.setTitle("FisherMate");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Ensure the table is created before launching the application
        DBconnection dbConnection = new DBconnection();
        dbConnection.getConnection();

        // Launch the JavaFX application
        WeatherDataUpdater.updateWeatherData();
        launch();
    }
}