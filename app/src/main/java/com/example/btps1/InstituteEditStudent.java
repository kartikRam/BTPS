package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstituteEditStudent extends AppCompatActivity implements Student_List_View_Adapter.getItemSelected{

    RecyclerView editstudent_list;
    String url = Login.geturl() ;
    String[] images = new String[100];
    String[] name = new String[100];
    String[] enrollment = new String[100];

    ViewFlipper flipper;
    int j = 0;
    String[] images1 ;
    String[] name1;
    String[] enrollment1 ;

    RecyclerView.Adapter ProgramAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText edit_student_enrollment_no,edit_student_email,edit_student_password,edit_student_name;
    Button edit_student_submit;
    private AlertDialog.Builder dialog_builder;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_edit_student);
        getSupportActionBar().hide();
        editstudent_list=findViewById(R.id.edit_student_list);
        editstudent_list.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        editstudent_list.setLayoutManager(layoutManager);
        call();
    }


    private void getlist() {

        images1=new String[j];
        enrollment1=new String[j];
        name1=new String[j];

        for(int i=0;i<j;i++){
            images1[i]=images[i];
            enrollment1[i]=enrollment[i];
            name1[i]=name[i];
        }

        ProgramAdapter=new Student_List_View_Adapter(this,this,enrollment1,images1,name1);
        editstudent_list.setAdapter(ProgramAdapter);
    }

    private void call() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ViewUserProfileApi api = retrofit.create(ViewUserProfileApi.class);
        Call<List<Get_UserProfile_Model>> call = api.Get_UserProfile_Model();
        call.enqueue(new Callback<List<Get_UserProfile_Model>>() {
            @Override
            public void onResponse(Call<List<Get_UserProfile_Model>> call, retrofit2.Response<List<Get_UserProfile_Model>> response) {

                List<Get_UserProfile_Model> data = response.body();

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getInstitute_id().equals(InstituteIndex.getInstitute_id())) {
                        if (data.get(i).getImage().matches("")) {
                            images[j] = "defaultstudent.jpg";
                        } else {
                            images[j] = data.get(i).getImage();
                        }
                        name[j] = data.get(i).getUser_email();
                        enrollment[j] = data.get(i).getEnrollment_no();
                        j++;
                    }
                }
                getlist();
            }

            @Override
            public void onFailure(Call<List<Get_UserProfile_Model>> call, Throwable t) {

            }
        });
    }


    @Override
    public void AddDataOnSingleClick(int position) {
        dialog_builder=new AlertDialog.Builder(this);
        final View student_detail_popup=getLayoutInflater().inflate(R.layout.editstudentdialog,null);
        dialog_builder.setView(student_detail_popup);
        dialog =dialog_builder.create();
        dialog.show();
        ImageView close_window=student_detail_popup.findViewById(R.id.close_dialog);
        close_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        flipper=(ViewFlipper)student_detail_popup.findViewById(R.id.flipper_student_details);
        edit_student_enrollment_no=student_detail_popup.findViewById(R.id.edit_student_enrollment_no);
        edit_student_email=student_detail_popup.findViewById(R.id.edit_student_email);
        edit_student_name=student_detail_popup.findViewById(R.id.edit_student_name);
        edit_student_password=student_detail_popup.findViewById(R.id.edit_student_password);
        edit_student_submit=student_detail_popup.findViewById(R.id.edit_student_submit);
        int imgarray[]={R.drawable.student_flipper_1,R.drawable.student_flipper_2,R.drawable.student_flipper_3};
        for (int i=0;i<imgarray.length;i++){
            showimage(imgarray[i]);
        }
        getstudentDetails(enrollment[position]);
        edit_student_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudentDetails();
            }
        });

    }

    private void updateStudentDetails() {
        String url1=Login.geturl()+"update_student.php";
        String name_temp,email_temp,password_temp,enroll_temp;
        name_temp=edit_student_name.getText().toString();
        email_temp=edit_student_name.getText().toString();
        password_temp=edit_student_password.getText().toString();
        enroll_temp=edit_student_enrollment_no.getText().toString();

        if(name_temp.matches("")||email_temp.matches("")||password_temp.matches("")||enroll_temp.matches("")){
            Toast.makeText(getApplicationContext(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
        }else{

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Student Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Process Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email_temp);
                    params.put("password", password_temp);
                    params.put("enrollment", enroll_temp);
                    params.put("name", name_temp);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }

    }

    public void getstudentDetails(String s) {
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
                    if (data.get(i).getEnrollment_no().equals(s)) {

                        edit_student_password.setText(data.get(i).getUser_password());
                        edit_student_enrollment_no.setText(data.get(i).getEnrollment_no());
                        edit_student_name.setText(data.get(i).getUser_name());
                        edit_student_email.setText(data.get(i).getUser_email());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Get_UserProfile_Model>> call, Throwable t) {

            }
        });
    }

    private void showimage(int image) {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }
}