package org.self.taskmaster.functions;

import org.self.taskmaster.models.Task;

import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;

public class Processing {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


//    public static LocalDateTime DateTimeProcessing(String time, Boolean pm, LocalDate date) {
//
//        // Split the time string into hours and minutes
//        String[] timeArray = time.split(":");
//        int hours = Integer.parseInt(timeArray[0]);
//        int minutes = Integer.parseInt(timeArray[1]);
//
//        // Adjust for AM/PM
//        if (pm && hours != 12) {
//            hours += 12; // Convert PM hours to 24-hour format
//        } else if (!pm && hours == 12) {
//            hours = 0; // Convert 12 AM to 0
//        }
//        // Create LocalTime and combine it with the LocalDate
//        LocalTime localTime = LocalTime.of(hours, minutes);
//        return LocalDateTime.of(date, localTime);
//    }

    public static Time TimeProcessing(String text) {
        try {
            return Time.valueOf(text); // Expecting a format like "14:30:00"
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid time format: " + text);
            return null; // Handle invalid input gracefully
        }
    }
    public static String[] ReverTimeProcessing(Task task) {
        String[] arrString = {};
        arrString[0]= task.getStart_time().toString();
        arrString[1]= task.getStart_date().toString();
        return arrString;
    }

    public static Date DateProcessing(LocalDate text) {
        try {
            return Date.valueOf(text); //
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid time format: " + text);
            return null; // Handle invalid input gracefully
        }
    }


    public static String formatToString(LocalDate date) {
        return (date != null) ? formatter.format(date) : "";
    }

    public static LocalDate formatString(String string) {
        System.out.println(string);
        return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
    }
    public static Date formatDate(LocalDate date) {
        System.out.println(date);
        return Date.valueOf(date);
    }
}
