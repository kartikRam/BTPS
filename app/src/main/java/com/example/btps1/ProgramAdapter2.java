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


public class ProgramAdapter2 extends RecyclerView.Adapter<ProgramAdapter2.ViewHolder> {

    Context context;
    String[] bus_time ;
    String[] bus_name ;
    String[] bus_number ;
    static getItemSelected ob1;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView bus_time1;
        TextView bus_name1;
        TextView bus_number1;
        CardView buses;
        public ViewHolder(@NonNull View v) {
            super(v);
            bus_time1 = v.findViewById(R.id.bus_time);
            bus_name1 = v.findViewById(R.id.bus_name);
            bus_number1 = v.findViewById(R.id.bus_number);
            buses=v.findViewById(R.id.buses);
        }

        @Override
        public void onClick(View v) {
               // ob1.getItem(this.getPosition());
        }
    }

    public ProgramAdapter2(Context context,getItemSelected object,String[] time, String[] busname1, String[] busnumber) {
        this.context=context;
        this.bus_time=time;
        this.bus_name=busname1;
        this.bus_number=busnumber;
        this.ob1=object;

    }

    @NonNull
    @Override
    public ProgramAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.busview,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramAdapter2.ViewHolder holder, int position) {
        holder.bus_name1.setText(bus_name[position]);
        holder.bus_number1.setText(bus_number[position]);
        holder.bus_time1.setText(bus_time[position]);
        holder.buses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ob1.getItem(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return bus_number.length;
    }
    public interface getItemSelected{
        public void getItem(int position);
        public void LongClick(int position);

    }
}


