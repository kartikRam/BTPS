package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DepoManagerManageBus extends AppCompatActivity {

    CardView depo_manager_add_bus,depo_manager_edit_bus,depo_manager_remove_bus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_manager_manage_bus);
        getSupportActionBar().hide();
        depo_manager_add_bus=findViewById(R.id.depo_manager_add_bus);
        depo_manager_edit_bus=findViewById(R.id.depo_manager_edit_bus);
        depo_manager_remove_bus=findViewById(R.id.depo_manager_remove_bus);

        depo_manager_remove_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DepoManagerBus.class);
                startActivity(intent);
            }
        });
        depo_manager_edit_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DepoManagerEditBus.class);
                startActivity(intent);
            }
        });
        depo_manager_add_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DepoManagerAddBusForm.class);
                startActivity(intent);
            }
        });
    }
}