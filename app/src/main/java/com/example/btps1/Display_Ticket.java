package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Display_Ticket extends AppCompatActivity {
    TextView user_name, src_and_dest, expires, totalkms, amount;
    ImageView user_image;

    String source, destination, rate, time, ticketid;

    String url = Login.geturl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__ticket);
        getSupportActionBar().hide();

        Intent i = getIntent();
        ticketid = i.getStringExtra("source");
        user_name = findViewById(R.id.user_name);
        src_and_dest = findViewById(R.id.src_and_dest);
        expires = findViewById(R.id.expires);
        totalkms = findViewById(R.id.totalkms);
        amount = findViewById(R.id.amount);
        user_image = findViewById(R.id.user_image);

        call();

        user_name.setText(MainActivity.getusername());
        String url2 = Login.geturl()+"images/" + MainActivity.getimage();
        Glide
                .with(Display_Ticket.this)
                .load(url2)
                .centerCrop()
                .placeholder(R.drawable.profile)
                .into(user_image);




    }

    private void call() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DisplayTicketApi api = retrofit.create(DisplayTicketApi.class);
        Call<List<Ticket_Model>> call = api.Ticket_Model();
        call.enqueue(new Callback<List<Ticket_Model>>() {
            @Override
            public void onResponse(Call<List<Ticket_Model>> call, retrofit2.Response<List<Ticket_Model>> response) {
                List<Ticket_Model> data = response.body();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getUser_id().equals(MainActivity.getuserid())) {

                        if (data.get(i).getTicket_id().equals(ticketid)) {
                            src_and_dest.setText(data.get(i).getSource() + " to " + data.get(i).getDestination());
                            amount.setText("Amount: " + data.get(i).getTicket_rate());
                            expires.setText("Created On: " + data.get(i).getTime());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Ticket_Model>> call, Throwable t) {

            }
        });
    }



}