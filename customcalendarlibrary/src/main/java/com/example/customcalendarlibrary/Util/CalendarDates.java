package com.example.customcalendarlibrary.Util;

public enum CalendarDates {

    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

    public int getNumberOfDays(int year) {

        boolean isLeapYear = leapYear(year);

        switch (this) {

            case FEBRUARY:
                return isLeapYear ? 29 : 28;

            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER:
                return 30;

            default:
                return 31;
        }
    }

    public String getMonthName() {

        switch (this) {

            case JANUARY:
                return "JANUARY";

            case FEBRUARY:
                return "FEBRUARY";

            case MARCH:
                return "MARCH";

            case APRIL:
                return "APRIL";

            case MAY:
                return "MAY";

            case JUNE:
                return "JUNE";

            case JULY:
                return "JULY";

            case AUGUST:
                return "AUGUST";

            case SEPTEMBER:
                return "SEPTEMBER";

            case OCTOBER:
                return "OCTOBER";

            case NOVEMBER:
                return "NOVEMBER";

            case DECEMBER:
                return "DECEMBER";

            default:
                return "Not Possible";
        }
    }

    public int getValue() {

        switch (this) {

            case JANUARY:
                return 0;
            case FEBRUARY:
                return 1;
            case MARCH:
                return 2;
            case APRIL:
                return 3;
            case MAY:
                return 4;
            case JUNE:
                return 5;
            case JULY:
                return 6;
            case AUGUST:
                return 7;
            case SEPTEMBER:
                return 8;
            case OCTOBER:
                return 9;
            case NOVEMBER:
                return 10;
            case DECEMBER:
                return 11;
            default:
                return -1;
        }
    }

    public static CalendarDates getMonthFromName(String name) {

        switch (name) {

            case "JANUARY":
                return JANUARY;
            case "FEBRUARY":
                return FEBRUARY;
            case "MARCH":
                return MARCH;
            case "APRIL":
                return APRIL;
            case "MAY":
                return MAY;
            case "JUNE":
                return JUNE;
            case "JULY":
                return JULY;
            case "AUGUST":
                return AUGUST;
            case "SEPTEMBER":
                return SEPTEMBER;
            case "OCTOBER":
                return OCTOBER;
            case "NOVEMBER":
                return NOVEMBER;
            case "DECEMBER":
                return DECEMBER;
            default:
                return null;

        }
    }

    public static CalendarDates getMonthFromValue(int value) {

        switch (value) {

            case 0:
                return JANUARY;
            case 1:
                return FEBRUARY;
            case 2:
                return MARCH;
            case 3:
                return APRIL;
            case 4:
                return MAY;
            case 5:
                return JUNE;
            case 6:
                return JULY;
            case 7:
                return AUGUST;
            case 8:
                return SEPTEMBER;
            case 9:
                return OCTOBER;
            case 10:
                return NOVEMBER;
            case 11:
                return DECEMBER;
            default:
                return null;
        }
    }

    private boolean leapYear(int year) {

        if (year % 4 == 0) {

            if (year % 100 == 0) {

                return year % 400 == 0;
            } else {

                return true;
            }
        } else {

            return false;
        }
    }
}