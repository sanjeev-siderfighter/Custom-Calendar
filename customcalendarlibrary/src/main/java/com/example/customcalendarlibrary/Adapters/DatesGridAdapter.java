package com.example.customcalendarlibrary.Adapters;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class DatesGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> dates = new ArrayList<>();

    private Context mContext;
    private DaysGridItemsBinding binding;
    private DatesGridViewHolder viewHolder;

    private int monthYearPosition;
    private int startPosition;
    private static DateCommunicatorWithCalendar communicatorWithCalendar;

    private int start, end, monthPos;

    static {
        communicatorWithCalendar = null;
//        start = CalendarUtil.INITIAL_VALUE;
//        end = CalendarUtil.INITIAL_VALUE;
    }

    DatesGridAdapter(Context context, CalendarDates month, String year, int position, Object communicator) {

        mContext = context;
        monthYearPosition = position;
        dates.clear();
        communicatorWithCalendar = (DateCommunicatorWithCalendar) communicator;

        GregorianCalendar calendar = new GregorianCalendar(Integer.valueOf(year), month.getValue(), 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale.US);
        startPosition = CalendarUtil.dayPosition(dateFormat.format(calendar.getTimeInMillis()));

        monthPos = start = end = CalendarUtil.INITIAL_VALUE;

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
//            this.monthYearPosition = monthYearPosition;
            this.monthYearPosition = getAdapterPosition() - 1;

            selectedDatePosition = CalendarUtil.INITIAL_VALUE;
            setEvents();
        }

        void setContent(String text, int start, int end) {

            binding.dayText.setText(text);
            getHighLightPositions(start, end);
        }

        void getHighLightPositions(int start, int end) {
            int position = getAdapterPosition();
            if (position == start) {
                binding.dayText.setBackgroundColor(binding.getRoot().getContext().getResources().getColor(R.color.highlight_color));
            }
            if (position == end) {
                binding.dayText.setBackgroundColor(binding.getRoot().getContext().getResources().getColor(R.color.highlight_color));
            }
            if (position > start && position < end) {
                binding.dayText.setBackgroundColor(binding.getRoot().getContext().getResources().getColor(R.color.highlight_color));
            }
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

        viewHolder = (DatesGridViewHolder) holder;
        if (position >= startPosition) {
            viewHolder.setContent(String.valueOf(dates.get(position - startPosition)), start, end);
        } else {
            viewHolder.setContent(" ", -1, -1);
        }
    }

    @Override
    public int getItemCount() {
        return dates.size() + startPosition;
    }

    void highlight(int monthPos, int startPos, int endPos) {

        this.monthPos = monthPos;
        start = startPos;
        end = endPos;

        notifyDataSetChanged();
//        notifyItemRangeChanged(start, end - start + 1);
    }
}
