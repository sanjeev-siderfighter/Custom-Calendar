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
import com.example.customcalendarlibrary.Util.Toaster;
import com.example.customcalendarlibrary.databinding.CalendarRecyclerViewLayoutBinding;

import java.util.Calendar;
import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CalendarRecyclerViewLayoutBinding binding;
    private Context mContext;

    private List<String> yearList;
    private List<CalendarDates> monthList;

    private static class CalendarViewHolder extends RecyclerView.ViewHolder {

        public CalendarRecyclerViewLayoutBinding binding;

        CalendarViewHolder(@NonNull CalendarRecyclerViewLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setDatesGridAdapter(CalendarDates month, String year) {

            binding.monthText.setText(month.getMonthName());
            binding.yearText.setText(year);

            binding.datesGrid.setHasFixedSize(false);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(binding.getRoot().getContext(), 7);
            binding.datesGrid.setLayoutManager(layoutManager);
            DatesGridAdapter adapter = new DatesGridAdapter(binding.getRoot().getContext(), month, year);
            binding.datesGrid.setAdapter(adapter);

            binding.executePendingBindings();
        }

        /*public void setDatesGridAdapter(String month, String year) {
            binding.monthText.setText(month);
            binding.yearText.setText(year);

            binding.datesGrid.setHasFixedSize(false);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(binding.getRoot().getContext(), 7);
            binding.datesGrid.setLayoutManager(layoutManager);
            DatesGridAdapter adapter = new DatesGridAdapter(binding.getRoot().getContext(), month, year);
            binding.datesGrid.setAdapter(adapter);

            binding.executePendingBindings();
        }*/
    }

    public CalendarRecyclerAdapter(Context context, List<CalendarDates> monthList, List<String> yearList) {

        mContext = context;
        this.monthList = monthList;
        this.yearList = yearList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {

            binding = DataBindingUtil.inflate(inflater, R.layout.calendar_recycler_view_layout, parent, false);
        } else {

            Toaster.generateShortToast(mContext, mContext.getResources().getString(R.string.layout_unavailable));
        }

        return new CalendarViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CalendarViewHolder viewHolder = (CalendarViewHolder) holder;
        viewHolder.setDatesGridAdapter(monthList.get(position % 12), yearList.get(position / 12));
    }

    @Override
    public int getItemCount() {
        return yearList.size() * 12;
    }

    /*private void setDatesGridAdapter(CalendarViewHolder viewHolder, String month, String year) {

//        DatesGridAdapter adapter = new DatesGridAdapter(mContext, month, year);
//        viewHolder.datesGrid.setAdapter(adapter);
        viewHolder..setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 7);
        viewHolder.datesGrid.setLayoutManager(layoutManager);
        DatesGridAdapter adapter = new DatesGridAdapter(mContext, month, year);
        viewHolder.datesGrid.setAdapter(adapter);
    }*/
}
