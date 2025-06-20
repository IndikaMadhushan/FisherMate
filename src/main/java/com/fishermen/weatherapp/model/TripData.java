package com.fishermen.weatherapp.model;

public class TripData {
    private int duration;
    private int crewSize;
    private int distance;
    private String location;

    // Constructors
    public TripData() {}

    public TripData(int duration, int crewSize, int distance, String location) {
        this.duration = duration;
        this.crewSize = crewSize;
        this.distance = distance;
        this.location = location;
    }

    // Getters and Setters
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getCrewSize() { return crewSize; }
    public void setCrewSize(int crewSize) { this.crewSize = crewSize; }

    public int getDistance() { return distance; }
    public void setDistance(int distance) { this.distance = distance; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
