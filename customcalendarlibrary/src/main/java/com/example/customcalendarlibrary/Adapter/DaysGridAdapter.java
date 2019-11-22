package com.example.customcalendarlibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.example.customcalendarlibrary.R;
import com.example.customcalendarlibrary.databinding.DaysGridItemsBinding;

import java.util.Arrays;
import java.util.List;

public class DaysGridAdapter extends BaseAdapter {

    private Context mContext;
    private DaysGridItemsBinding binding;

    private static final List<String> daysList;
    private static LayoutInflater inflater;

    static {
        daysList = Arrays.asList("S", "M", "T", "W", "T", "F", "S");
        inflater = null;
    }

    public DaysGridAdapter(Context context) {
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

            Holder holder = new Holder();

//            binding = DataBindingUtil.inflate(inflater, R.layout.days_grid_items, null, false);
//            convertView = binding.getRoot();

//            holder.dayText = binding.dayText;
//            holder.dayText.setText(daysList.get(position));

            convertView = inflater.inflate(R.layout.days_grid_items, null);
            holder.dayText = convertView.findViewById(R.id.day_text);
            holder.dayText.setText(daysList.get(position));
        }

        return convertView;
    }

    private class Holder {

        TextView dayText;
    }
}
