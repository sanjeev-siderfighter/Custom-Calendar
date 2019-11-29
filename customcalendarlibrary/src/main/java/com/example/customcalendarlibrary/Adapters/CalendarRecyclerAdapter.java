package com.example.customcalendarlibrary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customcalendarlibrary.R;
import com.example.customcalendarlibrary.Util.CalendarDates;
import com.example.customcalendarlibrary.Util.CalendarUtil;
import com.example.customcalendarlibrary.Util.DateCommunicatorWithCalendar;
import com.example.customcalendarlibrary.Util.DateHighlightPositionStore;
import com.example.customcalendarlibrary.Util.Toaster;
import com.example.customcalendarlibrary.databinding.CalendarRecyclerViewLayoutBinding;
import com.example.customcalendarlibrary.databinding.DatesGridRecyclerViewLayoutBinding;

import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<String> yearList;
    private List<CalendarDates> monthList;

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

        @Override
        public void getSelectedDatePosition(int monthYearPosition, int dateStartPosition) {

        }

        @Override
        public void notifyChange() {

            notifyDataSetChanged();
//            DateHighlightPositionStore.startMonth = DateHighlightPositionStore.endMonth =
//                    DateHighlightPositionStore.startDate = DateHighlightPositionStore.endDate = CalendarUtil.INITIAL_VALUE;
//            DatesGridAdapter.startMonthPos = DatesGridAdapter.endMonthPos = DatesGridAdapter.startDatePos = DatesGridAdapter.endDatePos = -1;
        }

        /*@Override
        public void getSelectedDatePosition(int month, int date) {

            caseSameMonths(month, date);

            *//*Context context = datesBinding.getRoot().getContext();

            if (startMonth == CalendarUtil.INITIAL_VALUE) {
                startMonth = month;
                startDate = date;
                Toaster.generateShortToast(context, "From " + startDate);
            } else if (month < startMonth) {
                startMonth = month;
                endMonth = CalendarUtil.INITIAL_VALUE;
                startDate = date;
                endDate = CalendarUtil.INITIAL_VALUE;
                Toaster.generateShortToast(context, "From " + startDate);
            } else if (month > startMonth && endMonth == CalendarUtil.INITIAL_VALUE) {
                endMonth = month;
                endDate = date;
                Toaster.generateShortToast(context, "From " + startDate + " To " + endDate);
            } else if (month > startMonth && month < endMonth) { // if (month > startMonth && month == endMonth), if (month == startMonth && month < endMonth)
                endMonth = month;
                endDate = date;
                Toaster.generateShortToast(context, "From " + startDate + " To " + endDate);
            } else if (endMonth != CalendarUtil.INITIAL_VALUE && month > endMonth) {
                startMonth = month;
                endMonth = CalendarUtil.INITIAL_VALUE;
                startDate = date;
                endDate = CalendarUtil.INITIAL_VALUE;
                Toaster.generateShortToast(context, "From " + startDate);
            } else if (month == startMonth) {
                if (month == endMonth) {
                    caseSameMonths(date);
                } else if (month > endMonth) {

                } else {

                }
                if (date < startDate || date > startDate) {
//                    endMonth = month;
                    startDate = date;
                    endDate = CalendarUtil.INITIAL_VALUE;
                    Toaster.generateShortToast(context, "From " + startDate);
                }
            } else if (month == endMonth && month != startMonth) {
                if (date > endDate) {
                    startMonth = month;
                    startDate = date;
                    endDate = CalendarUtil.INITIAL_VALUE;
                    Toaster.generateShortToast(context, "From " + startDate);
                } else if (date < endDate) {
                    endDate = date;
                    Toaster.generateShortToast(context, "From " + startDate + " To " + endDate);
                }
            } else*//**//* if (month == startMonth && month == endMonth)*//**//* {
                caseSameMonths(date);
            }*//*
        }

        private void caseSameMonths(int month, int date) {

            Context context = datesBinding.getRoot().getContext();

            *//*if (startDate == CalendarUtil.INITIAL_VALUE) {
                startDate = date;
                Toaster.generateShortToast(context, "From " + startDate);
            } else if (date < startDate) {
                startDate = date;
                endDate = CalendarUtil.INITIAL_VALUE;
                Toaster.generateShortToast(context, "From " + startDate);
            } else if (date > startDate && endDate == CalendarUtil.INITIAL_VALUE) {
                endDate = date;
                Toaster.generateShortToast(context, "From " + startDate + " To " + endDate);
            } else if (date > startDate && date < endDate) {
                endDate = date;
                Toaster.generateShortToast(context, "From " + startDate + " To " + endDate);
            } else if (date > endDate) {
                startDate = date;
                endDate = CalendarUtil.INITIAL_VALUE;
                Toaster.generateShortToast(context, "From " + startDate);
            } else {
                Toaster.generateShortToast(context, "Check for more cases!!");
            }

            adapter.highlight(month, startDate, endDate);*//*
        }*/

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        if (viewType == MONTH_YEAR_VIEW_TYPE) {

            CalendarRecyclerViewLayoutBinding monthYearBinding = DataBindingUtil.inflate(inflater, R.layout.calendar_recycler_view_layout, parent, false);
            return new CalendarViewHolder(monthYearBinding);
        } else { // (DATES_VIEW_TYPE)

            DatesGridRecyclerViewLayoutBinding datesBinding = DataBindingUtil.inflate(inflater, R.layout.dates_grid_recycler_view_layout, parent, false);
            return new DatesViewHolder(datesBinding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String yearText;
        CalendarDates monthText;
        int pos = position / 2;
        yearText = yearList.get(pos / CalendarUtil.NUMBER_OF_MONTHS);
        monthText = monthList.get(pos % CalendarUtil.NUMBER_OF_MONTHS);

        if (getItemViewType(position) == MONTH_YEAR_VIEW_TYPE) { // position == even

            CalendarViewHolder viewHolder = (CalendarViewHolder) holder;
            viewHolder.setContent(monthText, yearText);
        } else { // position == odd

            DatesViewHolder viewHolder = (DatesViewHolder) holder;
            viewHolder.setContent(pos, monthText, yearText);
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
}
