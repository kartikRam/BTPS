package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText email, pswd;
    static String url1,uid;
    static String uemail;
    String password;
    ViewFlipper flipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        flipper=(ViewFlipper) findViewById(R.id.flipper);
        url1 = "http://192.168.43.57/btps_signup_login/";
        int imgarray[]={R.drawable.buslogo1,R.drawable.buslogo2,R.drawable.buslogo3};
        email = findViewById(R.id.user_email);
        pswd = findViewById(R.id.lpassword);

        for (int i=0;i<imgarray.length;i++){
            showimage(imgarray[i]);
        }

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
    public static String getEmail() {
        return uemail;
    }
    public static String geturl(){return url1;}



    public void loginbtn(View view) {
        uemail = email.getText().toString();
        password = pswd.getText().toString();
        if (uemail.equals("") || password.equals("")) {
            Toast.makeText(this, "Please Enter all Information", Toast.LENGTH_LONG).show();
        } else {

            String url = url1+"login.php";

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equals("user")) {
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this, UserIndex.class);
                        intent.putExtra("email", uemail);
                        startActivity(intent);
                    }else if(response.equals("institute")){
                        Intent intent = new Intent(Login.this, InstituteIndex.class);
                        intent.putExtra("institute_email",uemail);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Institute",Toast.LENGTH_SHORT).show();
                    }else if(response.equals("depo-manager")){
                        Intent intent = new Intent(Login.this, DepoManagerIndex.class);
                        intent.putExtra("email",uemail);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Depo-Manager",Toast.LENGTH_SHORT).show();
                    }else if(response.equals("conductor")){
                        Intent intent = new Intent(Login.this, ConductorIndex.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Conductor",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Sorry Wrong Email or password",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Login Error" + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_email", uemail);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }

    public void show_signup(View view) {
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
        Login.this.finish();
    }

    public void show_forgetpassword(View view) {

        Intent intent = new Intent(Login.this, Forget_Password.class);
        startActivity(intent);
        Login.this.finish();
    }
}