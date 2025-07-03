package org.example.fishermatenew.models;

public class Calculator {

    public static String Consumption(Inputs inputData, int days) {
        String topic = "Approximating the consumptions...";
        double water = inputData.getNoOfCrewMembers() * 25 * days;
        String aboutWater = "Maximum water Consumption: " + water + " liters";

        double food = inputData.getNoOfCrewMembers() * 2.5 * days;
        String aboutFood = "Maximum food Consumption: " + food + " kg";

        double fuel = 500 * days;
        String aboutFuel = "Maximum fuel Consumption: " + fuel + " liters";
        return "\n" + topic + "\n" + aboutWater + "\n" + aboutFood + "\n" + aboutFuel;

    }
}
