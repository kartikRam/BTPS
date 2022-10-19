package com.example.btps1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder1 {
    TextView bus_time;
    TextView bus_name;
    TextView bus_number;

    ProgramViewHolder1(View v) {
        bus_time = v.findViewById(R.id.bus_time);
        bus_name = v.findViewById(R.id.bus_name);
        bus_number = v.findViewById(R.id.bus_number);
    }
}
