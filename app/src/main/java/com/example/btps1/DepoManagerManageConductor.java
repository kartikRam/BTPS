package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DepoManagerManageConductor extends AppCompatActivity {

    CardView depo_manager_add_conductor,depo_manager_edit_conductor,depo_manager_remove_conductor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_manager_manage_conductor);
        getSupportActionBar().hide();
        depo_manager_add_conductor=findViewById(R.id.depo_manager_add_conductor);
        depo_manager_edit_conductor=findViewById(R.id.depo_manager_edit_conductor);
        depo_manager_remove_conductor=findViewById(R.id.depo_manager_remove_conductor);
        depo_manager_remove_conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),DepoManagerConductor.class);
                startActivity(intent);
            }
        });
        depo_manager_edit_conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),DepoManagerEditConductor.class);
                startActivity(intent);
            }
        });
        depo_manager_add_conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DepoManagerAddConductorForm.class);
                startActivity(intent);
            }
        });

    }
}