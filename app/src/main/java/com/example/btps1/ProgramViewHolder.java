package com.example.btps1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder {
    ImageView itemImage;
    TextView cityname;
    ImageView remove;
    TextView cityDescription;

    ProgramViewHolder(View v){
        itemImage=v.findViewById(R.id.imagecity);
        cityname=v.findViewById(R.id.citytitle);
        cityDescription=v.findViewById(R.id.cityDescription);
        remove=v.findViewById(R.id.remove);
    }
}
