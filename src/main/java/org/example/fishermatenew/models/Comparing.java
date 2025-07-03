package org.example.fishermatenew.models;

import org.example.fishermatenew.dao.WeatherData;

public class Comparing {

    public static int compare(WeatherData data) {
        if (data == null) {
            throw new IllegalArgumentException("WeatherData cannot be null");
        }

        if (data.getRainProbability() > 70 || data.getWindSpeed() > 50 || data.isStorms()
                || data.getVisibility() < 1000
                || data.isLightning()) {
            return -1;

        } else {
            return 1;
        }
    }
}
