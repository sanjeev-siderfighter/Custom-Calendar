package com.example.mylibraryprojects;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customcalendarlibrary.CustomCalendar;

public class MainActivity extends AppCompatActivity implements CustomCalendar.CustomCalendarSelectedDatesListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomCalendar calendar = findViewById(R.id.custom_calendar_view);
        Toast.makeText(this, "date = " + calendar.getTodaysDate(), Toast.LENGTH_SHORT).show();

//        String r = calendar.addDaysToDate(17, 2, 2019, -54);
//        calendar.setMinDate(12, 2, 2018);
//        calendar.setAlreadySelectedDate(19, 2, 2018);
    }

    @Override
    public void getSelectedDates(String selectedDate) {
//        Toast.makeText(this, "date = " + selectedDate.getSelectedStartDay() + " " + selectedDate.getSelectedEndDay() + " " + selectedDate.getSelectedStartMonthPos() + " " + selectedDate.getSelectedEndMonthPos(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show();
    }
}
