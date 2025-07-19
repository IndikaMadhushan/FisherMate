package org.example.fishermatenew.models;

import org.example.fishermatenew.dao.DataRetrieverAndPrinter;
import org.example.fishermatenew.dao.WeatherData;



public class DisplayAllWeatherInfo {
    public static String displayallweatherinfo(Inputs inputData) {

        int noOfIterarions = inputData.getMaxDays() * 8; // 8 forecasts per day
        StringBuilder weatherAllInfo = null;
        weatherAllInfo = new StringBuilder();
        for (int i = 0; i < noOfIterarions; i++) {
            String weatherInfo = DataRetrieverAndPrinter.dataprinter(inputData);
            inputData.setDateTime(ThreeHourTimeUpdater.updateTime(inputData));

            if (weatherInfo != null) {
                weatherAllInfo.append(weatherInfo);
            } else {
                weatherAllInfo.append("No weather data available for this iteration.");
            }


        }
        return weatherAllInfo.toString();
    }
}
