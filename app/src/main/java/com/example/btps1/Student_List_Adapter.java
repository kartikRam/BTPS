package com.example.btps1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class Student_List_Adapter extends RecyclerView.Adapter<Student_List_Adapter.ViewHolder> {

    Context context;
    String[] name ;
    String[] enrollment ;
    String[] images ;
    ArrayList<Integer> selected_items;
    static getItemSelected ob1;
    int counter=0;
    int selected_position,remove_position;
    int temp;
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView enrollment;
        ImageView student_image_list;
        CardView student;
        ImageView selected;
        public ViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.name);
            enrollment= v.findViewById(R.id.enrollment_no);
            student_image_list = v.findViewById(R.id.student_image_list);
            student=v.findViewById(R.id.student);
            selected=v.findViewById(R.id.selected_list);
        }

        @Override
        public void onClick(View v) {
               // ob1.getItem(this.getPosition());
        }
    }

    public Student_List_Adapter(Context context, getItemSelected object, String[] enrollment, String[] image, String[] name) {
        this.context=context;
        this.enrollment=enrollment;
        this.images=image;
        this.name=name;
        this.ob1=object;
        selected_items=new ArrayList<Integer>();
    }

    @NonNull
    @Override
    public Student_List_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.student_list_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Student_List_Adapter.ViewHolder holder, int position) {
        holder.name.setText(name[position]);
        holder.enrollment.setText(enrollment[position]);
        String url2 = Login.geturl() + "images/" + images[position];
        Glide
                .with(context)
                .load(url2)
                .centerCrop()
                .placeholder(R.drawable.profile)
                .into(holder.student_image_list);

        if(!(selected_items.equals(null))) {
            for (int i = 0; i < selected_items.size(); i++) {
                if (position == selected_items.get(i)) {
                    holder.selected.setVisibility(View.VISIBLE);
                }
            }
        }
        if(remove_position==position){
            holder.selected.setVisibility(View.GONE);
        }

        holder.student.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(counter==0){
                    selected_items.add(position);
                    remove_position = 500;
                    ob1.AddDataOnLongClick(position);
                    notifyItemChanged(position);
                    counter=1;
                }
                return false;
            }
        });
        holder.student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter==1) {
                    if (holder.selected.getVisibility() == View.GONE) {
                        selected_items.add(position);
                        remove_position = 500;
                        ob1.AddDataOnSingleClick(position);
                        notifyItemChanged(position);
                    }
                }
                if(holder.selected.getVisibility()==View.VISIBLE){
                    for(int i=0;i<selected_items.size();i++){
                        if(holder.enrollment.getText().equals(selected_items.get(i))){
                            selected_items.remove(i);
                            Toast.makeText(v.getContext(),"hello",Toast.LENGTH_SHORT).show();
                        }
                    }
                    remove_position=position;
                    ob1.RemoveDataOnSingleClick(position);
                    notifyItemChanged(position);
                }
                if(ob1.IsDataEmpty()){
                    counter=0;
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return name.length;
    }

    public interface getItemSelected{
        public void AddDataOnSingleClick(int position);
        public void AddDataOnLongClick(int position);
        public void RemoveDataOnSingleClick(int position);
        public boolean IsDataEmpty();
    }
}


