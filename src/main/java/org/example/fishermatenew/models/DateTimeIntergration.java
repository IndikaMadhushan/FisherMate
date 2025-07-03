package org.example.fishermatenew.models;

public class DateTimeIntergration {
    public static void datetimeintegration(String date, String time, Inputs inputData) {
      String intergratedDateTime = date + " " + time;
        inputData.setDateTime(intergratedDateTime);
    }

}
