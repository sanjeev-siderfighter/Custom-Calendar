package com.example.customcalendarlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;z

import com.example.customcalendarlibrary.Adapters.CalendarRecyclerAdapter;
import com.example.customcalendarlibrary.Adapters.DaysGridAdapter;
import com.example.customcalendarlibrary.Util.CalendarDates;
import com.example.customcalendarlibrary.Util.CalendarUtil;
import com.example.customcalendarlibrary.Util.SelectedDate;
import com.example.customcalendarlibrary.Util.Toaster;
import com.example.customcalendarlibrary.databinding.CustomCalendarHomeLayoutBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CustomCalendar extends View {

    private Context mContext;
    private Activity mActivity;

    private CustomCalendarHomeLayoutBinding binding;

    private RecyclerView calendarRecyclerView;
    private CalendarRecyclerAdapter adapter;
    private List<CalendarDates> months;
    private List<String> years;

    private SelectedDate selectedDate;

    private CustomCalendarSelectedDatesListener listener;

    public CustomCalendar(Context context) {
        super(context);
        mContext = context;
        mActivity = (Activity) mContext;
        years = new ArrayList<>();
        listener = (CustomCalendarSelectedDatesListener) context;
        initView(null);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mActivity = (Activity) mContext;
        years = new ArrayList<>();
        listener = (CustomCalendarSelectedDatesListener) context;
        initView(attrs);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mActivity = (Activity) mContext;
        years = new ArrayList<>();
        listener = (CustomCalendarSelectedDatesListener) context;
        initView(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mActivity = (Activity) mContext;
        years = new ArrayList<>();
        listener = (CustomCalendarSelectedDatesListener) context;
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int startYear, startMonth, startDay;

        if (inflater != null) {

            if (attrs != null) {

                /*Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.US);
                String defaultYear = dateFormat.format(calendar.getTime()); // returns the current year...*/

                String today = getTodaysDate(); // dd-MM-yyyy
                String defaultDay = today.substring(0, 2);
                String defaultMonth = today.substring(3, 5);
                String defaultYear = today.substring(6);

                TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CustomCalendar);

                // if startYear = 2019, endYear = 2025, then months from Jan 2019 to Dec 2024 will appear...
                startYear = typedArray.getInt(R.styleable.CustomCalendar_startYear, Integer.valueOf(defaultYear));
                startMonth = typedArray.getInt(R.styleable.CustomCalendar_startDay, Integer.valueOf(defaultMonth));
                startDay = typedArray.getInt(R.styleable.CustomCalendar_startDay, Integer.valueOf(defaultDay));
                int endYear = typedArray.getInt(R.styleable.CustomCalendar_endYear, startYear + 20);
                int toolbarDrawable = typedArray.getInt(R.styleable.CustomCalendar_toolbarDrawable, R.drawable.default_gradient_drawable);

                setDefaultValues(startYear, endYear, toolbarDrawable);

                typedArray.recycle();
            } else {

                String today = getTodaysDate(); // dd-MM-yyyy
                startDay = Integer.parseInt(today.substring(0, 2));
                startMonth = Integer.parseInt(today.substring(3, 5));
                startYear = Integer.parseInt(today.substring(6));

                /*Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                String dateToday = dateFormat.format(calendar.getTime()); // returns the current year...
                int startYear = Integer.valueOf(defaultYear);
                int endYear = Integer.valueOf(defaultYear) + 20;

                setDefaultValues(startYear, endYear, R.drawable.default_gradient_drawable);*/
            }

            binding = DataBindingUtil.setContentView(mActivity, R.layout.custom_calendar_home_layout);
            binding.buttonSave.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedDate = adapter.getSelectedDates();
                    listener.getSelectedDates(getSelectedDate());
                }
            });
            setupGridView();
            setupRecyclerView();
            setMinDate(startDay, startMonth, startYear);
        } else {

            Toaster.generateShortToast(mContext, getResources().getString(R.string.layout_unavailable));
        }
    }

    private void setDefaultValues(int startYear, int endYear, int toolbarDrawable) {

//        binding.mainView.setBackground(ContextCompat.getDrawable(mContext, toolbarDrawable));
//        binding.appBar.setBackgroundResource(toolbarDrawable);
        while (startYear != endYear) {
            years.add(String.valueOf(startYear));
            startYear++;
        }
    }

    private String getSelectedDate() {

        int startDay, startMonth, startYear;
        int endDay = -1, endMonth = -1, endYear = -1;

        startDay = selectedDate.getSelectedStartDay();
        if (startDay == -1) {
            return getResources().getString(R.string.no_date_selected);
        }
        startMonth = months.get(selectedDate.getSelectedStartMonthPos() % 12).getValue() + 1;
        startYear = Integer.valueOf(selectedDate.getYearList().get(selectedDate.getSelectedStartMonthPos() / 12));

        GregorianCalendar gregorianCalendar = new GregorianCalendar(startYear, startMonth - 1, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale.US);
        int startFirstPos = CalendarUtil.dayPosition(dateFormat.format(gregorianCalendar.getTimeInMillis()));

        startDay = startDay + 1 - startFirstPos;

        if (selectedDate.getSelectedEndMonthPos() != -1) {
            endDay = selectedDate.getSelectedEndDay();
            endMonth = months.get(selectedDate.getSelectedEndMonthPos() % 12).getValue() + 1;
            endYear = Integer.valueOf(selectedDate.getYearList().get(selectedDate.getSelectedEndMonthPos() / 12));

            gregorianCalendar.set(endYear, endMonth - 1, 1);
            int endFirstPos = CalendarUtil.dayPosition(dateFormat.format(gregorianCalendar.getTimeInMillis()));

            endDay = endDay + 1 - endFirstPos;
        }

        String res = startDay + "-" + startMonth + "-" + startYear;
        if (endDay > 0) {
            res += "||" + endDay + "-" + endMonth + "-" + endYear;
        }

        return res;
    }

    private void setupGridView() {

        GridView daysGrid = binding.gridViewForDays;
        DaysGridAdapter adapter = new DaysGridAdapter(mContext);
        daysGrid.setAdapter(adapter);
    }

    private void setupRecyclerView() {

        calendarRecyclerView = binding.recyclerViewForDates;

        months = Arrays.asList(CalendarDates.JANUARY, CalendarDates.FEBRUARY, CalendarDates.MARCH,
                CalendarDates.APRIL, CalendarDates.MAY, CalendarDates.JUNE,
                CalendarDates.JULY, CalendarDates.AUGUST, CalendarDates.SEPTEMBER,
                CalendarDates.OCTOBER, CalendarDates.NOVEMBER, CalendarDates.DECEMBER);

//        years = Arrays.asList("2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019");

        calendarRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        calendarRecyclerView.setLayoutManager(layoutManager);

        adapter = new CalendarRecyclerAdapter(mContext, months, years);
        calendarRecyclerView.setAdapter(adapter);

//        calendarRecyclerView.scrollToPosition(49);
    }

    public String getTodaysDate() { // return dd-mm-yyyy format

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        return dateFormat.format(calendar.getTime());
    }

    public String addDaysToDate(int startDay, int startMonth, int startYear, int lengthDay) { // startMonth starts from 0...

        CalendarDates calendarDates = CalendarDates.getMonthFromValue(startMonth);

        if (calendarDates == null) {

            return getResources().getString(R.string.wrong_month_input);
        } else if (startDay < 1 || startDay > calendarDates.getNumberOfDays(startYear)) {

            return getResources().getString(R.string.wrong_days_input);
        } else {

            return CalendarUtil.dateAddition(startDay, startMonth, startYear, lengthDay);
        }
    }

    public void setMinDate(int day, int month, int year) { // day and month counting from 0

        int startYear = Integer.valueOf(years.get(0));

        if (startYear > year) {

            Toaster.generateLongToast(mContext, getResources().getString(R.string.min_date_error));
        } else {

            int monthYearPos;
            int yearPos = year - startYear;
            monthYearPos = (yearPos * 12) + month;

            adapter.disableDates(monthYearPos, day);
            calendarRecyclerView.scrollToPosition(2 * monthYearPos);
            adapter.notifyDataSetChanged();
        }
    }

    public void setMaxDate() {

    }

    public void setAlreadySelectedDate(int day, int month, int year) {

        CalendarDates myMonth = CalendarDates.getMonthFromValue(month);
        if (year > Integer.valueOf(years.get(years.size() - 1)) || month < 0 || month > 11 || day <= 0 || (myMonth != null && day > myMonth.getNumberOfDays(year))) {
            Toaster.generateLongToast(mContext, "Check your already selected date and your min date you provided.");
        } else {
            adapter.setAlreadySelectedDate(day, month, year);
        }
    }

    public interface CustomCalendarSelectedDatesListener {

        void getSelectedDates(String selectedDate);
    }
}
