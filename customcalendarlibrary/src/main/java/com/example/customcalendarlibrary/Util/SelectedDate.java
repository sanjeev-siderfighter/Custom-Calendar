package com.example.customcalendarlibrary.Util;

import java.util.List;

public class SelectedDate {

    private int selectedStartDay, selectedEndDay, selectedStartMonthPos, selectedEndMonthPos;
    private List<String> yearList;

    public SelectedDate(int selectedStartDay, int selectedEndDay, int selectedStartMonthPos, int selectedEndMonthPos, List<String> yearList) {
        this.selectedStartDay = selectedStartDay;
        this.selectedEndDay = selectedEndDay;
        this.selectedStartMonthPos = selectedStartMonthPos;
        this.selectedEndMonthPos = selectedEndMonthPos;
        this.yearList = yearList;
    }

    public void setYearList(List<String> yearList) {
        this.yearList = yearList;
    }

    public List<String> getYearList() {
        return yearList;
    }

    public void setSelectedDate(int selectedStartDay, int selectedEndDay, int selectedStartMonthPos, int selectedEndMonthPos) {
        this.selectedStartDay = selectedStartDay;
        this.selectedEndDay = selectedEndDay;
        this.selectedStartMonthPos = selectedStartMonthPos;
        this.selectedEndMonthPos = selectedEndMonthPos;
    }

    public int getSelectedStartDay() {
        return selectedStartDay;
    }

    public void setSelectedStartDay(int selectedStartDay) {
        this.selectedStartDay = selectedStartDay;
    }

    public int getSelectedEndDay() {
        return selectedEndDay;
    }

    public void setSelectedEndDay(int selectedEndDay) {
        this.selectedEndDay = selectedEndDay;
    }

    public int getSelectedStartMonthPos() {
        return selectedStartMonthPos;
    }

    public void setSelectedStartMonthPos(int selectedStartMonthPos) {
        this.selectedStartMonthPos = selectedStartMonthPos;
    }

    public int getSelectedEndMonthPos() {
        return selectedEndMonthPos;
    }

    public void setSelectedEndMonthPos(int selectedEndMonthPos) {
        this.selectedEndMonthPos = selectedEndMonthPos;
    }
}
