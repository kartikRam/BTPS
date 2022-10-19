package com.example.btps1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstituteHome extends AppCompatActivity {

    static String institute_email1;
    String contact, address,name;
    ImageView institute_image_profile;
    String encodeImageString;
    TextView institute_name_profile;
    EditText email_institute_profile,name_institute_profile,institute_id_institute_profile,address_institute_profile,contact_institute_profile;
    Button update_institute_profile;
    String url1 = Login.geturl();
    String url = Login.geturl() + "update_institute.php";
    Bitmap bitmap;
    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_home);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        institute_email1 = intent.getStringExtra("institute_email");
        institute_image_profile= findViewById(R.id.institute_image_profile);
        institute_name_profile = findViewById(R.id.institute_name_profile);
        email_institute_profile= findViewById(R.id.email_institute_profile);
        name_institute_profile = findViewById(R.id.name_institute_profile);
        institute_id_institute_profile= findViewById(R.id.institute_id_institute_profile);
        address_institute_profile = findViewById(R.id.address_institute_profile);
        contact_institute_profile    = findViewById(R.id.contact_institute_profile);
        update_institute_profile = findViewById(R.id.update_institute_profile);
        update_institute_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_data_to_db();
            }
        });

        call();

    }


    private void call() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ViewInstituteDetailsApi api = retrofit.create(ViewInstituteDetailsApi.class);
        Call<List<Get_Institute_Details>> call = api.Get_Institute_Details();
        call.enqueue(new Callback<List<Get_Institute_Details>>() {
            @Override
            public void onResponse(Call<List<Get_Institute_Details>> call, retrofit2.Response<List<Get_Institute_Details>> response) {
                List<Get_Institute_Details> data = response.body();
                for (int i = 0; i < data.size(); i++) {

                    if (data.get(i).getInstitute_email().equals(Login.getEmail())) {

                        email_institute_profile.setText(data.get(i).getInstitute_email());
                        name_institute_profile.setText(data.get(i).getInstitute_name());
                        contact_institute_profile.setText(data.get(i).getInstitute_contact_no());
                        address_institute_profile.setText(data.get(i).getInstitute_address());
                        institute_name_profile.setText(data.get(i).getInstitute_name());
                        institute_id_institute_profile.setText(data.get(i).getInstitute_id());

                        String url21 = Login.geturl()+"images/" + data.get(i).getImage();
                        Glide
                                .with(InstituteHome.this)
                                .load(url21)
                                .centerCrop()
                                .placeholder(R.drawable.profile)
                                .into(institute_image_profile);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Get_Institute_Details>> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            Uri filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                institute_image_profile.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {

            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            Toast.makeText(getApplicationContext(),""+data.getData(),Toast.LENGTH_SHORT).show();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                institute_image_profile.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (Exception ex) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
        counter=1;
    }


    public void upload_data_to_db() {

        contact=contact_institute_profile.getText().toString();
        address=address_institute_profile.getText().toString();
        name=name_institute_profile.getText().toString();

        if(contact.matches("")|| address.matches("")||name.matches("")){
            Toast.makeText(getApplicationContext(),"Please Fill all details",Toast.LENGTH_SHORT).show();
        }else {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();

                    map.put("phone", contact);
                    map.put("address", address);
                    map.put("name", name);
                    map.put("email", Login.getEmail());
                    if(counter==1) {
                        map.put("upload", encodeImageString);
                        map.put("is_image", "1");
                    }else{
                        map.put("email", Login.getEmail());
                        map.put("is_image", "0");

                    }
                    return map;

                }
            };


            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }
    }

    public void saveData(View view) {
        Dexter.withActivity(InstituteHome.this)
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