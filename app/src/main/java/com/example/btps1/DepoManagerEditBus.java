package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DepoManagerEditBus extends AppCompatActivity implements Student_List_View_Adapter.getItemSelected{

    RecyclerView edit_bus_list;
    RecyclerView.Adapter ProgramAdapter;
    RecyclerView.LayoutManager layoutManager;
    int j=0;

    String[] location = new String[150];
    String[] number = new String[150];
    String[] image = new String[150];
    String[] location1 ;
    String[] number1 ;
    String[] image1;
    String[] source=new String[150];
    String[] destination=new String[150];
    String[] time=new String[150];
    String[] via=new String[150];
    String[] conductor_id=new String[150];
    private AlertDialog.Builder dialog_builder;
    private AlertDialog dialog;

    String bus_number,bus_source,bus_via,bus_destination,conductor_id_text,bus_time;

    int t2hour, t2time;
    Button edit_bus_submit;
    TimePickerDialog picker;
    EditText edit_bus_number, edit_bus_source, edit_bus_via, edit_bus_destination, edit_conductor_id,edit_bus_time;
    ViewFlipper flipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_manager_edit_bus);
        getSupportActionBar().hide();
        edit_bus_list=(RecyclerView)findViewById(R.id.edit_bus_list);
        edit_bus_list.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        edit_bus_list.setLayoutManager(layoutManager);
        call();
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
                        source[j]=data.get(i).getSource();
                        destination[j]=data.get(i).getDestination();
                        time[j]=data.get(i).getBus_time();
                        Toast.makeText(getApplicationContext(), time[j], Toast.LENGTH_SHORT).show();
                        via[j]=data.get(i).getVia();
                        conductor_id[j]=data.get(i).getWorker_id();
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

    private void getlist() {


        image1=new String[j];
        location1=new String[j];
        number1=new String[j];

        for(int i=0;i<j;i++){
            image1[i]=image[i];
            location1[i]=location[i];
            number1[i]=number[i];
        }
        if(location1!=null) {
            ProgramAdapter = new Student_List_View_Adapter(this, this, location1, image1, number1);
            edit_bus_list.setAdapter(ProgramAdapter);
        }
    }

    @Override
    public void AddDataOnSingleClick(int position) {

        dialog_builder=new AlertDialog.Builder(this);
        final View bus_detail_popup=getLayoutInflater().inflate(R.layout.editbusdailog,null);
        dialog_builder.setView(bus_detail_popup);
        dialog =dialog_builder.create();
        dialog.show();

        flipper=bus_detail_popup.findViewById(R.id.flipper_bus_details);
        edit_bus_number = bus_detail_popup.findViewById(R.id.edit_bus_number);
        edit_bus_source = bus_detail_popup.findViewById(R.id.edit_bus_source);
        edit_bus_via = bus_detail_popup.findViewById(R.id.edit_bus_via);
        edit_bus_destination = bus_detail_popup.findViewById(R.id.edit_bus_destination);
        edit_bus_time = bus_detail_popup.findViewById(R.id.edit_bus_time);
        edit_conductor_id = bus_detail_popup.findViewById(R.id.edit_conductor_id);
        edit_bus_submit = bus_detail_popup.findViewById(R.id.edit_bus_submit);
        ImageView close_dialog=bus_detail_popup.findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        edit_bus_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello World", Toast.LENGTH_SHORT).show();
                TimePickerDialog timePickerDialog = new TimePickerDialog(DepoManagerEditBus.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
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
                            edit_bus_time.setText(sdf.format(date));
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
        int imgarray[]={R.drawable.buslogo1,R.drawable.buslogo2,R.drawable.buslogo3};

        for (int i=0;i<imgarray.length;i++){
            showimage(imgarray[i]);
        }

        setData(position);
        edit_bus_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bus_number = edit_bus_number.getText().toString();
                bus_source = edit_bus_source.getText().toString();
                bus_destination = edit_bus_destination.getText().toString();
                bus_via = edit_bus_via.getText().toString();
                bus_time = edit_bus_time.getText().toString();

                conductor_id_text = edit_conductor_id.getText().toString();
                if (bus_number.matches("") || bus_source.matches("") || bus_destination.matches("") || bus_via.matches("") || bus_time.matches("") || conductor_id_text.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all details!", Toast.LENGTH_SHORT).show();
                } else {
                    update();
                }
            }
        });


    }

    private void setData(int position) {

        edit_bus_number.setText(number[position]); ;
        edit_bus_source.setText(source[position]); ;
        edit_bus_via .setText(via[position]);
        edit_bus_destination.setText(destination[position]) ;
        edit_bus_time.setText(time[position]) ;
        edit_conductor_id .setText(conductor_id[position]);

    }

    public void showimage(int image){
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }

    private void update() {

        String url1=Login.geturl()+"updatebus.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Bus Updated Successfully", Toast.LENGTH_SHORT).show();
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
                    params.put("bus_number", bus_number);
                    params.put("bus_source", bus_source);
                    params.put("bus_destination", bus_destination);
                    params.put("bus_via", bus_via);
                    params.put("bus_time", bus_time);
                    params.put("bus_worker", conductor_id_text);
                    params.put("depo_manager_id", DepoManagerIndex.getManagerId());
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }

}