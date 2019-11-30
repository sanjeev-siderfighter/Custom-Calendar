package com.example.customcalendarlibrary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customcalendarlibrary.R;
import com.example.customcalendarlibrary.Util.CalendarDates;
import com.example.customcalendarlibrary.Util.CalendarUtil;
import com.example.customcalendarlibrary.Util.DateCommunicatorWithCalendar;
import com.example.customcalendarlibrary.Util.DateHighlightPositionStore;
import com.example.customcalendarlibrary.Util.Toaster;
import com.example.customcalendarlibrary.databinding.DaysGridItemsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class DatesGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> dates = new ArrayList<>();

    private Context mContext;
    private DaysGridItemsBinding binding;

    private int monthYearPosition;
    private int startPositionForDayOfMonth;
    private static DateCommunicatorWithCalendar communicatorWithCalendar;

    private static int startMonthPos = -1, endMonthPos = -1, startDatePos = -1, endDatePos = -1;

    static {
        communicatorWithCalendar = null;
    }

    DatesGridAdapter(Context context, CalendarDates month, String year, int position, Object communicator) {

        mContext = context;
        monthYearPosition = position;
        communicatorWithCalendar = (DateCommunicatorWithCalendar) communicator;

        GregorianCalendar calendar = new GregorianCalendar(Integer.valueOf(year), month.getValue(), 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale.US);
        startPositionForDayOfMonth = CalendarUtil.dayPosition(dateFormat.format(calendar.getTimeInMillis()));

        dates.clear();
        int monthSize = month.getNumberOfDays(Integer.valueOf(year));
        for (int i = 0; i < monthSize; i++) {
            dates.add(i + 1);
        }
    }

    private class DatesGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private DaysGridItemsBinding binding;

        private int monthYearPosition;

        DatesGridViewHolder(@NonNull DaysGridItemsBinding binding, int monthYearPosition) {

            super(binding.getRoot());
            this.binding = binding;
            this.monthYearPosition = monthYearPosition;

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

            if (view == binding.dayText && !binding.dayText.getText().toString().equalsIgnoreCase(CalendarUtil.BLANK_TEXT)) {

                if (DateHighlightPositionStore.startMonth == CalendarUtil.INITIAL_VALUE) {
                    DateHighlightPositionStore.startMonth = monthYearPosition;
                    DateHighlightPositionStore.startDate = getAdapterPosition();

                } else {
                    DateHighlightPositionStore.endMonth = monthYearPosition;
                    DateHighlightPositionStore.endDate = getAdapterPosition();
                }

                startMonthPos = DateHighlightPositionStore.startMonth;
                endMonthPos = DateHighlightPositionStore.endMonth;
                startDatePos = DateHighlightPositionStore.startDate;
                endDatePos = DateHighlightPositionStore.endDate;

                if ((endMonthPos == startMonthPos && endDatePos <= startDatePos) || (endMonthPos < startMonthPos)) {
                    endMonthPos = endDatePos = CalendarUtil.INITIAL_VALUE;
                    DateHighlightPositionStore.startMonth = monthYearPosition;
                    DateHighlightPositionStore.startDate = getAdapterPosition();
                    startMonthPos = DateHighlightPositionStore.startMonth;
                    startDatePos = DateHighlightPositionStore.startDate;
                }

                highlight();
                communicatorWithCalendar.notifyChange();
            }
        }

        void highlight() {

            if (startMonthPos != -1) {
                if (endMonthPos == -1) {
                    setBackground(startMonthPos, startDatePos);
                } else {
                    setBackground(startMonthPos, endMonthPos, startDatePos, endDatePos);
                    DateHighlightPositionStore.startMonth = DateHighlightPositionStore.endMonth = DateHighlightPositionStore.startDate = DateHighlightPositionStore.endDate = CalendarUtil.INITIAL_VALUE;
                }
            }
        }

        private void setBackground(int startMonthPos, int startDatePos) {
            if (monthYearPosition == startMonthPos && getAdapterPosition() == startDatePos) {
                if (!binding.dayText.getText().toString().equalsIgnoreCase(CalendarUtil.BLANK_TEXT)) {
                    binding.dayText.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.highlight_color));
                }
            }
        }

        private void setBackground(int startMonthPos, int endMonthPos, int startDatePos, int endDatePos) {
            if (monthYearPosition == startMonthPos && monthYearPosition == endMonthPos) {
                if (getAdapterPosition() >= startDatePos && getAdapterPosition() <= endDatePos) {
                    if (!binding.dayText.getText().toString().equalsIgnoreCase(CalendarUtil.BLANK_TEXT)) {
                        binding.dayText.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.highlight_color));
                    }
                }
            } else if (startMonthPos < endMonthPos) {
                if (monthYearPosition == startMonthPos) {
                    if (getAdapterPosition() >= startDatePos) {
                        if (!binding.dayText.getText().toString().equalsIgnoreCase(CalendarUtil.BLANK_TEXT)) {
                            binding.dayText.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.highlight_color));
                        }
                    }
                }
                if (monthYearPosition > startMonthPos && monthYearPosition < endMonthPos) {
                    if (!binding.dayText.getText().toString().equalsIgnoreCase(CalendarUtil.BLANK_TEXT)) {
                        binding.dayText.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.highlight_color));
                    }
                }
                if (monthYearPosition == endMonthPos) {
                    if (getAdapterPosition() <= endDatePos) {
                        if (!binding.dayText.getText().toString().equalsIgnoreCase(CalendarUtil.BLANK_TEXT)) {
                            binding.dayText.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.highlight_color));
                        }
                    }
                }
            }
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
        if (position >= startPositionForDayOfMonth) {
            viewHolder.setContent(String.valueOf(dates.get(position - startPositionForDayOfMonth)));
        } else {
            viewHolder.setContent(CalendarUtil.BLANK_TEXT);
        }

        viewHolder.highlight();
    }

    @Override
    public int getItemCount() {
        return dates.size() + startPositionForDayOfMonth;
    }
}
