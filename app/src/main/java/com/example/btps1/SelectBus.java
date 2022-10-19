package com.example.btps1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectBus extends AppCompatActivity implements ProgramAdapter2.getItemSelected {

    RecyclerView bus_list;
    RecyclerView.Adapter ProgramAdapter;
    RecyclerView.LayoutManager layoutManager;
    String url;
    String conductor_name;
    String conductor_image;
    String sourcesb;
    //Used to pass data to ticket class
    static String bus_number_pass;
    ViewFlipper flipper;
    static String destinationsb;
    String[] busname1=new String[15];
    String[] busnumber1=new String[15];
    String[] via1=new String[15];
    String[] time1=new String[15];
    String[] fare1=new String[15];
    String[] bus_name;
    String[] bus_number;
    String[] via;
    String[] time;
    String[] fare;
    String[] conductor_id=new String[50];
    String[] bus_source=new String[50];
    String[] bus_destination=new String[50];
    String x, y, z, a, v;
    Intent intent;
    private AlertDialog.Builder dialog_builder;
    private AlertDialog dialog;
    ImageView cut,conductor_image_bus_details;
    TextView conductor,source,destination,via_bus_details,time_bus_details,bus_number_details;
    Button bus_route,bus_location,get_ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bus);
        getSupportActionBar().hide();
        intent = getIntent();
        destinationsb = intent.getStringExtra("destination");
        url = Login.geturl();
        bus_list=(RecyclerView)findViewById(R.id.bus_list);
        bus_list.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        bus_list.setLayoutManager(layoutManager);
        call();
        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position=viewHolder.getAdapterPosition();

                    switch(direction){
                        case ItemTouchHelper.LEFT:
                                Toast.makeText(getApplicationContext(),"Left Swipe to "+bus_number[position],Toast.LENGTH_SHORT).show();
                                break;
                        case ItemTouchHelper.RIGHT:
                                Toast.makeText(getApplicationContext(),"Right Swipe to "+bus_number[position],Toast.LENGTH_SHORT).show();
                                break;
                    }
            }
        };
    }



    private void call() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<Get_Bus_Model>> call = api.Get_Bus_Model();
        call.enqueue(new Callback<List<Get_Bus_Model>>() {
            @Override
            public void onResponse(Call<List<Get_Bus_Model>> call, retrofit2.Response<List<Get_Bus_Model>> response) {

                List<Get_Bus_Model> data = response.body();
                int j=0;
                for (int i = 0; i < data.size(); i++) {
                        String sourcetemp=data.get(i).getSource().trim();
                        String sourcetemp1=UserPanel.getsource().trim();
                        String destination=data.get(i).getDestination().trim();
                        String desttemp=Select_Destination.getDestination().trim();

                    if (sourcetemp.equalsIgnoreCase(sourcetemp1) && destination.equalsIgnoreCase(desttemp)) {
                        y=data.get(i).getSource();
                        z=data.get(i).getDestination();
                        String final1=y+" to "+z;
                        bus_source[j]=y;
                        bus_destination[j]=z;
                        busname1[j]=final1;
                        via1[j]=data.get(i).getVia();
                        fare1[j]="1";
                        time1[j]=data.get(i).getTime();
                        busnumber1[j]=data.get(i).getBus_number();
                        conductor_id[j]=data.get(i).getWorker_id();
                        j++;
                    }
                }
                data(j);

            }

            @Override
            public void onFailure(Call<List<Get_Bus_Model>> call, Throwable t) {

            }
        });
    }
    private void data(int j) {
        bus_name=new String[j];
        bus_number=new String[j];
        via=new String[j];
        time=new String[j];
        fare=new String[j];

        for(int i=0;i<j;i++){
            bus_name[i]=busname1[i];
            bus_number[i]=busnumber1[i];
            via[i]=via1[i];
            time[i]=time1[i];
            fare[i]=fare1[i];
        }

        ProgramAdapter=new ProgramAdapter2(this,this,time,bus_name,bus_number);
        bus_list.setAdapter(ProgramAdapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            SelectBus.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void getItem(int position) {
        Toast.makeText(getApplicationContext(),"You Clicked "+bus_number[position],Toast.LENGTH_SHORT).show();
        dialog_builder=new AlertDialog.Builder(this);
        final View bus_detail_popup=getLayoutInflater().inflate(R.layout.bus_details,null);
        dialog_builder.setView(bus_detail_popup);
        dialog =dialog_builder.create();
        dialog.show();
        cut=bus_detail_popup.findViewById(R.id.cut);
        flipper=bus_detail_popup.findViewById(R.id.flipper_bus_details);
        conductor=bus_detail_popup.findViewById(R.id.bus_details_conductor_name);
        source=bus_detail_popup.findViewById(R.id.bus_details_source);
        destination=bus_detail_popup.findViewById(R.id.bus_details_destination);
        time_bus_details=bus_detail_popup.findViewById(R.id.bus_details_time);
        via_bus_details=bus_detail_popup.findViewById(R.id.bus_details_via);
        bus_number_details=bus_detail_popup.findViewById(R.id.bus_details_bus_number);
        get_ticket=bus_detail_popup.findViewById(R.id.get_ticket);
        bus_location=bus_detail_popup.findViewById(R.id.bus_location);
        bus_route=bus_detail_popup.findViewById(R.id.bus_route);
        conductor_image_bus_details=bus_detail_popup.findViewById(R.id.conductor_image_bus_details);
        getConductorDetails(conductor_id[position],position);
        int imgarray[]={R.drawable.buslogo1,R.drawable.buslogo2,R.drawable.buslogo3};
        for (int i=0;i<imgarray.length;i++){
            showimage(imgarray[i]);
        }


        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        get_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Ticket.class);
                bus_number_pass=bus_number[position];
                startActivity(intent);

            }
        });
        bus_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + bus_source[position] + "/" + bus_destination[position]);
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps" );
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        });
        bus_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getConductorDetails(String s,int position) {

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
                    if(data.get(i).getWorker_id().equals(s)){
                        conductor_name=data.get(i).getWorker_name();
                        conductor_image=Login.geturl()+"images/"+data.get(i).getImage();
                    }
                }
                set_data_bus_details(position);
            }
            @Override
            public void onFailure(Call<List<Display_Conductor_Model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " Lelo ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void set_data_bus_details(int position) {

        conductor.setText(conductor_name);
        if(conductor_image.matches("")){
            //Do nothing
            //Toast.makeText(getApplicationContext(),"Sorry Conductor has No image",Toast.LENGTH_SHORT).show();
        }else{
            Glide
                    .with(SelectBus.this)
                    .load(conductor_image)
                    .centerCrop()
                    .placeholder(R.drawable.conductor_logo)
                    .into(conductor_image_bus_details);

        }

        source.setText(bus_source[position]);
        destination.setText(bus_destination[position]);
        time_bus_details.setText(time[position]);
        via_bus_details.setText(via[position]);
        bus_number_details.setText(bus_number[position]);

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

    @Override
    public void LongClick(int position) {
        Toast.makeText(getApplicationContext(),"Long click on "+bus_number[position],Toast.LENGTH_SHORT).show();
    }
    public static String get_bus_number(){
        return bus_number_pass;
    }

}