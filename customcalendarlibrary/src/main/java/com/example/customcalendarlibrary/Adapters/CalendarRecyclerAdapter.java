package com.example.customcalendarlibrary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customcalendarlibrary.R;
import com.example.customcalendarlibrary.Util.CalendarDates;
import com.example.customcalendarlibrary.Util.CalendarUtil;
import com.example.customcalendarlibrary.Util.DateCommunicatorWithCalendar;
import com.example.customcalendarlibrary.Util.SelectedDate;
import com.example.customcalendarlibrary.databinding.CalendarRecyclerViewLayoutBinding;
import com.example.customcalendarlibrary.databinding.DatesGridRecyclerViewLayoutBinding;

import java.util.Collections;
import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<String> yearList;
    private List<CalendarDates> monthList;

    private DatesViewHolder datesViewHolder;

    private static int minMonthYearPosition = -1, minDayPosition = -1;

    private static final int MONTH_YEAR_VIEW_TYPE = 0; // use for even positions
    private static final int DATES_VIEW_TYPE = 1; // use for odd positions

    public CalendarRecyclerAdapter(Context context, List<CalendarDates> monthList, List<String> yearList) {

        mContext = context;
        this.monthList = monthList;
        this.yearList = yearList;
    }

    private static class CalendarViewHolder extends RecyclerView.ViewHolder {


        private CalendarRecyclerViewLayoutBinding monthYearBinding;

        CalendarViewHolder(@NonNull CalendarRecyclerViewLayoutBinding monthYearBinding) {

            super(monthYearBinding.getRoot());
            this.monthYearBinding = monthYearBinding;
        }

        void setContent(CalendarDates month, String year) {

            monthYearBinding.monthText.setText(month.getMonthName());
            monthYearBinding.yearText.setText(year);
        }
    }
    private class DatesViewHolder extends RecyclerView.ViewHolder implements DateCommunicatorWithCalendar {


        private DatesGridRecyclerViewLayoutBinding datesBinding;
        private DatesGridAdapter adapter;

        DatesViewHolder(@NonNull DatesGridRecyclerViewLayoutBinding datesBinding) {

            super(datesBinding.getRoot());
            this.datesBinding = datesBinding;
        }

        void setContent(int position, CalendarDates month, String year) {

            datesBinding.datesGrid.setHasFixedSize(false);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(datesBinding.getRoot().getContext(), CalendarUtil.NUMBER_OF_DAYS_IN_A_WEEK);
            datesBinding.datesGrid.setLayoutManager(layoutManager);
            adapter = new DatesGridAdapter(datesBinding.getRoot().getContext(), month, year, position, this);
            datesBinding.datesGrid.setAdapter(adapter);

            datesBinding.executePendingBindings();
        }

        SelectedDate getSelectedDate() {
            return adapter.getSelectedDate();
        }

        @Override
        public void notifyChange() {

            notifyDataSetChanged();
        }

        void disableDates(int monthYearPosition, int dayPosition) {
            adapter.disableDates(monthYearPosition, dayPosition);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        if (viewType == MONTH_YEAR_VIEW_TYPE) {

            CalendarRecyclerViewLayoutBinding monthYearBinding = DataBindingUtil.inflate(inflater,
                    R.layout.calendar_recycler_view_layout, parent, false);
            return new CalendarViewHolder(monthYearBinding);
        } else { // (DATES_VIEW_TYPE)

            DatesGridRecyclerViewLayoutBinding datesBinding = DataBindingUtil.inflate(inflater,
                    R.layout.dates_grid_recycler_view_layout, parent, false);
            return new DatesViewHolder(datesBinding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String yearText;
        CalendarDates monthText;

        int pos = position / 2;
        int yearPos = pos / CalendarUtil.NUMBER_OF_MONTHS, monthPos = pos % CalendarUtil.NUMBER_OF_MONTHS;
        yearText = yearList.get(yearPos);
        monthText = monthList.get(monthPos);

        if (getItemViewType(position) == MONTH_YEAR_VIEW_TYPE) { // position == even

            CalendarViewHolder viewHolder = (CalendarViewHolder) holder;

            if (yearPos > 0) {
                changeVisibility(viewHolder.monthYearBinding.yearMonthPart, View.VISIBLE);
                viewHolder.setContent(monthText, yearText);
            } else {

                if (monthPos >= minMonthYearPosition % 12) {
                    changeVisibility(viewHolder.monthYearBinding.yearMonthPart, View.VISIBLE);
                    viewHolder.setContent(monthText, yearText);
                } else {
                    changeVisibility(viewHolder.monthYearBinding.yearMonthPart, View.GONE);
                }
            }
        } else { // position == odd

            datesViewHolder = (DatesViewHolder) holder;

            if (yearPos > 0) {
                datesViewHolder.setContent(pos, monthText, yearText);
                datesViewHolder.disableDates(minMonthYearPosition, minDayPosition);
            } else {

                if (monthPos >= minMonthYearPosition % 12) {
                    changeVisibility(datesViewHolder.datesBinding.datesGrid, View.VISIBLE);
                    datesViewHolder.setContent(pos, monthText, yearText);
                    datesViewHolder.disableDates(minMonthYearPosition, minDayPosition);
                } else {
                    changeVisibility(datesViewHolder.datesBinding.datesGrid, View.GONE);
                }
            }
        }
    }

    /**
     * Half elements are Month/year part and half elements are dates
     *
     * @return yearList.size() * monthList.size() will only return half of the elements. So multiply by 2.
     */
    @Override
    public int getItemCount() {
        return yearList.size() * monthList.size() * 2;
    }

    @Override
    public int getItemViewType(int position) {

        if ((position & 1) == 0) { // even

            return MONTH_YEAR_VIEW_TYPE;
        } else { // odd

            return DATES_VIEW_TYPE;
        }
    }

    /**
     * Changes the date text color to somewhat grayscale... there is no click action for dates less than this...
     * @param monthYearPosition position of the month-year pair in the list
     * @param dayPosition position of the day in the grid.
     */
    public void disableDates(int monthYearPosition, int dayPosition) {

        minMonthYearPosition = monthYearPosition;
        minDayPosition = dayPosition;

        yearList = yearList.subList(minMonthYearPosition / 12, yearList.size());
    }

    public SelectedDate getSelectedDates() {

        return datesViewHolder.getSelectedDate();
    }

    private void changeVisibility(View view, int visibility) {
        view.setVisibility(visibility);
    }
}
