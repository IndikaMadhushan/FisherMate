package org.example.fishermatenew.dao;

public class WeatherData {
    private double rainProbability;
    private double windSpeed;

    private int visibility;
    private boolean lightning;
    private boolean storms;

    public WeatherData() {
    }

    public double getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(double rainProbability) {
        this.rainProbability = rainProbability;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public boolean isLightning() {
        return lightning;
    }

    public void setLightning(boolean lightning) {
        this.lightning = lightning;
    }

    public boolean isStorms() {
        return storms;
    }

    public void setStorms(boolean storms) {
        this.storms = storms;
    }

}
