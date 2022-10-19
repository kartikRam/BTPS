package com.example.btps1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class Hire_Bus_View_Adapter extends RecyclerView.Adapter<Hire_Bus_View_Adapter.ViewHolder> {

    Context context;
    String[] bus_number ;
    int rupees ;
    static getItemSelected ob1;
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView bus_number,rupees;
        CardView bus;
        public ViewHolder(@NonNull View v) {
            super(v);
            bus_number = v.findViewById(R.id.bus_number_hire_bus);
            rupees=v.findViewById(R.id.bus_rupees_hire);
            bus=v.findViewById(R.id.buses_hire);
        }

        @Override
        public void onClick(View v) {
               // ob1.getItem(this.getPosition());
        }
    }

    public Hire_Bus_View_Adapter(Context context, getItemSelected object, String[] bus_number, int rupees) {
        this.context=context;
        this.bus_number=bus_number;
        this.rupees=rupees;
        this.ob1=object;
    }

    @NonNull
    @Override
    public Hire_Bus_View_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.show_hire_buses,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Hire_Bus_View_Adapter.ViewHolder holder, int position) {
        holder.bus_number.setText(bus_number[position]);
        holder.rupees.setText(String.valueOf(rupees));

        holder.bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ob1.BusClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return bus_number.length;
    }

    public interface getItemSelected{
        public void BusClick(int position);
    }
}


