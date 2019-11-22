package com.example.customcalendarlibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customcalendarlibrary.WrappedGridView;
import com.example.customcalendarlibrary.R;
import com.example.customcalendarlibrary.Util.Toaster;
import com.example.customcalendarlibrary.databinding.CalendarRecyclerViewLayoutBinding;

import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CalendarRecyclerViewLayoutBinding binding;
    private Context mContext;

    private List<String> monthList, yearList;

    private static class CalendarViewHolder extends RecyclerView.ViewHolder {

        TextView monthText, yearText;
//        GridView datesGrid;
        WrappedGridView datesGrid;

        CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            monthText = itemView.findViewById(R.id.month_text);
            yearText = itemView.findViewById(R.id.year_text);
            datesGrid = itemView.findViewById(R.id.dates_grid);
        }
    }

    public CalendarRecyclerAdapter(Context context, List<String> monthList, List<String> yearList) {

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

        return new CalendarViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CalendarViewHolder viewHolder = (CalendarViewHolder) holder;
        viewHolder.monthText.setText(monthList.get(position));
        viewHolder.yearText.setText(yearList.get(position));

        setDatesGridAdapter(viewHolder, monthList.get(position), yearList.get(position));
    }

    @Override
    public int getItemCount() {
        return monthList.size(); // should be yearList.size()...
    }

    private void setDatesGridAdapter(CalendarViewHolder viewHolder, String month, String year) {

        month = "JAN";
        year = "2019";

        DatesGridAdapter adapter = new DatesGridAdapter(mContext, month, year);
        viewHolder.datesGrid.setAdapter(adapter);
    }
}
