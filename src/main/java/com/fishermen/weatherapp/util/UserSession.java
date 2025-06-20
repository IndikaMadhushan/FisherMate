package com.fishermen.weatherapp.util;

import com.fishermen.weatherapp.model.*;
import java.util.List;
import java.util.ArrayList;

public class UserSession {
    private static String currentUserEmail;
    private static String currentUserName;
    private static FishermenProfile currentProfile;
    private static List<TripHistory> tripHistory = new ArrayList<>();

    // User management
    public static void setCurrentUser(String email, String name) {
        currentUserEmail = email;
        currentUserName = name;
    }

    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    // Profile management
    public static void setCurrentProfile(FishermenProfile profile) {
        currentProfile = profile;
    }

    public static FishermenProfile getCurrentProfile() {
        return currentProfile;
    }

    // Trip history management
    public static void addTripToHistory(TripData tripData, WeatherData weatherData, TripCalculation calculation) {
        TripHistory trip = new TripHistory(tripData, weatherData, calculation);
        tripHistory.add(0, trip); // Add to beginning of list
    }

    public static List<TripHistory> getTripHistory() {
        return new ArrayList<>(tripHistory);
    }

    public static void removeTripFromHistory(TripHistory trip) {
        tripHistory.removeIf(t -> t.getId() == trip.getId());
    }

    public static void clearTripHistory() {
        tripHistory.clear();
    }

    // Session management
    public static void clearSession() {
        currentUserEmail = null;
        currentUserName = null;
        currentProfile = null;
        tripHistory.clear();
    }

    public static boolean isLoggedIn() {
        return currentUserEmail != null;
    }
}
