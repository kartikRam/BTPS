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

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstituteIndex extends AppCompatActivity {

    CardView institute_profile,institute_add_student,institute_remove_student,institute_edit_student,institute_logout;
    TextView institute_name_dashboard;
    ImageView institute_image_dashboard;
    static String institute_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_index);
        getSupportActionBar().hide();
        institute_profile=findViewById(R.id.institute_profile);
        institute_add_student=findViewById(R.id.institute_add_student);
        institute_remove_student=findViewById(R.id.institute_remove_student);
        institute_edit_student=findViewById(R.id.institute_edit_student);
        institute_logout=findViewById(R.id.institute_logout);
        institute_name_dashboard=findViewById(R.id.institute_name_dashboard);
        institute_image_dashboard=findViewById(R.id.institute_image_dashboard);
        call();
        institute_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        institute_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),InstituteHome.class);
                startActivity(intent);
            }
        });

        institute_add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),InstituteAddStudentForm.class);
                startActivity(intent);
            }
        });
        institute_remove_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),InstituteAddStudent.class);
                startActivity(intent);
            }
        });
        institute_edit_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),InstituteEditStudent.class);
                startActivity(intent);
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
                InstituteIndex.this.finish();
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


    private void call() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Login.geturl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ViewInstituteDetailsApi api = retrofit.create(ViewInstituteDetailsApi.class);
        Call<List<Get_Institute_Details>> call = api.Get_Institute_Details();
        call.enqueue(new Callback<List<Get_Institute_Details>>() {
            @Override
            public void onResponse(Call<List<Get_Institute_Details>> call, retrofit2.Response<List<Get_Institute_Details>> response) {
                List<Get_Institute_Details> data = response.body();
                for (int i = 0; i < data.size(); i++) {

                    if (data.get(i).getInstitute_email().equals(Login.getEmail())) {

                        institute_name_dashboard.setText(data.get(i).getInstitute_name());
                        institute_id=data.get(i).getInstitute_id();
                        String url21 = Login.geturl()+"images/" + data.get(i).getImage();
                        Glide
                                .with(InstituteIndex.this)
                                .load(url21)
                                .centerCrop()
                                .placeholder(R.drawable.profile)
                                .into(institute_image_dashboard);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Get_Institute_Details>> call, Throwable t) {

            }
        });

    }

    public static String getInstitute_id(){
        return institute_id;
    }
}