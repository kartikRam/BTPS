package com.example.btps1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] months;
    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, String[] months) {
        this.context = applicationContext;
        this.months = months;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return months.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.spinner_layout, null);
        TextView names = (TextView) convertView.findViewById(R.id.months);
        names.setText(months[position]);
        return convertView;
    }
}
