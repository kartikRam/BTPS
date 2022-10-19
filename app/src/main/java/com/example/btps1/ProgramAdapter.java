package com.example.btps1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.startActivity;


public class ProgramAdapter extends ArrayAdapter<String> {
    Context context;
    int[] images;
    int i=0;
    String[] cityname;
    String[] cityDescription;
    String[] data=new String[20];
    clicked ob1;

    public ProgramAdapter(@NonNull Context context,clicked ob1,String[] cityName,int[] images,String[] cityDescription) {
        super(context,R.layout.single_item,R.id.citytitle,cityName);
        this.context=context;
        this.cityname=cityName;
        this.images=images;
        this.cityDescription=cityDescription;
        this.ob1=ob1;

    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View Singleitem=convertView;
        ProgramViewHolder holder=null;
        if(Singleitem==null){
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Singleitem=layoutInflater.inflate(R.layout.single_item,parent,false);
            holder=new ProgramViewHolder(Singleitem);
            Singleitem.setTag(holder);
        }
        else{
            holder=(ProgramViewHolder)Singleitem.getTag();
        }
        holder.itemImage.setImageResource(images[position]);
        holder.cityname.setText(cityname[position]);
        holder.cityDescription.setText(cityDescription[position]);
        Singleitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(),"Selected:"+cityDescription[position],Toast.LENGTH_SHORT).show();
                data[i]=cityDescription[position];
                return false;
            }

    });

        Singleitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ob1.selected(position);
            }
        });
        return Singleitem;
    }

    public interface clicked{
        public void selected(int position);
    }
}

