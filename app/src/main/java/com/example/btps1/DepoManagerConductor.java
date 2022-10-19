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

public class DepoManagerConductor extends AppCompatActivity implements Student_List_Adapter.getItemSelected {

    Button remove_conductor;
    String url = Login.geturl();

    RecyclerView conductor_list;
    RecyclerView.Adapter ProgramAdapter;
    RecyclerView.LayoutManager layoutManager;

    int j = 0;
    String[] name1 = new String[120];
    String[] id1 = new String[120];
    String[] image1 = new String[120];
    String[] name;
    String[] id;
    String[] image;
    ArrayList<String> data = new ArrayList<String>();
    String url1=Login.geturl()+"remove_conductor.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_manager_conductor);
        getSupportActionBar().hide();
        conductor_list = (RecyclerView)findViewById(R.id.depo_manager_conductor_list);
        conductor_list.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        conductor_list.setLayoutManager(layoutManager);

        remove_conductor = findViewById(R.id.remove_conductor);



        call();
        remove_conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DepoManagerConductor.this);
                builder.setTitle("Remove Conductor");
                builder.setMessage("Are you sure you want to Remove Conductor?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        remove();
                        recreate();
                    }

                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

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
        image = new String[j];
        name = new String[j];
        id = new String[j];

        for (int i = 0; i < j; i++) {
            image[i] = image1[i];
            name[i] = name1[i];
            id[i] = id1[i];
        }
        if (name != null) {
            ProgramAdapter = new Student_List_Adapter(this, this, name, image, id);
            conductor_list.setAdapter(ProgramAdapter);

        }
    }


    private void call() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DisplayConductorApi api = retrofit.create(DisplayConductorApi.class);
        Call<List<Display_Conductor_Model>> call = api.Display_Conductor_Model();
        call.enqueue(new Callback<List<Display_Conductor_Model>>() {
            @Override
            public void onResponse(Call<List<Display_Conductor_Model>> call, retrofit2.Response<List<Display_Conductor_Model>> response) {
                List<Display_Conductor_Model> data = response.body();

                for (int i = 0; i < data.size(); i++) {
                   if(data.get(i).getDepo_manager_id().equals(DepoManagerIndex.getManagerId())){
                       name1[j]=data.get(i).getWorker_email();
                       id1[j]=data.get(i).getWorker_id();
                       if(data.get(i).getImage().equals("")){
                           image1[j]="conductor_logo.png";
                       }else{
                           image1[j]=data.get(i).getImage();
                       }
                       j++;
                   }
                }
                getlist();
            }
            @Override
            public void onFailure(Call<List<Display_Conductor_Model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " Lelo ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void AddDataOnSingleClick(int position) {
        if(data.contains(id[position])){
            Toast.makeText(getApplicationContext(),"Data already there"+id1[position],Toast.LENGTH_SHORT).show();
        }else {
            data.add(id1[position]);
        }


    }

    @Override
    public void AddDataOnLongClick(int position) {
        if(data.equals(null)||data.isEmpty()){
            data.add(id[position]);
            remove_conductor.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void RemoveDataOnSingleClick(int position) {
        if(data.equals(null)||data.isEmpty()){
            //Nothing To remove
        }else{
            data.remove(id1[position]);
        }
    }

    @Override
    public boolean IsDataEmpty() {

        if(data.isEmpty()||data.equals(null)){
            remove_conductor.setVisibility(View.GONE);
            return true;
        }else{
            return false;
        }}
}