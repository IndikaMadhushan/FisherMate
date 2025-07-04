package org.example.fishermatenew.dao;

import org.example.fishermatenew.models.Inputs;

import java.sql.*;

public class DataRetriever {
    public static WeatherData dataRetriever(Inputs inputdata) {

        WeatherData weatherData = new WeatherData();

        String url = "jdbc:mysql://localhost:3306/fishermate";
        String username = "root";
        String password = "";

        try {
            Connection con = DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM weather_data WHERE location = ? AND forecast_time = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            String location = inputdata.getLocation();
            String forecastTime = inputdata.getDateTime();

            stmt.setString(1, location);

            stmt.setTimestamp(2, Timestamp.valueOf(forecastTime));

            ResultSet rs = stmt.executeQuery();

            String weatherCondition = null;
            if (rs.next()) {

                weatherData.setRainProbability(rs.getDouble("rain_probability"));
                weatherData.setWindSpeed(rs.getDouble("wind_speed"));
                weatherData.setVisibility(rs.getInt("visibility"));
                weatherCondition = rs.getString("weather_condition");
                weatherData.setLightning(weatherCondition.contains("thunder"));
                weatherData.setStorms(weatherCondition.contains("storm"));

            } else {
                return null; // No data found for the given location and time

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return weatherData;
    }
}
