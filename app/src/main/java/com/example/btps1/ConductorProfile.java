package com.example.btps1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.appwidget.AppWidgetHost;
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

public class ConductorProfile extends AppCompatActivity {

    ImageView conductor_image_profile;
    TextView conductor_name_profile;
    EditText email_conductor_profile,name_conductor_profile,conductor_id_conductor_profile,address_conductor_profile,contact_conductor_profile,branch_conductor_profile,post_conductor_profile;
    Button update_conductor_profile;
    String encodeImageString;
    int counter=0;
    Bitmap bitmap;
    String name,address,number;
    String url=Login.geturl()+"/update_depomanager.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_profile);
        getSupportActionBar().hide();
        conductor_image_profile=findViewById(R.id.conductor_image_profile);
        email_conductor_profile=findViewById(R.id.email_conductor_profile);
        name_conductor_profile=findViewById(R.id.name_conductor_profile);
        conductor_id_conductor_profile=findViewById(R.id.conductor_id_conductor_profile);
        address_conductor_profile=findViewById(R.id.address_conductor_profile);
        contact_conductor_profile=findViewById(R.id.contact_conductor_profile);
        branch_conductor_profile=findViewById(R.id.branch_conductor_profile);
        post_conductor_profile=findViewById(R.id.post_conductor_profile);
        update_conductor_profile=findViewById(R.id.update_conductor_profile);
        conductor_name_profile=findViewById(R.id.conductor_name_profile);
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
                int j = 0;
                for (int i = 0; i < data.size(); i++) {
                    String email = data.get(i).getWorker_email().trim();
                    if (email.matches(Login.getEmail())) {
                        conductor_name_profile.setText(data.get(i).getWorker_name());
                        String url2 = Login.geturl()+"images/" + data.get(i).getImage();
                        Glide
                                .with(ConductorProfile.this)
                                .load(url2)
                                .centerCrop()
                                .placeholder(R.drawable.profile)
                                .into(conductor_image_profile);

                        email_conductor_profile.setText(data.get(i).getWorker_email());
                        name_conductor_profile.setText(data.get(i).getWorker_name());
                        conductor_id_conductor_profile.setText(data.get(i).getWorker_id());
                        address_conductor_profile.setText(data.get(i).getWorker_address());
                        contact_conductor_profile.setText(data.get(i).getWorker_contact_no());
                        branch_conductor_profile.setText(data.get(i).getBranch_name());
                        if(data.get(i).getWorker_role().equals("1")){
                            post_conductor_profile.setText("Depo-Manager");
                        }else{
                            post_conductor_profile.setText("Conductor");
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Display_Conductor_Model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " Lelo ", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                conductor_image_profile.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (Exception ex) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
        counter=1;
    }


    public void upload_data_to_db(View view) {

        number=contact_conductor_profile.getText().toString();
        address=address_conductor_profile.getText().toString();
        name=name_conductor_profile.getText().toString();

        if(number.matches("")|| address.matches("")||name.matches("")){
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

                    map.put("phone", number);
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
        Dexter.withActivity(ConductorProfile.this)
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