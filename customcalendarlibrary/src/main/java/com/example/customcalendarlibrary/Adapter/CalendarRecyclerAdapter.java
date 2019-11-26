package com.example.customcalendarlibrary.Adapter;

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
import com.example.customcalendarlibrary.Util.WeekDays;
import com.example.customcalendarlibrary.databinding.CalendarRecyclerViewLayoutBinding;
import com.example.customcalendarlibrary.databinding.DatesGridRecyclerViewLayoutBinding;

import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<String> yearList;
    private List<CalendarDates> monthList;
    private WeekDays startDayPosition;

    private static final int MONTH_YEAR_VIEW_TYPE = 0; // use for even positions
    private static final int DATES_VIEW_TYPE = 1; // use for odd positions

    private static class CalendarViewHolder extends RecyclerView.ViewHolder {

        private CalendarRecyclerViewLayoutBinding monthYearBinding;

        CalendarViewHolder(@NonNull CalendarRecyclerViewLayoutBinding monthYearBinding) {

            super(monthYearBinding.getRoot());
            this.monthYearBinding = monthYearBinding;
//            setClickListeners();
        }

        void setContent(CalendarDates month, String year) {

            monthYearBinding.monthText.setText(month.getMonthName());
            monthYearBinding.yearText.setText(year);
        }
    }

    private static class DatesViewHolder extends RecyclerView.ViewHolder {

        private DatesGridRecyclerViewLayoutBinding datesBinding;

        DatesViewHolder(@NonNull DatesGridRecyclerViewLayoutBinding datesBinding) {

            super(datesBinding.getRoot());
            this.datesBinding = datesBinding;
        }

        void setContent(CalendarDates month, String year) {

            datesBinding.datesGrid.setHasFixedSize(false);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(datesBinding.getRoot().getContext(), CalendarUtil.NUMBER_OF_DAYS_IN_A_WEEK);
            datesBinding.datesGrid.setLayoutManager(layoutManager);
            DatesGridAdapter adapter = new DatesGridAdapter(datesBinding.getRoot().getContext(), month, year);
            datesBinding.datesGrid.setAdapter(adapter);

            datesBinding.executePendingBindings();
        }
    }

    public CalendarRecyclerAdapter(Context context, List<CalendarDates> monthList, List<String> yearList) {

        mContext = context;
        this.monthList = monthList;
        this.yearList = yearList;
        this.startDayPosition = WeekDays.SUNDAY; // todo: get it from epoch...
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
            viewHolder.setContent(monthText, yearText);
        }
    }

    @Override
    public int getItemCount() {
        return yearList.size() * monthList.size() * 2;
    }

    @Override
    public int getItemViewType(int position) {

        if ((position & 1) == 0) {

            return MONTH_YEAR_VIEW_TYPE;
        } else {

            return DATES_VIEW_TYPE;
        }
    }
}
