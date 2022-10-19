package com.example.btps1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DepoManagerAddBusForm extends AppCompatActivity {

    Button add_bus_submit;
    TimePickerDialog picker;
    EditText add_bus_number, add_bus_source, add_bus_via, add_bus_destination, add_conductor_id;
    EditText add_bus_time;
    int t2hour, t2time;
    String url = Login.geturl()+"addbus.php";
    String bus_number,bus_source,bus_via,bus_destination,conductor_id,bus_time;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_manager_add_bus_form);
        getSupportActionBar().hide();
        tv1 = findViewById(R.id.tv1);
        add_bus_number = findViewById(R.id.add_bus_number);
        add_bus_source = findViewById(R.id.add_bus_source);
        add_bus_via = findViewById(R.id.add_bus_via);
        add_bus_destination = findViewById(R.id.add_bus_destination);
        add_bus_time = findViewById(R.id.add_bus_time);
        add_conductor_id = findViewById(R.id.add_conductor_id);
        add_bus_submit = findViewById(R.id.add_bus_submit);


        add_bus_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello World", Toast.LENGTH_SHORT).show();
                TimePickerDialog timePickerDialog = new TimePickerDialog(DepoManagerAddBusForm.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t2hour = hourOfDay;
                        t2time = minute;
                        String time = t2hour + ":" + t2time;
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date date = dateFormat.parse(time);
                            SimpleDateFormat sdf = new SimpleDateFormat(
                                    "HH:mm aa"
                            );
                            add_bus_time.setText(sdf.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t2hour, t2time);
                timePickerDialog.show();
            }
        });


        add_bus_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus_number = add_bus_number.getText().toString();
                bus_source = add_bus_source.getText().toString();
                bus_destination = add_bus_destination.getText().toString();
                bus_via = add_bus_via.getText().toString();
                bus_time = add_bus_time.getText().toString();
                conductor_id = add_conductor_id.getText().toString();
                if (bus_number.matches("") || bus_source.matches("") || bus_destination.matches("") || bus_via.matches("") || bus_time.matches("") || conductor_id.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all details!", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(getApplicationContext(), "Jello", Toast.LENGTH_SHORT).show();
                    call();
                    add_bus_number.setText("");
                    add_bus_source.setText("");
                    add_bus_destination.setText("");
                    add_bus_via.setText("");
                    add_bus_time.setText("");
                    add_conductor_id.setText("");
                }
            }
        });
    }

/*

    private void call_to_retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DisplayBusApi api = retrofit.create(DisplayBusApi.class);
        Call<List<Display_Bus_Model>> call = api.Display_Bus_Model();
        call.enqueue(new Callback<List<Display_Bus_Model>>() {
            @Override
            public void onResponse(Call<List<Display_Bus_Model>> call, retrofit2.Response<List<Display_Bus_Model>> response) {
                List<Display_Bus_Model> data = response.body();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getBus_number().equals(bus_number_update.trim())) {
                        bus_number.getEditText().setText(data.get(i).getBus_number());
                        bus_source.getEditText().setText(data.get(i).getSource());
                        bus_destination.getEditText().setText(data.get(i).getDestination());
                        bus_via.getEditText().setText(data.get(i).getVia());
                        bus_time.setText(data.get(i).getBus_time());
                        bus_worker.getEditText().setText(data.get(i).getWorker_id());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Display_Bus_Model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
*/



    private void call() {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Bus Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "error:"+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("bus_number", bus_number);
                params.put("bus_source", bus_source);
                params.put("bus_destination", bus_destination);
                params.put("bus_via", bus_via);
                params.put("bus_time", bus_time);
                params.put("bus_worker", conductor_id);
                params.put("depo_manager_id", DepoManagerIndex.getManagerId());
                params.put("branch_name",DepoManagerIndex.getManagerBranch());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }


}