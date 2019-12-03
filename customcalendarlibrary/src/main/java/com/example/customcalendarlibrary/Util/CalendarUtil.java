package com.example.customcalendarlibrary.Util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarUtil {

    public static final int NUMBER_OF_MONTHS = 12;
    public static final int INITIAL_VALUE = -1;
    public static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;
    public static final String BLANK_TEXT = " ";

    public static int dayPosition(String day) {

        switch (day) {
            case "Sun":
                return 0;
            case "Mon":
                return 1;
            case "Tue":
                return 2;
            case "Wed":
                return 3;
            case "Thu":
                return 4;
            case "Fri":
                return 5;
            case "Sat":
                return 6;
            default:
                return -1;
        }
    }

    public static String dateAddition(int day1, int month1, int year1, int lenDay) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year1, month1, day1);
        calendar.add(Calendar.DATE, lenDay);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String res = dateFormat.format(calendar.getTime());
        Log.d("tagger", res);
        return res;
    }
}
