package com.example.btps1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class ProgramAdapter3 extends RecyclerView.Adapter<ProgramAdapter3.ViewHolder> {

    Context context;
    String[] ticket_name ;
    String[] ticket_time ;
    String[] ticket_date ;
    static getTicketSelected ob1;

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        TextView ticket_name;
        TextView ticket_date;
        TextView ticket_time;
        CardView ticket;
        public ViewHolder(@NonNull View v) {
            super(v);
            ticket_name = v.findViewById(R.id.ticket_name);
            ticket_date = v.findViewById(R.id.ticket_date);
            ticket_time = v.findViewById(R.id.ticket_time);
            ticket=v.findViewById(R.id.ticket);
        }

    }

    public ProgramAdapter3(Context context,getTicketSelected object,String[] ticket_name, String[] ticket_date, String[] ticket_time) {
        this.context=context;
        this.ticket_name=ticket_name;
        this.ticket_date=ticket_date;
        this.ticket_time=ticket_time;
        this.ob1=object;

    }

    @NonNull
    @Override
    public ProgramAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.ticket_list_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramAdapter3.ViewHolder holder, int position) {
        holder.ticket_name.setText(ticket_name[position]);
        holder.ticket_date.setText(ticket_date[position]);
        holder.ticket_time.setText(ticket_time[position]);
        holder.ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ob1.getTicket(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return ticket_date.length;
    }
    public interface getTicketSelected{
        public void getTicket(int position);

    }
}


