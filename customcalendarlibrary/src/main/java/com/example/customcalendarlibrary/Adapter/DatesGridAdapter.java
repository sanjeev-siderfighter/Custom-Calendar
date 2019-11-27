package com.example.customcalendarlibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customcalendarlibrary.R;
import com.example.customcalendarlibrary.Util.CalendarDates;
import com.example.customcalendarlibrary.Util.CalendarUtil;
import com.example.customcalendarlibrary.Util.DateCommunicatorWithCalendar;
import com.example.customcalendarlibrary.Util.Toaster;
import com.example.customcalendarlibrary.databinding.DaysGridItemsBinding;

import java.util.ArrayList;
import java.util.List;

public class DatesGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> dates = new ArrayList<>();

    private Context mContext;
    private DaysGridItemsBinding binding;

    private int monthYearPosition;
    private static DateCommunicatorWithCalendar communicatorWithCalendar;

    DatesGridAdapter(Context context, CalendarDates month, String year, int position, Object communicator) {

        mContext = context;
        monthYearPosition = position;
        dates.clear();
        communicatorWithCalendar = (DateCommunicatorWithCalendar) communicator;

        int monthSize = month.getNumberOfDays(Integer.valueOf(year));

        for (int i = 0; i < monthSize; i++) {
            dates.add(i + 1);
        }
    }

    private static class DatesGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private DaysGridItemsBinding binding;

        private int monthYearPosition;
        private int selectedDatePosition;

        DatesGridViewHolder(@NonNull DaysGridItemsBinding binding, int monthYearPosition) {

            super(binding.getRoot());
            this.binding = binding;
            this.monthYearPosition = monthYearPosition;

            selectedDatePosition = CalendarUtil.INITIAL_VALUE;
            setEvents();
        }

        void setContent(String text) {

            binding.dayText.setText(text);
        }

        private void setEvents() {

            binding.dayText.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view == binding.dayText) {
                selectedDatePosition = getAdapterPosition();
                communicatorWithCalendar.getSelectedDatePosition(monthYearPosition, selectedDatePosition);
            }
            /*if (selectedDatePosition == CalendarUtil.INITIAL_VALUE) {
                selectedDatePosition = getAdapterPosition();
                Toaster.generateShortToast(view.getContext(), "From " + selectedDatePosition);
            } else {
                dateEndPosition = getAdapterPosition();
                Toaster.generateShortToast(view.getContext(), "From " + selectedDatePosition + " To " + dateEndPosition);
                selectedDatePosition = CalendarUtil.INITIAL_VALUE;
            }*/
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.days_grid_items, parent, false);
        } else {

            Toaster.generateShortToast(mContext, mContext.getResources().getString(R.string.layout_unavailable));
        }

        return new DatesGridViewHolder(binding, monthYearPosition);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DatesGridViewHolder viewHolder = (DatesGridViewHolder) holder;
        viewHolder.setContent(String.valueOf(dates.get(position)));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }
}
