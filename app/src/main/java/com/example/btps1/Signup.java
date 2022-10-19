package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    EditText name, email, password, city, phone_no,confirm_password;
    Button btn_signup;
    String confirm,name1,password1,email1,city1,phone_no1;

    String URL_REGIST = Login.geturl()+"register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        confirm_password=findViewById(R.id.userconfirmpassword);
        name = findViewById(R.id.uname);
        email = findViewById(R.id.useremail);
        password = findViewById(R.id.userpassword);
        city = findViewById(R.id.usercity);
        phone_no = findViewById(R.id.userphoneno);
        btn_signup = findViewById(R.id.signup_button2);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 name1 = name.getText().toString().trim();
                 password1 = password.getText().toString().trim();
                email1 = email.getText().toString().trim();
                city1 = city.getText().toString().trim();
                phone_no1 = phone_no.getText().toString().trim();
                confirm=confirm_password.getText().toString().trim();


                if (name1.matches("")||password1.matches("")||email1.matches("")||city1.matches("")||phone_no1.matches("")){
                    Toast.makeText(getApplicationContext(),"Please Fill all details",Toast.LENGTH_LONG).show();
                }else if(!(confirm.matches(password1))){
                    Toast.makeText(getApplicationContext(),"Please verify Password and Confirm Password",Toast.LENGTH_SHORT).show();
                }else{
                    Regist();

                }

            }
        });

    }

    private void Regist() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Sign up Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.this, com.example.btps1.Login.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Sign up Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name1);
                params.put("password", password1);
                params.put("email", email1);
                params.put("city", city1);
                params.put("phone_no", phone_no1);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}