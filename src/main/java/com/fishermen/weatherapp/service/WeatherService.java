package com.fishermen.weatherapp.service;

import com.fishermen.weatherapp.model.WeatherData;
import java.util.Random;

public class WeatherService {
    private Random random = new Random();

    public WeatherData getWeatherData(String location) {
        // Simulate API call delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Generate realistic weather data based on location
        WeatherData weather = new WeatherData();
        
        // Temperature range based on location
        int baseTemp = getBaseTemperature(location);
        weather.setTemperature(baseTemp + random.nextInt(20) - 10);
        
        // Wind speed (5-30 mph)
        weather.setWindSpeed(5 + random.nextInt(25));
        
        // Wave height (1-8 feet)
        weather.setWaveHeight(1 + random.nextInt(8));
        
        // Visibility (5-10 miles)
        weather.setVisibility(5 + random.nextInt(6));
        
        // Weather condition
        String[] conditions = {"Clear", "Partly Cloudy", "Cloudy", "Light Rain", "Heavy Rain", "Stormy"};
        weather.setCondition(conditions[random.nextInt(conditions.length)]);
        
        // Humidity (40-80%)
        weather.setHumidity(40 + random.nextInt(40));

        return weather;
    }

    private int getBaseTemperature(String location) {
        switch (location.toLowerCase()) {
            case "gulf of mexico":
            case "caribbean":
                return 80;
            case "atlantic coast":
            case "pacific coast":
                return 70;
            case "great lakes":
                return 60;
            default:
                return 75;
        }
    }
}
