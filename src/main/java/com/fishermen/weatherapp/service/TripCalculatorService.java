package com.fishermen.weatherapp.service;

import com.fishermen.weatherapp.model.*;

public class TripCalculatorService {

    public TripCalculation calculateTrip(TripData tripData, WeatherData weather, FishermenProfile profile) {
        TripCalculation calculation = new TripCalculation();

        // Calculate food requirements (kg per person per day)
        double foodPerPersonPerDay = 1.5;
        int totalFood = (int) Math.ceil(tripData.getDuration() * tripData.getCrewSize() * foodPerPersonPerDay);
        calculation.setFoodRequired(totalFood);

        // Calculate water requirements (liters per person per day)
        double waterPerPersonPerDay = 4.0;
        int totalWater = (int) Math.ceil(tripData.getDuration() * tripData.getCrewSize() * waterPerPersonPerDay);
        calculation.setWaterRequired(totalWater);

        // Calculate fuel requirements based on boat type and distance
        double fuelEfficiency = getFuelEfficiency(profile != null ? profile.getBoatType() : "Medium Boat (20-40ft)");
        int totalFuel = (int) Math.ceil((tripData.getDistance() * 2) / fuelEfficiency); // Round trip
        calculation.setFuelRequired(totalFuel);

        // Safety assessment
        int safetyScore = 100;

        if (weather.getWindSpeed() > 20) {
            calculation.addWarning("High wind speeds detected (" + weather.getWindSpeed() + " mph)");
            safetyScore -= 30;
        }

        if (weather.getWaveHeight() > 6) {
            calculation.addWarning("High wave conditions (" + weather.getWaveHeight() + " ft)");
            safetyScore -= 25;
        }

        if (weather.getVisibility() < 7) {
            calculation.addWarning("Poor visibility conditions (" + weather.getVisibility() + " miles)");
            safetyScore -= 20;
        }

        if (weather.getCondition().contains("Rain") || weather.getCondition().equals("Stormy")) {
            calculation.addWarning("Adverse weather conditions: " + weather.getCondition());
            safetyScore -= 20;
        }

        // Additional safety checks based on experience
        if (profile != null && profile.getExperience() != null) {
            if (profile.getExperience().equals("0-2 years") && weather.getWindSpeed() > 15) {
                calculation.addWarning("High winds not recommended for beginners");
                safetyScore -= 15;
            }
        }

        calculation.setSafetyScore(Math.max(0, safetyScore));
        calculation.setSafe(safetyScore >= 70);

        return calculation;
    }

    private double getFuelEfficiency(String boatType) {
        if (boatType == null) return 2.5;
        
        switch (boatType.toLowerCase()) {
            case "small boat (< 20ft)":
                return 4.0; // 4 miles per gallon
            case "medium boat (20-40ft)":
                return 2.5; // 2.5 miles per gallon
            case "large boat (> 40ft)":
            case "trawler":
                return 1.5; // 1.5 miles per gallon
            default:
                return 2.5;
        }
    }
}
