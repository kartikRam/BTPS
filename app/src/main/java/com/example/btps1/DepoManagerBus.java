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

public class DepoManagerBus extends AppCompatActivity implements Student_List_Adapter.getItemSelected {
    String[] location = new String[150];
    String[] number = new String[150];
    String[] image = new String[150];
    String[] location1 ;
    String[] number1 ;
    String[] image1;
    int j = 0;

    Button remove_bus;
    ArrayList<String> data = new ArrayList<String>();
    String url1 = Login.geturl() + "removebus.php";
    String url = Login.geturl();
    String check="no";
    RecyclerView bus_list;
    RecyclerView.Adapter ProgramAdapter;
    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_manager_bus);
        bus_list = (RecyclerView)findViewById(R.id.depo_manager_bus_list);
        remove_bus = findViewById(R.id.remove_bus);
        getSupportActionBar().hide();
        bus_list.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        bus_list.setLayoutManager(layoutManager);
        call();
        remove_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DepoManagerBus.this);
                builder.setTitle("Remove Bus");
                builder.setMessage("Are you sure you want to Remove Bus?");
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



    private void getlist() {

        image1=new String[j];
        location1=new String[j];
        number1=new String[j];

        for(int i=0;i<j;i++){
            image1[i]=image[i];
            location1[i]=location[i];
            number1[i]=number[i];
        }

        ProgramAdapter=new Student_List_Adapter(this,this,location1,image1,number1);
        bus_list.setAdapter(ProgramAdapter);
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


    private void call() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Login.geturl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DisplayBusApi api = retrofit.create(DisplayBusApi.class);
        Call<List<Display_Bus_Model>> call = api.Display_Bus_Model();
        call.enqueue(new Callback<List<Display_Bus_Model>>() {
            @Override
            public void onResponse(Call<List<Display_Bus_Model>> call, retrofit2.Response<List<Display_Bus_Model>> response) {
                List<Display_Bus_Model> data = response.body();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getDepo_manager_id().equals(DepoManagerIndex.getManagerId())) {
                        location[j] = data.get(i).getSource() + " to " + data.get(i).getDestination();
                        number[j] = data.get(i).getBus_number();
                        image[j]="bus_default.png";
                        j++;
                    }
                }

                getlist();
            }

            @Override
            public void onFailure(Call<List<Display_Bus_Model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void AddDataOnSingleClick(int position) {
        if(data.contains(number[position])){
            Toast.makeText(getApplicationContext(),"Data already there"+number[position],Toast.LENGTH_SHORT).show();
        }else {
            data.add(number1[position]);
        }


    }

    @Override
    public void AddDataOnLongClick(int position) {
        if(data.equals(null)||data.isEmpty()){
            data.add(number1[position]);
            remove_bus.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void RemoveDataOnSingleClick(int position) {
        if(data.equals(null)||data.isEmpty()){
            //Nothing To remove
        }else{
            data.remove(number1[position]);
        }
    }

    @Override
    public boolean IsDataEmpty() {

        if(data.isEmpty()||data.equals(null)){
            remove_bus.setVisibility(View.GONE);
            return true;
        }else{
            return false;
        }}
}