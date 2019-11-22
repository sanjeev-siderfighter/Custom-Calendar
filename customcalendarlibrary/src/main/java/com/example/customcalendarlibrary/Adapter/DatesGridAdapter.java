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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DatesGridAdapter extends BaseAdapter {

    private List<Integer> dates = new ArrayList<>();

    private static LayoutInflater inflater = null;
    private Context mContext;
    private DaysGridItemsBinding binding;

    public DatesGridAdapter(Context context, String month, String year) {

        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dates.clear();
        if (month.equalsIgnoreCase("JAN")) {
            for (int i = 0; i < 31; i++) {
                dates.add(i + 1);
            }
        }
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.days_grid_items, parent, false);
            convertView = binding.getRoot();

            Holder holder = new Holder();
            holder.dateText = binding.dayText;
            holder.dateText.setText(String.valueOf(dates.get(position)));
        }

        return convertView;
    }

    private class Holder {
        TextView dateText;
    }
}
