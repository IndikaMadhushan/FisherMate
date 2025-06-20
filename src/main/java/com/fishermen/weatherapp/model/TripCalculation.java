package com.fishermen.weatherapp.model;

import java.util.List;
import java.util.ArrayList;

public class TripCalculation {
    private int foodRequired;
    private int waterRequired;
    private int fuelRequired;
    private boolean safe;
    private int safetyScore;
    private List<String> warnings;

    // Constructors
    public TripCalculation() {
        this.warnings = new ArrayList<>();
    }

    // Getters and Setters
    public int getFoodRequired() { return foodRequired; }
    public void setFoodRequired(int foodRequired) { this.foodRequired = foodRequired; }

    public int getWaterRequired() { return waterRequired; }
    public void setWaterRequired(int waterRequired) { this.waterRequired = waterRequired; }

    public int getFuelRequired() { return fuelRequired; }
    public void setFuelRequired(int fuelRequired) { this.fuelRequired = fuelRequired; }

    public boolean isSafe() { return safe; }
    public void setSafe(boolean safe) { this.safe = safe; }

    public int getSafetyScore() { return safetyScore; }
    public void setSafetyScore(int safetyScore) { this.safetyScore = safetyScore; }

    public List<String> getWarnings() { return warnings; }
    public void setWarnings(List<String> warnings) { this.warnings = warnings; }
    
    public void addWarning(String warning) { this.warnings.add(warning); }
}
