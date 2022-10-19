package com.example.btps1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class ProgramAdapter1 extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] cityname;
    String[] cityDescription;
    get_destination ob1;
    public ProgramAdapter1(@NonNull Context context,get_destination ob1,String[] cityName,int[] images,String[] cityDescription) {
        super(context,R.layout.single_item,R.id.citytitle,cityName);
        this.context=context;
        this.cityname=cityName;
        this.images=images;
        this.cityDescription=cityDescription;
        this.ob1=ob1;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        Singleitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ob1.selected(position);
            }
        });
        return Singleitem;
    }
    public interface get_destination{
        public void selected(int position);
    }
}
