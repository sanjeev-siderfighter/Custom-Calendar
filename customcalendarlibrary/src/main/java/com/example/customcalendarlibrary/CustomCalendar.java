package com.example.customcalendarlibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customcalendarlibrary.Adapter.CalendarRecyclerAdapter;
import com.example.customcalendarlibrary.Adapter.DaysGridAdapter;
import com.example.customcalendarlibrary.Util.Toaster;
import com.example.customcalendarlibrary.databinding.CalendarRecyclerViewLayoutBinding;
import com.example.customcalendarlibrary.databinding.CustomCalendarHomeLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class CustomCalendar extends View {

    private Context mContext;
    private Activity mActivity;

    private CustomCalendarHomeLayoutBinding binding;

    public CustomCalendar(Context context) {
        super(context);
        mContext = context;
        mActivity = (Activity) mContext;
        initView(null);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mActivity = (Activity) mContext;
        initView(attrs);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mActivity = (Activity) mContext;
        initView(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mActivity = (Activity) mContext;
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {

            binding = DataBindingUtil.setContentView(mActivity, R.layout.custom_calendar_home_layout);

            setupGridView();
            setupRecyclerView();
        } else {

            Toaster.generateShortToast(mContext, getResources().getString(R.string.layout_unavailable));
        }
    }

    private void setupGridView() {

        GridView daysGrid = binding.gridviewForDays;
        DaysGridAdapter adapter = new DaysGridAdapter(mContext);
        daysGrid.setAdapter(adapter);
    }

    private void setupRecyclerView() {

        RecyclerView calendarRecyclerView = binding.recyclerviewForDates;

        List<String> months = new ArrayList<>();
        List<String> years = new ArrayList<>();

        for(int i = 0; i < 12; i++) {
            months.add("JAN");
            years.add("2019");
        }

        calendarRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        calendarRecyclerView.setLayoutManager(layoutManager);

        CalendarRecyclerAdapter adapter = new CalendarRecyclerAdapter(mContext, months, years);
        calendarRecyclerView.setAdapter(adapter);
    }
}
