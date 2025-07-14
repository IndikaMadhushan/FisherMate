package org.example.fishermatenew.models;

import org.example.fishermatenew.dao.DataRetrieverAndPrinter;
import org.example.fishermatenew.dao.WeatherData;


public class RealMaxDays {
    final static int FORECAST_CONSTANT = 8;

    public static int realMaxDays(Inputs inputData) {
        int numberOfForecasts = inputData.getMaxDays() * FORECAST_CONSTANT;
        WeatherData data;
        for (int i = 0; i < numberOfForecasts; i++) {
            data = DataRetrieverAndPrinter.dataRetriever(inputData);


            assert data != null;
            int result = Comparing.compare(data);


            if (result == -1) {

                return (i / FORECAST_CONSTANT);
            }

            inputData.setDateTime(ThreeHourTimeUpdater.updateTime(inputData));
        }

        return inputData.getMaxDays(); // All forecasts safe
    }
}
