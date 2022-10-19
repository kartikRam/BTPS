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

public class DepoManagerIndex extends AppCompatActivity {

    TextView depo_manager_name_dashboard;
    ImageView depo_manager_image_dashboard;
    CardView depo_manager_profile,depo_manager_manage_conductor,depo_manager_manage_bus,depo_manager_assistance_request,depo_manager_logout,depo_manager_pass_verification;
    static String depo_manager_id,branch_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_manager_index);
        getSupportActionBar().hide();
        depo_manager_name_dashboard=findViewById(R.id.depo_manager_name_dashboard);
        depo_manager_image_dashboard=findViewById(R.id.depo_manager_image_dashboard);
        depo_manager_profile=findViewById(R.id.depo_manager_profile);
        depo_manager_manage_conductor=findViewById(R.id.depo_manager_manage_conductor);
        depo_manager_manage_bus=findViewById(R.id.depo_manager_manage_bus);
        depo_manager_pass_verification=findViewById(R.id.depo_manager_pass_verification);
        depo_manager_assistance_request=findViewById(R.id.depo_manager_assistance_request);
        depo_manager_logout=findViewById(R.id.depo_manager_logout);
        call();

        depo_manager_pass_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),DepoManagerPassVerification.class);
                startActivity(intent);
            }
        });
        depo_manager_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        depo_manager_assistance_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DepoManagerAssistanceRequest.class);
                startActivity(intent);
            }
        });
        depo_manager_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),ConductorProfile.class);
                startActivity(intent);
            }
        });
        depo_manager_manage_conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),DepoManagerManageConductor.class);
                startActivity(intent);
            }
        });
        depo_manager_manage_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),DepoManagerManageBus.class);
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
                        depo_manager_name_dashboard.setText(data.get(i).getWorker_name());
                        String url2 = Login.geturl()+"images/" + data.get(i).getImage();
                        Glide
                                .with(DepoManagerIndex.this)
                                .load(url2)
                                .centerCrop()
                                .placeholder(R.drawable.profile)
                                .into(depo_manager_image_dashboard);
                        depo_manager_id=data.get(i).getWorker_id();
                        Toast.makeText(getApplicationContext(),data.get(i).getWorker_id(),Toast.LENGTH_SHORT).show();
                        branch_name=data.get(i).getBranch_name();
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
                DepoManagerIndex.this.finish();
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
    public static String getManagerId(){
        return depo_manager_id;
    }
    public static String getManagerBranch(){
        return branch_name;
    }
}