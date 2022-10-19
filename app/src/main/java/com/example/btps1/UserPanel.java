package com.example.btps1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserPanel extends AppCompatActivity implements ProgramAdapter.clicked {

    ArrayAdapter<String> arrayAdapter;
    TextView usersource;
    Context context;
    LocationManager locationmanager;
    private Location mobileLocation;
    private double longitude, latitude;
    static String source;
    static String destination;
    static  String user_source;
    Button next;
    static String email;
    Float x1,x2,y1,y2;
    ListView listView;
    String[] cityname = {"Mehsana", "Ahemadabad", "Gandhinagar", "Visnagar", "Gozaria", "Lakhvad"};
    String[] citydescription = {"Mehsana Gujrat India", "Ahemadabad Gujrat India", "Gandhinagar Gujrat India", "Visnagar Gujrat India", "Gozaria Gujrat India", "Lakhvad Gujrat India"};
    int[] images = {R.drawable.mehsana, R.drawable.ahmedabad, R.drawable.gandhinagar, R.drawable.visnagar, R.drawable.gozaria, R.drawable.lakhvad};

    Switch sb = null;
    FusedLocationProviderClient mFusedLocationClient;

    // Initializing other items
    // from layout file
    TextView latitudeTextView, longitTextView;
    int PERMISSION_ID = 44;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout sourcelist = findViewById(R.id.sourceList);
        setContentView(R.layout.activity_user_panel);
        getSupportActionBar().hide();
        listView = findViewById(R.id.my_list);
        usersource = findViewById(R.id.usersource);
        next=findViewById(R.id.next);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sb = new Switch(this);
        sb.setTextOff("OFF");
        sb.setTextOn("ON");
        sb.setChecked(true);
        showsource();
        Switch sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listView.setVisibility(View.INVISIBLE);
                    usersource.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    getLastLocation();
                    findAddress(longitude,latitude);

                  } else {

                    usersource.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(source.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please wait! Location is not detected yet",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(UserPanel.this, com.example.btps1.Select_Destination.class);
                    intent.putExtra("source", source);
                    startActivity(intent);
                }

            }
        });
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            Toast.makeText(getApplicationContext(),""+location.getLongitude()+"  "+location.getLatitude(),Toast.LENGTH_SHORT).show();
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude=mLastLocation.getLatitude();
            longitude=mLastLocation.getLongitude();
        }
    };

    private void findAddress(double longitude,double latitude) {
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            Toast.makeText(getApplicationContext(),"top",Toast.LENGTH_SHORT).show();
            addresses = gcd.getFromLocation(latitude, longitude, 1);
            Toast.makeText(getApplicationContext(),"down ",Toast.LENGTH_SHORT).show();
            source=addresses.get(0).getLocality();
        } catch (Exception e) {
            e.printStackTrace();
        }

        usersource.setText(source);
    }


    // method to check for permissions
    private boolean checkPermissions() {


        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
    public void showsource() {

        List<String> mylist = new ArrayList<>();
        ProgramAdapter programAdapter=new ProgramAdapter(this,this,cityname,images,citydescription);
        listView.setAdapter(programAdapter);

    }

    public static String getsource() {
        return source;
    }

    public static String getemail() {
        return email;
    }



    //public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.my_menu, menu);
        //MenuItem menuItem = menu.findItem(R.id.search_icon);
        //SearchView searchView = (SearchView) menuItem.getActionView();
        //searchView.setQueryHint("Where To Go!");

  /*      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }*/





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
                    Toast.makeText(getApplicationContext(),"Nothing There",Toast.LENGTH_LONG).show();
                }else if(x1 > x2){

                    Intent i = new Intent(UserPanel.this, TicketList.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
                break;
        }
        return false;
    }

    @Override
    public void selected(int position) {
        Intent intent=new Intent(getApplicationContext(),Select_Destination.class);
        source=cityname[position];
        startActivity(intent);
    }
}