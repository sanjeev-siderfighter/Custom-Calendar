package com.example.customcalendarlibrary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.customcalendarlibrary.R;
import com.example.customcalendarlibrary.databinding.DaysGridItemsBinding;

import java.util.Arrays;
import java.util.List;

public class DaysGridAdapter extends BaseAdapter {

    private static final List<String> daysList;
    private static LayoutInflater inflater;

    static {
        daysList = Arrays.asList("S", "M", "T", "W", "T", "F", "S");
        inflater = null;
    }

    public DaysGridAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return daysList.size();
    }

    @Override
    public Object getItem(int position) {
        return daysList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            DaysGridItemsBinding binding = DataBindingUtil.inflate(inflater, R.layout.days_grid_items, null, false);
            binding.dayText.setText(daysList.get(position));
            convertView = binding.getRoot();
        }

        return convertView;
    }
}
