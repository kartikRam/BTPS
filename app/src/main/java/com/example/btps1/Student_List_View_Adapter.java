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


public class Student_List_View_Adapter extends RecyclerView.Adapter<Student_List_View_Adapter.ViewHolder> {

    Context context;
    String[] name ;
    String[] enrollment ;
    String[] images ;
    static getItemSelected ob1;
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

    public Student_List_View_Adapter(Context context, getItemSelected object, String[] enrollment, String[] image, String[] name) {
        this.context=context;
        this.enrollment=enrollment;
        this.images=image;
        this.name=name;
        this.ob1=object;
    }

    @NonNull
    @Override
    public Student_List_View_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.student_list_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Student_List_View_Adapter.ViewHolder holder, int position) {
        holder.name.setText(name[position]);
        holder.enrollment.setText(enrollment[position]);
        String url2 = Login.geturl() + "images/" + images[position];
        Glide
                .with(context)
                .load(url2)
                .centerCrop()
                .placeholder(R.drawable.profile)
                .into(holder.student_image_list);


        holder.student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ob1.AddDataOnSingleClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return name.length;
    }

    public interface getItemSelected{
        public void AddDataOnSingleClick(int position);
    }
}


