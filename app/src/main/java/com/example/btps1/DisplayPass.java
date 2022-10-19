package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import androidmads.library.qrgenearator.QRGEncoder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayPass extends AppCompatActivity  {

    ImageView status;
    ProgressBar p_Bar;
    TextView level;
    TextView pass_source, pass_destination, starting_date,ending_date,days_remained;
    Button payment_button,scan_pass,renew_pass;
    Boolean success;
    LinearLayout verification_failed;
    String url = Login.geturl();
    String passurl = Login.geturl()+"pass.php";
    Float x1,x2,y1,y2;
    LinearLayout pass_created;
    static int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pass);
        getSupportActionBar().hide();
        verification_failed=(LinearLayout)findViewById(R.id.verification_failed);
        pass_source=findViewById(R.id.pass_source);
        pass_destination=findViewById(R.id.pass_destination);
        ending_date=findViewById(R.id.ending_date);
        days_remained=findViewById(R.id.days_remained);
        starting_date = findViewById(R.id.starting_date);
        status=findViewById(R.id.status_pass_verified);
        scan_pass=findViewById(R.id.scan_pass);
        renew_pass=findViewById(R.id.renew_pass);
        p_Bar=findViewById(R.id.p_Bar);
        level=findViewById(R.id.level);
        pass_created=(LinearLayout)findViewById(R.id.pass_created);
        Intent intent = new Intent();
        success = intent.getBooleanExtra("done", false);
        payment_button=findViewById(R.id.payment_button);
        Display();
        payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Payment.class);
                startActivity(intent);
            }
        });
    }

    private void Display() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ViewPassApi api = retrofit.create(ViewPassApi.class);
        Call<List<Get_Pass_Model>> call = api.Get_Pass_Model();
        call.enqueue(new Callback<List<Get_Pass_Model>>() {
            @Override
            public void onResponse(Call<List<Get_Pass_Model>> call, retrofit2.Response<List<Get_Pass_Model>> response) {
                List<Get_Pass_Model> data = response.body();

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getUser_id().equals(UserIndex.get_userid())) {

                        if(data.get(i).getPass_status().equals("0")){
                            verification_failed.setVisibility(View.VISIBLE);
                        }
                        if(data.get(i).getPass_status().equals("1")){

                            status.setImageResource(R.drawable.emoji_sad);
                            level.setText("1/3 Level Reached");
                            p_Bar.setProgress(1);
                            progress=1;


                        }else if(data.get(i).getPass_status().equals("2")){

                            status.setImageResource(R.drawable.emoji_normal);
                            level.setText("2/3 Level Reached");
                            p_Bar.setProgress(2);
                            progress=2;
                        }else{
                            status.setImageResource(R.drawable.emoji_happy);
                            level.setText("3/3 Level Reached");
                            p_Bar.setProgress(3);
                            //pass_created.setVisibility(View.VISIBLE);
                            progress=3;
                            payment_button.setVisibility(View.VISIBLE);


                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<List<Get_Pass_Model>> call, Throwable t) {

            }
        });

    }
/*

    private void StoreDetails() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, passurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Pass Stored", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(DisplayPass.this, com.example.btps1.UserPanel.class);
                    //startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error storing pass" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("source", pass_source);
                params.put("destination", pass_destination);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
*/


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

                    Intent i = new Intent(DisplayPass.this,Pass.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                }else if(x1 > x2){
                    recreate();

                }
                break;
        }
        return false;
    }




}