package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketList extends AppCompatActivity implements ProgramAdapter3.getTicketSelected {
    String[] ticketid=new String[50];
    String[] tname=new String[50];
    String[] tickettime=new String[50];
    String[] ticketdate=new String[50];
    String[] busid=new String[50];
    String[] workerid=new String[50];
    String[] source=new String[50];
    String[] destination=new String[50];
    String[] ticketrate=new String[50];
    String[] ticketkilometer=new String[50];
    String[] ticketno=new String[50];
    int j=0;

    String[] ticket_id;
    String[] ticket_time;
    String[] ticket_date;
    String[] bus_id;
    String[] worker_id;
    String[] ticket_source;
    String[] ticket_destination;
    String[] ticket_rate;
    String[] ticket_kilometer;
    String[] ticket_no;
    String[] ticket_name;

    String counductor_name_string,bus_number_string,conductor_image_ticket,user_image;
    ImageView cut;
    ImageView user_image_ticket,conductor_image;
    TextView counductor_name,ticket_details_source,ticket_details_destination,ticket_details_time,ticket_details_date,ticket_details_bus_number,ticket_details_total_kilometer,ticket_details_no_ticket;

    Float x1,x2,y1,y2;
    RecyclerView ticket_list;
    RecyclerView.Adapter ProgramAdapter;
    RecyclerView.LayoutManager layoutManager;
    

    private AlertDialog.Builder dialog_builder;
    private AlertDialog dialog;

    String url = Login.geturl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        getSupportActionBar().hide();

        ticket_list=findViewById(R.id.ticket_list);
        layoutManager=new LinearLayoutManager(this);
        ticket_list.setLayoutManager(layoutManager);
        call();

    }

    private void call() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DisplayTicketApi api = retrofit.create(DisplayTicketApi.class);
        Call<List<Ticket_Model>> call = api.Ticket_Model();
        call.enqueue(new Callback<List<Ticket_Model>>() {
            @Override
            public void onResponse(Call<List<Ticket_Model>> call, retrofit2.Response<List<Ticket_Model>> response) {
                List<Ticket_Model> data = response.body();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getUser_id().trim().equalsIgnoreCase(UserIndex.get_userid().trim())) {
                        ticketid[j]=data.get(i).getTicket_id();
                        source[j]=data.get(i).getSource();
                        destination[j]=data.get(i).getDestination();
                        ticketdate[j]=data.get(i).getDate();
                        workerid[j]=data.get(i).getWorker_id();
                        ticketrate[j]=data.get(i).getTicket_rate();
                        ticketkilometer[j]=data.get(i).getTotal_kilometer();
                        ticketno[j]=data.get(i).getNo_ticket();
                        tname[j]=data.get(i).getSource()+" to "+data.get(i).getDestination();
                        tickettime[j]=data.get(i).getTime();
                        busid[j]=data.get(i).getBus_id();
                        j++;
                    }
                }

                setdata();
                createActivity();
            }

            @Override
            public void onFailure(Call<List<Ticket_Model>> call, Throwable t) {

            }
        });
    }

    private void setdata() {
        ticket_id=new String[j];
        ticket_time=new String[j];
        ticket_date=new String[j];
        bus_id=new String[j];
        worker_id=new String[j];
        ticket_source=new String[j];
        ticket_destination=new String[j];
        ticket_rate=new String[j];
        ticket_kilometer=new String[j];
        ticket_no=new String[j];
        ticket_name=new String[j];

        for(int i=0;i<j;i++){
            ticket_name[i]=tname[i];
            ticket_id[i]=ticketid[i];
            ticket_time[i]=tickettime[i];
            ticket_date[i]=ticketdate[i];
            ticket_source[i]=source[i];
            ticket_destination[i]=destination[i];
            ticket_rate[i]=ticketrate[i];
            ticket_kilometer[i]=ticketkilometer[i];
            ticket_no[i]=ticketno[i];
            worker_id[i]=workerid[i];
            bus_id[i]=busid[i];
        }

    }
    private void createActivity() {
        ProgramAdapter=new ProgramAdapter3(this,this,ticket_name,ticket_date,ticket_time);
        ticket_list.setAdapter(ProgramAdapter);
    }




    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1  < x2){
                    Intent i = new Intent(TicketList.this, UserPanel.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();

                }else if(x1 > x2){
                    Toast.makeText(getApplicationContext(),"Nothing There",Toast.LENGTH_LONG).show();

                }
                break;
        }
        return false;
    }

    @Override
    public void getTicket(int position) {
        showTicketDetails(position);
    }

    private void showTicketDetails(int position) {
        dialog_builder=new AlertDialog.Builder(this);
        final View ticket_detail_popup=getLayoutInflater().inflate(R.layout.ticket_details,null);
        dialog_builder.setView(ticket_detail_popup);
        dialog =dialog_builder.create();
        dialog.show();
        cut=ticket_detail_popup.findViewById(R.id.cut);
        user_image_ticket=ticket_detail_popup.findViewById(R.id.user_image_ticket);
        counductor_name=ticket_detail_popup.findViewById(R.id.ticket_details_conductor_name);
        conductor_image=ticket_detail_popup.findViewById(R.id.conductor_image_ticket_details);
        ticket_details_source=ticket_detail_popup.findViewById(R.id.ticket_details_source);
        ticket_details_destination=ticket_detail_popup.findViewById(R.id.ticket_details_destination);
        ticket_details_time=ticket_detail_popup.findViewById(R.id.ticket_details_time);
        ticket_details_date=ticket_detail_popup.findViewById(R.id.ticket_details_date);
        ticket_details_bus_number=ticket_detail_popup.findViewById(R.id.ticket_details_bus_number);
        ticket_details_total_kilometer=ticket_detail_popup.findViewById(R.id.ticket_details_total_kilometer);
        ticket_details_no_ticket=ticket_detail_popup.findViewById(R.id.ticket_details_no_ticket);
        set_bus_details(position);
        set_conductor_details(position);
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



    private void set_bus_details(int position) {

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
                    if (data.get(i).getBus_id().trim().equalsIgnoreCase(bus_id[position])) {
                            bus_number_string=data.get(i).getBus_number();
                    }
                }
            }


            @Override
            public void onFailure(Call<List<Get_Bus_Model>> call, Throwable t) {

            }
        });

        }

    private void set_conductor_details(int position) {
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
                int j = 0;
                for (int i = 0; i < data.size(); i++) {
                    if(data.get(i).getWorker_id().trim().equals(worker_id[position].trim())){
                        counductor_name_string=data.get(i).getWorker_name();
                        conductor_image_ticket=data.get(i).getImage();
                    }
                }
                setdata_ticket(position);
            }
            @Override
            public void onFailure(Call<List<Display_Conductor_Model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " Lelo ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setdata_ticket(int position) {

              Glide
                .with(TicketList.this)
                .load((Login.geturl()+"/images/"+user_image))
                .centerCrop()
                .placeholder(R.drawable.profile)
                .into(user_image_ticket);

              Glide
                .with(TicketList.this)
                .load((Login.geturl()+"/images/"+conductor_image_ticket))
                .centerCrop()
                .placeholder(R.drawable.conductor_logo)
                .into(conductor_image);

        counductor_name.setText(counductor_name_string);
        ticket_details_source.setText(source[position]);
        ticket_details_destination.setText(destination[position]);
        ticket_details_time.setText(ticket_time[position]);
        ticket_details_date.setText(ticket_date[position]);
        ticket_details_bus_number.setText(bus_number_string);
        ticket_details_total_kilometer.setText(ticket_kilometer[position]);
        ticket_details_no_ticket.setText(ticket_no[position]);

    }
}