package org.example.fishermatenew.models;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class ThreeHourTimeUpdater {

    public static String updateTime(Inputs inputData) {

        String updatedTime = null;

        Timestamp timestamp = Timestamp.valueOf(inputData.getDateTime());

        // Create Calendar to add 3 hours
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);
        cal.add(Calendar.HOUR_OF_DAY, 3);

        // Get new Timestamp
        Timestamp newTimestamp = new Timestamp(cal.getTimeInMillis());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convert to LocalDateTime and format it
        updatedTime = newTimestamp.toLocalDateTime().format(formatter);

        return updatedTime;
    }

}
