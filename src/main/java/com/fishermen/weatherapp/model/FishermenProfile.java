package com.fishermen.weatherapp.model;

public class FishermenProfile {
    private String name;
    private String experience;
    private String boatType;
    private String boatLength;
    private String crewSize;
    private String licenseNumber;
    private String homePort;
    private String emergencyContact;
    private String specialization;

    // Constructors
    public FishermenProfile() {}

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getBoatType() { return boatType; }
    public void setBoatType(String boatType) { this.boatType = boatType; }

    public String getBoatLength() { return boatLength; }
    public void setBoatLength(String boatLength) { this.boatLength = boatLength; }

    public String getCrewSize() { return crewSize; }
    public void setCrewSize(String crewSize) { this.crewSize = crewSize; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getHomePort() { return homePort; }
    public void setHomePort(String homePort) { this.homePort = homePort; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
}
