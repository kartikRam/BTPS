package com.example.btps1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class InstituteAddStudentForm extends AppCompatActivity {

    String email, password, confirm_password, institute_email, enrollment,name;
    String URL_REGIST = Login.geturl()+"addstudent.php";
    EditText add_student_enrollment_no,add_student_name,add_student_email,add_student_password,add_student_confirm_password;
    Button add_student_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_add_student_form);
        getSupportActionBar().hide();
        add_student_enrollment_no=findViewById(R.id.add_student_enrollment_no);
        add_student_name=findViewById(R.id.add_student_name);
        add_student_email=findViewById(R.id.add_student_email);
        add_student_password=findViewById(R.id.add_student_password);
        add_student_confirm_password=findViewById(R.id.add_student_confirm_password);
        add_student_submit=findViewById(R.id.add_student_submit);
        add_student_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = add_student_email.getText().toString();
                password = add_student_password.getText().toString();
                confirm_password = add_student_confirm_password.getText().toString();
                institute_email = Login.getEmail();
                enrollment = add_student_enrollment_no.getText().toString();
                name=add_student_name.getText().toString();
                if (email.matches("") || password.matches("") || enrollment.matches("") || confirm_password.matches("")||name.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Data !", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirm_password)) {
                        addstudent();
                        add_student_enrollment_no.setText("");
                        add_student_confirm_password.setText("");
                        add_student_password.setText("");
                        add_student_email.setText("");
                        add_student_name.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Please check Password and Confirm Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void addstudent() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Student Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Add Student Failed" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("institute_email", institute_email);
                params.put("enrollment", enrollment);
                params.put("name", name);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }


}