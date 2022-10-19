package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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

import static com.example.btps1.Login.url1;

public class UserIndex extends AppCompatActivity {

    static String user_name,user_id,institute_id,user_address,user_contact,image,user_type;
    TextView user_name_dashboard;
    ImageView user_image_dashboard;
    boolean pass_created=false;
    CardView profile,logout,trackbus,hirebus,pass,ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_index);
        getSupportActionBar().hide();
        profile=findViewById(R.id.profile);
        logout=findViewById(R.id.logout);
        trackbus=findViewById(R.id.trackbus);
        hirebus=findViewById(R.id.hirebus);
        pass=findViewById(R.id.pass);
        ticket=findViewById(R.id.ticket);
        user_image_dashboard=findViewById(R.id.user_image_dashboard);
        user_name_dashboard=findViewById(R.id.user_name_dashboard);
        call();
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_LONG).show();
            }
        });

        hirebus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Hire.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Bus Hire",Toast.LENGTH_LONG).show();
            }
        });
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassIsCreated();
            }
        });
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserPanel.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Bus Ticket",Toast.LENGTH_LONG).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
                Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_LONG).show();
            }
        });
        trackbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Tracking.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Bus Track",Toast.LENGTH_LONG).show();
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
                UserIndex.this.finish();
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
    public void call(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Login.geturl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ViewUserProfileApi api = retrofit.create(ViewUserProfileApi.class);
        Call<List<Get_UserProfile_Model>> call = api.Get_UserProfile_Model();
        call.enqueue(new Callback<List<Get_UserProfile_Model>>() {
            @Override
            public void onResponse(Call<List<Get_UserProfile_Model>> call, retrofit2.Response<List<Get_UserProfile_Model>> response) {
                List<Get_UserProfile_Model> data = response.body();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getUser_email().equals(Login.getEmail())) {
                        user_name=data.get(i).getUser_name();
                        user_address=data.get(i).getUser_address();
                        user_contact=data.get(i).getUser_contact_no();
                        user_id=data.get(i).getUser_id();
                        image=data.get(i).getImage();
                        user_name_dashboard.setText(user_name);
                        String url2 = Login.geturl()+"images/" + data.get(i).getImage();
                        Glide
                                .with(UserIndex.this)
                                .load(url2)
                                .centerCrop()
                                .placeholder(R.drawable.profile)
                                .into(user_image_dashboard);
                        if(data.get(i).getIs_student().equals("1")){
                            user_type="Student";
                        }else{user_type="Passenger";}

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Get_UserProfile_Model>> call, Throwable t) {

            }
        });
    }
    public static String get_username(){
        return user_name;
    }
    public static String get_userid(){
        return user_id;
    }
    public static String get_userimage(){
        return image;
    }

    public static String get_usertype(){
        return user_type;
    }

    public void checkPassIsCreated(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Login.geturl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ViewPassApi api = retrofit.create(ViewPassApi.class);
        Call<List<Get_Pass_Model>> call = api.Get_Pass_Model();
        call.enqueue(new Callback<List<Get_Pass_Model>>() {
            @Override
            public void onResponse(Call<List<Get_Pass_Model>> call, retrofit2.Response<List<Get_Pass_Model>> response) {
                List<Get_Pass_Model> data = response.body();

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getUser_id().equals(get_userid())) {

                        pass_created=true;
                    }
                }
                IntentPass();
            }

            @Override
            public void onFailure(Call<List<Get_Pass_Model>> call, Throwable t) {

            }
        });

    }

    private void IntentPass() {
        if(pass_created){

            Intent intent = new Intent(getApplicationContext(),DisplayPass.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Bus Pass",Toast.LENGTH_LONG).show();

        }else{
            Intent intent = new Intent(getApplicationContext(),Pass.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Bus Pass",Toast.LENGTH_LONG).show();

        }
    }
}