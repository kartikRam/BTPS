package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Hire extends AppCompatActivity {

    EditText start_date_hire_bus,end_date_hire_bus,station_hire_bus;
    Button show_buses;
    static String start_date,end_date,station;
    Date date1,date2;
    static String dayDifference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire);
        getSupportActionBar().hide();
        start_date_hire_bus=findViewById(R.id.start_date_hire_bus);
        end_date_hire_bus=findViewById(R.id.end_date_hire_bus);
        station_hire_bus=findViewById(R.id.station_hire_bus);
        show_buses=findViewById(R.id.show_buses);
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int days=calendar.get(Calendar.DAY_OF_MONTH);
        start_date_hire_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Hire.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String sDate=dayOfMonth+"/"+month+"/"+year;
                        start_date_hire_bus.setText(sDate);
                    }
                },year,month,days
                );
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });
        end_date_hire_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Hire.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String sDate=dayOfMonth+"/"+month+"/"+year;
                        end_date_hire_bus.setText(sDate);
                    }
                },year,month,days
                );
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        show_buses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start_date=start_date_hire_bus.getText().toString();
                end_date=end_date_hire_bus.getText().toString();
                station=station_hire_bus.getText().toString();
                if(start_date.matches("")||end_date.matches("")||station.matches("")){
                    Toast.makeText(getApplicationContext(),"Please Fill all details",Toast.LENGTH_SHORT).show();
                }else{
                    SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        date1 = dates.parse(start_date);
                        date2 = dates.parse(end_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);
                    dayDifference = Long.toString(differenceDates);

                    Intent intent =new Intent(getApplicationContext(),Show_buses_hire.class);
                    startActivity(intent);
                }

            }
        });


    }


    public static String getStart_date(){
        return start_date;
    }
    public static String getEnd_date(){
        return end_date;
    }
    public static String get_Station(){
        return station;
    }

    public static String get_days(){
        return dayDifference;
    }
}