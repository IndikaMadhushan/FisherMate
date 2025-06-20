package com.fishermen.weatherapp.model;

import java.time.LocalDateTime;

public class TripHistory {
    private long id;
    private LocalDateTime date;
    private TripData tripData;
    private WeatherData weatherData;
    private TripCalculation calculation;

    // Constructors
    public TripHistory() {
        this.id = System.currentTimeMillis();
        this.date = LocalDateTime.now();
    }

    public TripHistory(TripData tripData, WeatherData weatherData, TripCalculation calculation) {
        this();
        this.tripData = tripData;
        this.weatherData = weatherData;
        this.calculation = calculation;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public TripData getTripData() { return tripData; }
    public void setTripData(TripData tripData) { this.tripData = tripData; }

    public WeatherData getWeatherData() { return weatherData; }
    public void setWeatherData(WeatherData weatherData) { this.weatherData = weatherData; }

    public TripCalculation getCalculation() { return calculation; }
    public void setCalculation(TripCalculation calculation) { this.calculation = calculation; }
}
