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

    private boolean leapYear(int year) {

        if (year % 4 == 0) {

            if (year % 100 == 0) {

                if (year % 400 == 0) {

                    return true;
                } else {

                    return false;
                }
            } else {

                return true;
            }
        } else {

            return false;
        }
    }
}