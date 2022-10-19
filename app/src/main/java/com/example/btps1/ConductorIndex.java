package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConductorIndex extends AppCompatActivity {

    TextView conductor_name_dashboard;
    ImageView conductor_image_dashboard;
    CardView conductor_profile,conductor_scan,conductor_assistance_request,conductor_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_index);
        getSupportActionBar().hide();
        conductor_name_dashboard=findViewById(R.id.conductor_name_dashboard);
        conductor_image_dashboard=findViewById(R.id.conductor_image_dashboard);
        conductor_profile=findViewById(R.id.conductor_profile);
        conductor_scan=findViewById(R.id.conductor_scan);
        conductor_assistance_request=findViewById(R.id.conductor_assistance_request);
        conductor_logout=findViewById(R.id.conductor_logout);
        call();

        conductor_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        conductor_assistance_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ConductorAssistanceRequest.class);
                startActivity(intent);
            }
        });
        conductor_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ConductorScan.class);
                startActivity(intent);
            }
        });
        conductor_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ConductorProfile.class);
                startActivity(intent);
            }
        });
    }


    private void call() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Login.geturl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DisplayConductorApi api = retrofit.create(DisplayConductorApi.class);
        Call<List<Display_Conductor_Model>> call = api.Display_Conductor_Model();
        call.enqueue(new Callback<List<Display_Conductor_Model>>() {
            @Override
            public void onResponse(Call<List<Display_Conductor_Model>> call, retrofit2.Response<List<Display_Conductor_Model>> response) {
                List<Display_Conductor_Model> data = response.body();
                int j = 0;
                for (int i = 0; i < data.size(); i++) {
                    String email = data.get(i).getWorker_email().trim();
                    if (email.matches(Login.getEmail())) {
                        conductor_name_dashboard.setText(data.get(i).getWorker_name());
                        String url2 = Login.geturl()+"images/" + data.get(i).getImage();
                        Glide
                                .with(ConductorIndex.this)
                                .load(url2)
                                .centerCrop()
                                .placeholder(R.drawable.profile)
                                .into(conductor_image_dashboard);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Display_Conductor_Model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " Lelo ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void dialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to Logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //UserPanel.finishActivity();
                ConductorIndex.this.finish();
                System.exit(0);
            }

        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}