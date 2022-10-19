package com.example.btps1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Pass extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static String pass_source,pass_destination,no_of_months,pass_type;
    EditText user_type,rupees,total_kilometer_pass;
    static String pass_kilometer;
    RadioButton local_pass_type,express_pass_type;
    Button show_pass_type,show_pass_payment;
    Spinner months;
    String[] months_array={"1 Month","2 Month","3 Month","6 Month","12 Month"};
    EditText source_detail_pass,destination_detail_pass;
    LinearLayout pass_details,select_type,show_payment;
    String[] city={"Mehsana","Gandhinagar","Ahemadabad","Gozaria","Surat","Junagadh","Vadodra","Sidhappur"};
    float x1;
    float x2;
    float y1;
    float y2;
    int i=0;
    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private String current_date;
    String end_date;
    List<Place.Field> fields;
    static String apiKey="AIzaSyAbPUmwXunzAU6AdlG0fXQaKV0hvTAMQ7g";
    String desLatitude="",desLongitude="";
    String srcLongitude="",srcLatitude="";
    int days = 0;
    Double kilometer;
    String encodeImageString;
    Bitmap bitmap;

    double lat1,lat2,log1,log2;
    String stype;
    int counter=0, flag=0;
    LinearLayout ask_id_card;
    Button store_pass_student,store_pass;
    ImageView identity_card;
    TextView marquee_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        getSupportActionBar().hide();
        months=(Spinner) findViewById(R.id.months);
        months.setOnItemSelectedListener( this);
        pass_details=(LinearLayout)findViewById(R.id.pass_details);
        select_type=(LinearLayout)findViewById(R.id.select_type);
        show_payment=(LinearLayout)findViewById(R.id.show_payment);
        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),months_array);
        months.setAdapter(customAdapter);
        ask_id_card=(LinearLayout)findViewById(R.id.ask_id_card);
        store_pass_student=findViewById(R.id.store_pass_student);
        marquee_text=findViewById(R.id.marquee_text);
        marquee_text.setSelected(true);
        store_pass=findViewById(R.id.store_pass);
        identity_card=findViewById(R.id.identity_card);
        source_detail_pass=findViewById(R.id.source_details_bus_pass);
        destination_detail_pass=findViewById(R.id.destination_details_bus_pass);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, city);
        source_detail_pass.setAdapter(adapter);
        destination_detail_pass.setAdapter(adapter);
        */user_type=findViewById(R.id.user_type);
        rupees=findViewById(R.id.rupees);
        total_kilometer_pass=findViewById(R.id.total_kilometer_pass);
        local_pass_type=findViewById(R.id.local_pass_type);
        express_pass_type=findViewById(R.id.express_pass_type);
        show_pass_type=findViewById(R.id.show_pass_type);
        show_pass_payment=findViewById(R.id.show_pass_payment);
        user_type.setText(UserIndex.get_usertype());
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        current_date = dateFormat.format(calendar.getTime());

        store_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"onStore_Pass Clicked",Toast.LENGTH_SHORT).show();
                if (UserIndex.get_usertype().equals("Student")) {
                    show_payment.setVisibility(View.GONE);
                    ask_id_card.setVisibility(View.VISIBLE);

                    Toast.makeText(getApplicationContext(),"onStore_Pass Student",Toast.LENGTH_SHORT).show();
                }else{
                    String url = Login.geturl() + "store_pass_student.php";
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equals("success")) {
                                Toast.makeText(getApplicationContext(), "Pass sent to Verification", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "error:" + response, Toast.LENGTH_SHORT).show();
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
                            params.put("source", source_detail_pass.getText().toString());
                            params.put("destination", destination_detail_pass.getText().toString());
                            params.put("days", String.valueOf(days));
                            params.put("pass_type", pass_type);
                            params.put("total_kilometer", total_kilometer_pass.getText().toString());
                            params.put("rupees", rupees.getText().toString());
                            params.put("submit_date", current_date);
                            params.put("uid", UserIndex.get_userid());
                            params.put("utype", "0");
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                }


            }
        });


        show_pass_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_source=source_detail_pass.getText().toString();
                pass_destination=destination_detail_pass.getText().toString();
                String src=source_detail_pass.getText().toString();
                String des=destination_detail_pass.getText().toString();
                if(pass_source.matches("")||pass_destination.matches("")){
                    Toast.makeText(getApplicationContext(),"Please Fill all details",Toast.LENGTH_SHORT).show();
                }else{
                 stype="source";
                 Geolocation geolocation=new Geolocation();
                 geolocation.getAddress(src,getApplicationContext(),new GeoHandler());
                 geolocation.getAddress(des,getApplicationContext(),new GeoHandler());


                }
            }
        });
        show_pass_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(local_pass_type.isChecked()){
                    pass_type="local";
                    if(user_type.getText().equals("Student")) {
                        Double rps = 12.5  * days;
                        Toast.makeText(getApplicationContext(), rps.toString(), Toast.LENGTH_SHORT).show();
                        rupees.setText(rps.toString());
                    }else{
                         double rps1= 20.12  * days;
                        rupees.setText( String.valueOf(rps1));
                    }

                }else{
                    pass_type="express";
                    if(user_type.getText().equals("Student")) {
                        Double rps =  0.25*3  * days;
                        Toast.makeText(getApplicationContext(), rps.toString(), Toast.LENGTH_SHORT).show();
                        rupees.setText(rps.toString());
                    }else{
                        Double rps =  26.67  * days;
                        Toast.makeText(getApplicationContext(), rps.toString(), Toast.LENGTH_SHORT).show();
                        rupees.setText(rps.toString());

                    }
                }
                select_type.setVisibility(View.GONE);
                show_payment.setVisibility(View.VISIBLE);
            }
        });


    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        no_of_months=months_array[position];
        if(position==0){
            days=1*30;
        }
        if(position==1){
            days=2*30;
        }
        if(position==2){
            days=3*30;
        }
        if(position==3){
            days=6*30;
        }
        if(position==4){
            days=12*30;
        }
        
        end_date=addDays(current_date,days);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static String get_passsource() {
        return pass_source;
    }

    public static String get_passdestination() {
        return pass_destination;
    }

    public static String get_passamount() {
        return pass_kilometer;
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
                    Toast.makeText(getApplicationContext(),"Nothing There",Toast.LENGTH_LONG).show();
                }else if(x1 > x2){

                    Intent i = new Intent(Pass.this, DisplayPass.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
                break;
        }
        return false;
    }


    public static String addDays(String startDate,int numberOfDays) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy",  Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_WEEK,numberOfDays);
        String resultDate=dateFormat.format(c.getTime());
        return resultDate;
    }

    public static double calculateDistance(String  startLatitude, String  startLongitude, String  endLatitude, String  endLongitude) {
        float[] results = new float[3];
        Double sLatitude,sLongitude,eLatitude,eLongitude;
        sLatitude=Double.parseDouble(startLatitude);
        sLongitude=Double.parseDouble(startLongitude);
        eLatitude=Double.parseDouble(endLatitude);
        eLongitude=Double.parseDouble(endLongitude);
        Location.distanceBetween(sLatitude, sLongitude, eLatitude, eLongitude, results);
        return (results[0]/1000);
    }



    private class GeoHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {

            String address;
            switch(msg.what){
                case 1:
                    Bundle bundle=msg.getData();
                    address=bundle.getString("address");
                    break;
                default:
                    address=null;
            }
            if(address!=null) {
                if (stype.equals("source")) {
                    String src = address;
                    String[] temp1 = src.split(",");
                    srcLatitude = temp1[0];
                    srcLongitude = temp1[1];
                    i++;
                    stype="destination";

                } else {
                    String des = address;
                    String[] temp = des.split(",");
                    desLatitude = temp[0];
                    desLongitude = temp[1];
                    i++;
                }
                if(i==2) {
                    i=0;
                    kilometer = calculateDistance(srcLatitude, srcLongitude, desLatitude, desLongitude);
                    Toast.makeText(getApplicationContext(),kilometer.toString(),Toast.LENGTH_SHORT).show();
                    /*if(kilometer>1.0) {
                    */    total_kilometer_pass.setText(kilometer.toString());
                        pass_details.setVisibility(View.GONE);
                        select_type.setVisibility(View.VISIBLE);
                    /*}else{*/
                        //Toast.makeText(getApplicationContext(),"Could not make Pass for Same source and Destination",Toast.LENGTH_SHORT).show();
                    /*}*/
                }
            }else{
                Toast.makeText(getApplicationContext(),"Please Verify the Source And Destination again",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                identity_card.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (Exception ex) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
        counter=1;
    }



    public void upload_data_to_db(View view) {

        if(counter==1) {
            String url = Login.geturl() + "store_pass_student.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Pass sent to Verification", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "error:" + response, Toast.LENGTH_SHORT).show();
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
                    params.put("source", source_detail_pass.getText().toString());
                    params.put("destination", destination_detail_pass.getText().toString());
                    params.put("days", String.valueOf(days));
                    params.put("pass_type", pass_type);
                    params.put("total_kilometer", total_kilometer_pass.getText().toString());
                    params.put("rupees", rupees.getText().toString());
                    params.put("uid", UserIndex.get_userid());
                    params.put("utype", "1");
                    params.put("id_card",encodeImageString);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);


        }
    }


    public void saveData(View view) {
        Dexter.withActivity(Pass.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

}