package com.example.customcalendarlibrary.Util;

public enum WeekDays {

    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    public int getDayValue() {

        switch (this) {

            case SUNDAY:
                return 0;
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            case SATURDAY:
                return 6;
            default:
                return -1;
        }
    }

    public String getDayName() {

        switch (this) {

            case SUNDAY:
                return "SUNDAY";
            case MONDAY:
                return "MONDAY";
            case TUESDAY:
                return "TUESDAY";
            case WEDNESDAY:
                return "WEDNESDAY";
            case THURSDAY:
                return "THURSDAY";
            case FRIDAY:
                return "FRIDAY";
            case SATURDAY:
                return "SATURDAY";
            default:
                return "NOT POSSIBLE";
        }
    }
}
