package com.example.btps1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Ticket extends AppCompatActivity  {

    EditText source_details, destination_details,bus_number_details, ticket_details;
    Button create_ticket;
    String tickets;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    LinearLayout get_no_tickets,select_payment;
    RadioButton online,offline;
    TextView rupees;
    Button get_qr;
    String payment;
    ImageView show_qr;
    private AlertDialog.Builder dialog_builder;
    private AlertDialog dialog;
    int i=0;
    String srcLatitude,srcLongitude,desLatitude,desLongitude;
    String stype;
    double kilometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        getSupportActionBar().hide();
        source_details=findViewById(R.id.source_details);
        destination_details=findViewById(R.id.destination_details);
        bus_number_details=findViewById(R.id.bus_number_details);
        ticket_details=findViewById(R.id.ticket_details);
        create_ticket=findViewById(R.id.create_ticket);
        source_details.setText(UserPanel.getsource());
        destination_details.setText(Select_Destination.getDestination());

        stype="source";
        Geolocation geolocation=new Geolocation();
        geolocation.getAddress(source_details.getText().toString(),getApplicationContext(),new Ticket.GeoHandler());
        geolocation.getAddress(destination_details.getText().toString(),getApplicationContext(),new Ticket.GeoHandler());

        bus_number_details.setText(SelectBus.get_bus_number());
        get_no_tickets=(LinearLayout)findViewById(R.id.get_no_tickets);
        select_payment=(LinearLayout)findViewById(R.id.select_payment);
        online=findViewById(R.id.online);
        offline=findViewById(R.id.offline);
        rupees=findViewById(R.id.rupees);
        get_qr=findViewById(R.id.get_qr);
        create_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickets=ticket_details.getText().toString();
                if(tickets.matches("")){
                    Toast.makeText(getApplicationContext(),"Please Enter No. of Tickets",Toast.LENGTH_SHORT).show();
                }else{
                    get_no_tickets.setVisibility(View.GONE);
                    select_payment.setVisibility(View.VISIBLE);
                    }

            }
        });
        get_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(offline.isChecked()){
                    payment="offline";
                    Toast.makeText(getApplicationContext(),payment,Toast.LENGTH_SHORT).show();
                }
                if(online.isChecked()){
                    payment="online";
                    Toast.makeText(getApplicationContext(),payment,Toast.LENGTH_SHORT).show();
                }
                show_qr();
            }
        });
    }

    private void show_qr() {
        dialog_builder=new AlertDialog.Builder(this);
        final View show_qr_popup=getLayoutInflater().inflate(R.layout.show_qr,null);
        dialog_builder.setView(show_qr_popup);
        dialog =dialog_builder.create();
        dialog.show();
        show_qr=show_qr_popup.findViewById(R.id.showqrcode);
        MultiFormatWriter writer=new MultiFormatWriter();
        try {
            BitMatrix bitMatrix=writer.encode(/*DATA of QR CODE*/UserIndex.get_userid(), BarcodeFormat.QR_CODE,350,350);
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            Bitmap bitmap= barcodeEncoder.createBitmap(bitMatrix);
            show_qr.setImageBitmap(bitmap);
            InputMethodManager manager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    public String get_no_ticket(){
        return tickets;
    }



    private class GeoHandler extends Handler {
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
                    i = 0;
                    kilometer = calculateDistance(srcLatitude, srcLongitude, desLatitude, desLongitude);
                    Toast.makeText(getApplicationContext(),"kilometer"+kilometer,Toast.LENGTH_SHORT).show();
                    double rup=kilometer*0.70;
                    rupees.setText("Total Rupees To Pay: "+String.valueOf(rup));
                }

            }else{
                Toast.makeText(getApplicationContext(),"Something went Wrong!",Toast.LENGTH_SHORT).show();
            }
        }
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
}
