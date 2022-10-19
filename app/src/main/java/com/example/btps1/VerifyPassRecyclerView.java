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


public class VerifyPassRecyclerView extends RecyclerView.Adapter<VerifyPassRecyclerView.ViewHolder> {

    Context context;
    String[] uname;
    String[] uimage ;
    String[] submit_date ;
    static ItemSelected ob1;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView user_name_pass_verification;
        TextView submit_date;
        ImageView user_image_pass_verification;
        CardView pass_verification;
        public ViewHolder(@NonNull View v) {
            super(v);
            user_name_pass_verification = v.findViewById(R.id.user_name_pass_verification);
            user_image_pass_verification = v.findViewById(R.id.user_image_pass_verification);
            submit_date = v.findViewById(R.id.submit_date);
            pass_verification=v.findViewById(R.id.pass_verification);
        }

        @Override
        public void onClick(View v) {
               // ob1.getItem(this.getPosition());
        }
    }

    public VerifyPassRecyclerView(Context context, ItemSelected object, String[] uname, String[] submit_date, String[] uimage) {
        this.context=context;
        this.uname=uname;
        this.uimage=uimage;
        this.submit_date=submit_date;
        this.ob1=object;

    }

    @NonNull
    @Override
    public VerifyPassRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.pass_verification_view,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VerifyPassRecyclerView.ViewHolder holder, int position) {
        holder.user_name_pass_verification.setText(uname[position]);
        holder.submit_date.setText(submit_date[position]);
        String url2 = Login.geturl()+"images/" + uimage[position];
        Glide
                .with(context)
                .load(url2)
                .centerCrop()
                .placeholder(R.drawable.profile)
                .into(holder.user_image_pass_verification);

        holder.pass_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ob1.setItem(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return uname.length;
    }
    public interface ItemSelected{
        public void setItem(int position);
    }
}


