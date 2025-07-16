package org.example.fishermatenew.models;

public class HistoryRecord {
    private String date;
    private String location;
    private String time;
    private int crewMembers;

    public HistoryRecord(String date, String location, String time, int crewMembers) {
        this.date = date;
        this.location = location;
        this.time = time;
        this.crewMembers = crewMembers;
    }

    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getTime() { return time; }
    public int getCrewMembers() { return crewMembers; }
}
