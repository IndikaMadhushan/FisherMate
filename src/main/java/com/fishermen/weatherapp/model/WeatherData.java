package com.fishermen.weatherapp.model;

public class WeatherData {
    private int temperature;
    private int windSpeed;
    private int waveHeight;
    private int visibility;
    private String condition;
    private int humidity;

    // Constructors
    public WeatherData() {}

    public WeatherData(int temperature, int windSpeed, int waveHeight, int visibility, String condition, int humidity) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.waveHeight = waveHeight;
        this.visibility = visibility;
        this.condition = condition;
        this.humidity = humidity;
    }

    // Getters and Setters
    public int getTemperature() { return temperature; }
    public void setTemperature(int temperature) { this.temperature = temperature; }

    public int getWindSpeed() { return windSpeed; }
    public void setWindSpeed(int windSpeed) { this.windSpeed = windSpeed; }

    public int getWaveHeight() { return waveHeight; }
    public void setWaveHeight(int waveHeight) { this.waveHeight = waveHeight; }

    public int getVisibility() { return visibility; }
    public void setVisibility(int visibility) { this.visibility = visibility; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }
}
