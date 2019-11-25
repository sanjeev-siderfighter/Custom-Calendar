package com.example.customcalendarlibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customcalendarlibrary.R;
import com.example.customcalendarlibrary.Util.CalendarDates;
import com.example.customcalendarlibrary.Util.Toaster;
import com.example.customcalendarlibrary.databinding.DaysGridItemsBinding;

import java.util.ArrayList;
import java.util.List;

public class DatesGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> dates = new ArrayList<>();

    private Context mContext;
    private DaysGridItemsBinding binding;

    DatesGridAdapter(Context context, CalendarDates month, String year) {

        mContext = context;
        dates.clear();

        int monthSize = month.getNumberOfDays(Integer.valueOf(year));

        for (int i = 0; i < monthSize; i++) {
            dates.add(i + 1);
        }
    }

    private static class DatesGridViewHolder extends RecyclerView.ViewHolder {

        TextView dateText;

        public DatesGridViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.day_text);

            // set on click listener here...
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

        return new DatesGridViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DatesGridViewHolder viewHolder = (DatesGridViewHolder) holder;
        viewHolder.dateText.setText(String.valueOf(dates.get(position)));
//        viewHolder.dateText.setGravity(Gravity.CENTER);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }
}
