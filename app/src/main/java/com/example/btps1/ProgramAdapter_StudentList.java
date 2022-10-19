package com.example.btps1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ProgramAdapter_StudentList extends ArrayAdapter<String> {
    Context context;
    View view;
    String[] images;
    int i = 0;
    String[] Student_email;
    String[] Student_enrollment;

    delete_data dd;




    public ProgramAdapter_StudentList(@NonNull Context context, delete_data dd,String[] cityName, String[] images, String[] cityDescription) {
        super(context, R.layout.single_item_studentlist, R.id.student_email, cityName);
        this.context = context;
        this.dd=dd;
        this.Student_email = cityName;
        this.images = images;
        this.Student_enrollment = cityDescription;

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View Singleitem = convertView;
        ProgramViewHolder_StudentList holder = null;
        if (Singleitem == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Singleitem = layoutInflater.inflate(R.layout.single_item_studentlist, parent, false);
            holder = new ProgramViewHolder_StudentList(Singleitem);
            Singleitem.setTag(holder);
        } else {
            holder = (ProgramViewHolder_StudentList) Singleitem.getTag();
        }
        holder.remove.setImageResource(R.drawable.check_circle);
        holder.remove.setVisibility(View.GONE);
        //holder.itemImage.setImageResource(images[position]);
        String url2 = Login.geturl() + "images/" + images[position];
        Glide
                .with(context)
                .load(url2)
                .centerCrop()
                .placeholder(R.drawable.profile)
                .into(holder.itemImage);

        holder.Student_email.setText(Student_email[position]);
        holder.Student_enrollment.setText(Student_enrollment[position]);

        Singleitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (v.findViewById(R.id.remove).getVisibility() == View.GONE) {
                    dd.add_data(Student_enrollment[position]);
                    v.findViewById(R.id.remove).setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        Singleitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(getContext(), "Single Click", Toast.LENGTH_SHORT).show();
                    String check=dd.check_data();

                    if(check.equals("empty")){
                        dd.transfer(Student_enrollment[position]);
                    }
                if (v.findViewById(R.id.remove).getVisibility() == View.VISIBLE) {
                    dd.remove_data(Student_enrollment[position]);
                    v.findViewById(R.id.remove).setVisibility(View.INVISIBLE);
                }

            }
        });
        return Singleitem;
    }

    public interface delete_data{
        void add_data(String enrollment);
        void remove_data(String enrollment);
        String check_data();
        void transfer(String enrollment);
    }

}

