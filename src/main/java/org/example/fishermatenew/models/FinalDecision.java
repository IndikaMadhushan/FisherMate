package org.example.fishermatenew.models;

import org.example.fishermatenew.dao.DataRetriever;
import org.example.fishermatenew.dao.WeatherData;

public class FinalDecision {
    public static String finalDecision(Inputs inputdata) {
        WeatherData data = DataRetriever.dataRetriever(inputdata);

        if (Comparing.compare(data) == -1) {
            return "\nThe trip is not recommended due to bad weather conditions.";
        } else {

            int result =RealMaxDays.realMaxDays(inputdata);
            if (result == 0) {
                return "\nThe trip is recommended based on the weather conditions.\nUnfortunately based on the weather condition of the rest of the day, you cannot stay for the whole day.";
            } else if (result< inputdata.getMaxDays()) {
                return "\nThe trip is recommended based on the weather conditions.\nYou can only stay safely for only " + result + " days." + "\n" + Calculator.Consumption(inputdata, result);
            } else {
                return "\nThe trip is recommended based on the weather conditions.You can stay your maximumly expected  " + inputdata.getMaxDays() + " days." + "\n" + Calculator.Consumption(inputdata, inputdata.getMaxDays());
            }

        }
    }
}
