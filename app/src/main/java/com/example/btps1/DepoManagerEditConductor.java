package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DepoManagerEditConductor extends AppCompatActivity implements Student_List_View_Adapter.getItemSelected {

    RecyclerView edit_conductor_list;
    RecyclerView.Adapter ProgramAdapter;
    RecyclerView.LayoutManager layoutManager;
    int j=0;
    EditText edit_conductor_email, edit_conductor_password,edit_conductor_name;
    Button edit_conductor_submit;
    String email_text, password_text,name_text;

    ViewFlipper flipper;
    String[] email = new String[150];
    String[] name = new String[150];
    String[] image = new String[150];
    String[] password=new String[150];
    String[] image1;
    String[] email1;
    String[] name1;
    private AlertDialog.Builder dialog_builder;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_manager_edit_conductor);

        getSupportActionBar().hide();
        edit_conductor_list=(RecyclerView)findViewById(R.id.edit_conductor_list);
        edit_conductor_list.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        edit_conductor_list.setLayoutManager(layoutManager);
        call();
    }

    private void call() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Login.geturl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DisplayConductorApi api = retrofit.create(DisplayConductorApi.class);
        Call<List<Display_Conductor_Model>> call = api.Display_Conductor_Model();
        call.enqueue(new Callback<List<Display_Conductor_Model>>() {
            @Override
            public void onResponse(Call<List<Display_Conductor_Model>> call, retrofit2.Response<List<Display_Conductor_Model>> response) {
                List<Display_Conductor_Model> data = response.body();

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getDepo_manager_id()==null) {

                        //nothing to do Here!
                        //But i Have to do something here , otherwise this fucking application is gonna giving me an error
                        String j="Kuch Nhi karna salo";
                    }else{
                        if (data.get(i).getDepo_manager_id().equals(DepoManagerIndex.getManagerId())) {
                            name[j] = data.get(i).getWorker_name();
                            email[j] = data.get(i).getWorker_email();
                            if (data.get(i).getImage().equals("")) {
                                image[j] = "conductor_logo.png";
                            } else {
                                image[j] = data.get(i).getImage();
                            }
                            password[j] = data.get(i).getWorker_password();
                            j++;
                        }
                    }
                }
                getlist();
            }
            @Override
            public void onFailure(Call<List<Display_Conductor_Model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " Lelo ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getlist() {

        image1=new String[j];
        name1=new String[j];
        email1=new String[j];

        for(int i=0;i<j;i++){
            image1[i]=image[i];
            name1[i]=name[i];
            email1[i]=email[i];
        }
        if(name1!=null) {
            ProgramAdapter = new Student_List_View_Adapter(this, this, email1, image1, name1);
            edit_conductor_list.setAdapter(ProgramAdapter);
        }
    }

    @Override
    public void AddDataOnSingleClick(int position) {

        dialog_builder=new AlertDialog.Builder(this);
        final View conductor_detail_popup=getLayoutInflater().inflate(R.layout.editconductordialog,null);
        dialog_builder.setView(conductor_detail_popup);
        dialog =dialog_builder.create();
        dialog.show();

        flipper=conductor_detail_popup.findViewById(R.id.flipper_conductor_details);
        edit_conductor_email = conductor_detail_popup.findViewById(R.id. edit_conductor_email);
        edit_conductor_password = conductor_detail_popup.findViewById(R.id. edit_conductor_password);
        edit_conductor_name = conductor_detail_popup.findViewById(R.id. edit_conductor_name);
        edit_conductor_submit=conductor_detail_popup.findViewById(R.id.edit_conductor_submit);
        ImageView close_dialog=conductor_detail_popup.findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        int imgarray[]={R.drawable.conductor_background1,R.drawable.conductor_background2,R.drawable.conductor_background3};

        for (int i=0;i<imgarray.length;i++){
            showimage(imgarray[i]);
        }
        setdata(position);
        edit_conductor_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_text=edit_conductor_email.getText().toString();
                password_text=edit_conductor_password.getText().toString();
                name_text=edit_conductor_name.getText().toString();
                if(email_text.matches("")||password_text.matches("")||name_text.matches("")){
                    Toast.makeText(DepoManagerEditConductor.this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
                }else{
                    update();
                }
            }
        });

    }

    private void update() {

        String url1=Login.geturl()+"update_conductor.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Conductor Updated Successfully", Toast.LENGTH_SHORT).show();
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
                params.put("name",name_text);
                params.put("email",email_text);
                params.put("password",password_text);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void setdata(int position) {
        edit_conductor_email.setText(email[position]);
        edit_conductor_password.setText(password[position]);
        edit_conductor_name.setText(name[position]);

    }

    private void showimage(int image) {

        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }
}