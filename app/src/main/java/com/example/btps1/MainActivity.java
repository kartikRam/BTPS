package com.example.btps1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
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

import static com.example.btps1.Api.url;

public class MainActivity extends AppCompatActivity {

    ImageView user_image_profile;
    TextView user_name_profile;
    EditText email_user_profile,name_user_profile,user_id_user_profile,address_user_profile,contact_user_profile,user_type_user_profile,student_institue_name,student_enrollment_no;
    Button update_profile;
    String encodeImageString;
    TextInputLayout student_enrollment_user_profile,institute_name_user_profile;
    int counter=0;

    static String uid;
    static String image;
    static String uname;
    String utype;
    String uphone;
    String url1 = Login.geturl();
    Button profile_update;
    Bitmap bitmap;
    private static final String url = Login.geturl() + "fileupload.php";
    String  phone,contact,address,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        user_image_profile=findViewById(R.id.user_image_profile);
        email_user_profile=findViewById(R.id.email_user_profile);
        name_user_profile=findViewById(R.id.name_user_profile);
        user_id_user_profile=findViewById(R.id.user_id_user_profile);
        address_user_profile=findViewById(R.id.address_user_profile);
        contact_user_profile=findViewById(R.id.contact_user_profile);
        user_type_user_profile=findViewById(R.id.user_type_user_profile);
        student_institue_name=findViewById(R.id.student_institute_name);
        student_enrollment_no=findViewById(R.id.student_Enrollment_no);
        update_profile=findViewById(R.id.update_profile);
        student_enrollment_user_profile=findViewById(R.id.student_enrollment_user_profile);
        institute_name_user_profile=findViewById(R.id.institue_name_user_profile);
        user_name_profile=findViewById(R.id.user_name_profile);
        call();


    }

    public static String getuserid() {
        return uid;
    }

    public static String getusername() {
        return uname;
    }

    public static String getimage() {
        return image;
    }

    private void call() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ViewUserProfileApi api = retrofit.create(ViewUserProfileApi.class);
        Call<List<Get_UserProfile_Model>> call = api.Get_UserProfile_Model();
        call.enqueue(new Callback<List<Get_UserProfile_Model>>() {
            @Override
            public void onResponse(Call<List<Get_UserProfile_Model>> call, retrofit2.Response<List<Get_UserProfile_Model>> response) {
                List<Get_UserProfile_Model> data = response.body();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getUser_email().equals(Login.getEmail())) {
                        uid = data.get(i).getUser_id();
                        uname = data.get(i).getUser_name();
                        image = data.get(i).getImage();
                        utype = data.get(i).getIs_student();
                        uphone = data.get(i).getUser_contact_no();
                        if(!(data.get(i).getImage().equals(null))) {
                            String url2 = Login.geturl()+"images/" + data.get(i).getImage();
                            Glide
                                    .with(MainActivity.this)
                                    .load(url2)
                                    .centerCrop()
                                    .placeholder(R.drawable.profile)
                                    .into(user_image_profile);
                        }
                        user_name_profile.setText(data.get(i).getUser_name());
                        email_user_profile.setText(data.get(i).getUser_email());
                        name_user_profile.setText(data.get(i).getUser_name());
                        user_id_user_profile.setText(data.get(i).getUser_id());
                        contact_user_profile.setText(data.get(i).getUser_contact_no());
                        address_user_profile.setText(data.get(i).getUser_address());

                        if (utype.equals("0")) {
                            user_type_user_profile.setText("Passenger");
                            institute_name_user_profile.setVisibility(View.GONE);
                            student_enrollment_user_profile.setVisibility(View.GONE);
                        } else {
                            user_type_user_profile.setText("Student");
                            student_enrollment_no.setText(data.get(i).getEnrollment_no());
                            getInstituteName(data.get(i).getInstitute_id());

                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Get_UserProfile_Model>> call, Throwable t) {

            }
        });
    }

    private void getInstituteName(String institute_id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Login.geturl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ViewInstituteDetailsApi api = retrofit.create(ViewInstituteDetailsApi.class);
        Call<List<Get_Institute_Details>> call = api.Get_Institute_Details();
        call.enqueue(new Callback<List<Get_Institute_Details>>() {
            @Override
            public void onResponse(Call<List<Get_Institute_Details>> call, retrofit2.Response<List<Get_Institute_Details>> response) {
                List<Get_Institute_Details> data = response.body();
                for (int i = 0; i < data.size(); i++) {

                    if (data.get(i).getInstitute_id().equals(institute_id)) {
                        student_institue_name.setText(data.get(i).getInstitute_name());
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
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                user_image_profile.setImageBitmap(bitmap);
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

       contact=contact_user_profile.getText().toString();
       address=address_user_profile.getText().toString();
       name=name_user_profile.getText().toString();

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
        Dexter.withActivity(MainActivity.this)
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