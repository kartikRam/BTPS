package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Show_buses_hire extends AppCompatActivity implements Hire_Bus_View_Adapter.getItemSelected {


    RecyclerView bus_list_hire;
    RecyclerView.Adapter ProgramAdapter;
    RecyclerView.LayoutManager layoutManager;

    String start_date,end_date,station;
    String[] bus_number=new String[150];
    String[] BusNumber;
    int days;
    int rupees;
    String[] bus_id=new String[150];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buses_hire);
        getSupportActionBar().hide();
        start_date=Hire.getStart_date();
        end_date=Hire.getEnd_date();
        station=Hire.get_Station();
        bus_list_hire=(RecyclerView)findViewById(R.id.bus_list_hire);
        bus_list_hire.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        bus_list_hire.setLayoutManager(layoutManager);
        days=Integer.parseInt(Hire.get_days());
        rupees=100*24*days;
        call();

    }

    private void call() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Login.geturl())
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
                    String destination=data.get(i).getDestination().trim();

                    if (sourcetemp.equalsIgnoreCase(data.get(i).getBranch_name())) {
                        if(data.get(i).getBus_hire().equals("0")){
                            bus_number[j]=data.get(i).getBus_number();
                            bus_id[j]=data.get(i).getBus_id();
                            j++;
                        }
                    }
                }callRecyclerView(j);
            }

            @Override
            public void onFailure(Call<List<Get_Bus_Model>> call, Throwable t) {

            }
        });

    }

    private void callRecyclerView(int j) {
        BusNumber=new String[j];
        for(int i=0;i<j;i++){
            BusNumber[i]=bus_number[i];
        }


        ProgramAdapter=new Hire_Bus_View_Adapter(this,this,BusNumber,rupees);
        bus_list_hire.setAdapter(ProgramAdapter);
    }

    @Override
    public void BusClick(int position) {
        Toast.makeText(getApplicationContext(),"YOU CLICKED "+position,Toast.LENGTH_SHORT).show();
    }
}