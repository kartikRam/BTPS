package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Select_Destination extends AppCompatActivity implements ProgramAdapter1.get_destination {

    String[] cityname = {"Mehsana", "Ahemadabad", "Gandhinagar", "Visnagar", "Gozaria", "Lakhvad"};
    String[] citydescription = {"Mehsana Gujrat India", "Ahemadabad Gujrat India", "Gandhinagar Gujrat India", "Visnagar Gujrat India", "Gozaria Gujrat India", "Lakhvad Gujrat India"};
    int[] images = {R.drawable.mehsana, R.drawable.ahmedabad, R.drawable.gandhinagar, R.drawable.visnagar, R.drawable.gozaria, R.drawable.lakhvad};
    ListView listView;
    static String source;
    static String destination;
    String pos;

    int position;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__destination2);
        getSupportActionBar().hide();
        listView=findViewById(R.id.my_list);

        intent=getIntent();
        source=intent.getStringExtra("source");
       // pos=intent.getStringExtra("position");
        showdestination();

    }
    public static String getselectedsource(){
        return source;
    }
    public static String getDestination(){
        return destination;
    }

    public void showdestination() {
        ProgramAdapter1 programAdapter=new ProgramAdapter1(this,this,cityname,images,citydescription);
        listView.setAdapter(programAdapter);

    }

    @Override
    public void selected(int position) {
        Intent intent=new Intent(getApplicationContext(),SelectBus.class);
        destination=cityname[position];
        startActivity(intent);
    }
}