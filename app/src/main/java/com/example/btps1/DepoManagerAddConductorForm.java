package com.example.btps1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class DepoManagerAddConductorForm extends AppCompatActivity {

    EditText add_conductor_email, add_conductor_password, add_conductor_confirm_password,add_conductor_name,add_conductor_branch_name;
    Button add_conductor_submit;
    String email, password, confirm_password,name,branch_name;
    String url = Login.geturl() + "addconductor.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_depo_manager_add_conductor_form);
        add_conductor_email = findViewById(R.id.add_conductor_email);
        add_conductor_password = findViewById(R.id.add_conductor_password);
        add_conductor_confirm_password = findViewById(R.id.add_conductor_confirm_password);
        add_conductor_name = findViewById(R.id.add_conductor_name);
        add_conductor_branch_name = findViewById(R.id.add_conductor_branch_name);
        add_conductor_submit=findViewById(R.id.add_conductor_submit);
        add_conductor_branch_name.setText(DepoManagerIndex.getManagerBranch());

        add_conductor_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = add_conductor_email.getText().toString();
                password = add_conductor_password.getText().toString();
                confirm_password =add_conductor_confirm_password.getText().toString();
                name=add_conductor_name.getText().toString();

                if (confirm_password.matches(password)) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(getApplicationContext(), "Conductor Added Successfully", Toast.LENGTH_SHORT).show();

                                add_conductor_email.setText("");
                                add_conductor_password.setText("");
                                add_conductor_confirm_password.setText("");
                                add_conductor_name.setText("");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Process Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", email);
                            params.put("password", password);
                            params.put("depo_manager_id", DepoManagerIndex.getManagerId());
                            params.put("name", name);
                            params.put("branch_name", DepoManagerIndex.getManagerBranch());
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(getApplicationContext(), "Please Check the password again!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}