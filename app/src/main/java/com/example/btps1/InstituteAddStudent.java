package com.example.btps1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstituteAddStudent extends AppCompatActivity implements Student_List_Adapter.getItemSelected {
    RecyclerView student_list;
    Button  remove_student;
    String url = Login.geturl();
    String[] images = new String[100];
    String[] name = new String[100];
    String[] enrollment = new String[100];


    int j = 0;
    String[] images1 ;
    String[] name1;
    String[] enrollment1 ;

    String url1 = Login.geturl() + "deletestudentinstitute.php";
    ArrayList<String> data = new ArrayList<String>();

    RecyclerView.Adapter ProgramAdapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_add_student);
        getSupportActionBar().hide();
        student_list = findViewById(R.id.student_list);
        student_list.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        student_list.setLayoutManager(layoutManager);
        remove_student=findViewById(R.id.remove_student);
        call();
        remove_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });

    }

    public void remove() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    recreate();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String data1 = new Gson().toJson(data);
                params.put("data", data1);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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

        ProgramAdapter=new Student_List_Adapter(this,this,enrollment1,images1,name1);
        student_list.setAdapter(ProgramAdapter);
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
            if(data.contains(enrollment[position])){
                Toast.makeText(getApplicationContext(),"Data already there"+enrollment1[position],Toast.LENGTH_SHORT).show();
            }else {
                data.add(enrollment[position]);

            }
    }

    @Override
    public void AddDataOnLongClick(int position) {
        if(data.equals(null)||data.isEmpty()){
            data.add(enrollment[position]);
            remove_student.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void RemoveDataOnSingleClick(int position) {
        if(data.equals(null)||data.isEmpty()){
            //Nothing To remove
        }else{
            data.remove(enrollment[position]);
        }
    }
    @Override
    public boolean IsDataEmpty(){
        if(data.isEmpty()||data.equals(null)){
            remove_student.setVisibility(View.GONE);
            return true;
        }else{
            return false;
        }
    }
}