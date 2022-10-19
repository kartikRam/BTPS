package com.example.btps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DepoManagerPassVerification extends AppCompatActivity implements VerifyPassRecyclerView.ItemSelected {
    RecyclerView verify_pass_list;
    RecyclerView.Adapter ProgramAdapter;
    RecyclerView.LayoutManager layoutManager;
    int j = 0;
    //array used for retrofit
    String[] passid = new String[150];
    String[] userid = new String[150];
    String[] source = new String[150];
    String[] destination = new String[150];
    String[] submit_date = new String[150];
    String[] days = new String[150];
    String[] pass_status = new String[150];
    String[] kilometer = new String[150];
    String[] amount = new String[150];
    String[] payment = new String[150];
    String[] user_images = new String[150];
    String[] id_card = new String[150];
    String[] pass_type = new String[150];
    String[] user_name = new String[150];
    String[] institute_id = new String[150];
    //array used for recyclerView
    String[] uimage;
    String[] uname;
    String[] submitdate;
    ImageView cut;

    ViewFlipper flipper;
    TextView institute_name,pass_source,pass_destination,pass_submit_date,pass_days,pass_amount,verification_pass_type;
    ImageView approve,disapprove;
    Button show_id_card;
    String institute_name_text;



    private AlertDialog.Builder dialog_builder;
    private AlertDialog dialog;

    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private String current_date;
    String end_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_manager_pass_verification);
        getSupportActionBar().hide();
        verify_pass_list = (RecyclerView) findViewById(R.id.verify_pass_list);
        verify_pass_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        verify_pass_list.setLayoutManager(layoutManager);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        current_date = dateFormat.format(calendar.getTime());

        call();
    }

    private void call() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Login.geturl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ViewPassApi api = retrofit.create(ViewPassApi.class);
        Call<List<Get_Pass_Model>> call = api.Get_Pass_Model();
        call.enqueue(new Callback<List<Get_Pass_Model>>() {
            @Override
            public void onResponse(Call<List<Get_Pass_Model>> call, retrofit2.Response<List<Get_Pass_Model>> response) {
                List<Get_Pass_Model> data = response.body();

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getDepo_manager_id().equals(DepoManagerIndex.getManagerId())) {
                        if(!(data.get(i).getPass_status().equals("3"))  ) {
                            if(!(data.get(i).getPass_status().equals("0"))) {
                                passid[j] = data.get(i).getPass_id();
                                userid[j] = data.get(i).getUser_id();
                                source[j] = data.get(i).getSource();
                                destination[j] = data.get(i).getDestination();
                                submit_date[j] = data.get(i).getSubmitDate();
                                days[j] = data.get(i).getDays();
                                pass_status[j] = data.get(i).getPass_status();
                                kilometer[j] = data.get(i).getKilometer();
                                amount[j] = data.get(i).getAmount();
                                payment[j] = data.get(i).getPayment();
                                id_card[j] = data.get(i).getId_card();
                                pass_type[j] = data.get(i).getPass_type();

                                j++;
                            }
                        }
                    }
                }
                getUserDetails();


            }

            @Override
            public void onFailure(Call<List<Get_Pass_Model>> call, Throwable t) {

            }
        });

    }
        public void getUserDetails (){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Login.geturl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ViewUserProfileApi api = retrofit.create(ViewUserProfileApi.class);
            Call<List<Get_UserProfile_Model>> call = api.Get_UserProfile_Model();
            call.enqueue(new Callback<List<Get_UserProfile_Model>>() {
                @Override
                public void onResponse(Call<List<Get_UserProfile_Model>> call, retrofit2.Response<List<Get_UserProfile_Model>> response) {
                    List<Get_UserProfile_Model> data = response.body();

                    for (int i = 0; i < data.size(); i++) {
                        for(int k=0;k<userid.length;k++){
                            if(data.get(i).getUser_id().equals(userid[k])){
                                user_name[k] = data.get(i).getUser_name();
                                institute_id[k]=data.get(i).getInstitute_id();
                                if (!(data.get(i).getImage().equals(null))) {
                                    user_images[k] = data.get(i).getImage();
                                } else {
                                    user_images[k]="defaultstudent.jpg";
                                }
                            }
                        }
                    }
                    CallRecycler();
                }


                @Override
                public void onFailure(Call<List<Get_UserProfile_Model>> call, Throwable t) {

                }
            });
        }

        private void CallRecycler () {

            uname = new String[j];
            submitdate = new String[j];
            uimage = new String[j];

            for (int i = 0; i < j; i++) {
                uname[i] = user_name[i];
                submitdate[i] = submit_date[i];
                uimage[i] = user_images[i];
            }

            ProgramAdapter = new VerifyPassRecyclerView(this, this, uname, submitdate, uimage);
            verify_pass_list.setAdapter(ProgramAdapter);
        }

    @Override
    public void setItem(int position) {

        end_date=addDays(current_date,Integer.parseInt(days[position]));

        setPassStatus(position,"2");
        Toast.makeText(getApplicationContext(),uname[position],Toast.LENGTH_SHORT).show();
        dialog_builder=new AlertDialog.Builder(this);
        final View pass_detail_popup=getLayoutInflater().inflate(R.layout.pass_dialog,null);
        dialog_builder.setView(pass_detail_popup);
        dialog =dialog_builder.create();
        dialog.show();cut=pass_detail_popup.findViewById(R.id.cut_depo_manager);
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Toast.makeText(getApplicationContext(),"starting date"+current_date,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"ending date"+end_date,Toast.LENGTH_SHORT).show();

        flipper=pass_detail_popup.findViewById(R.id.flipper_pass_verification);
        int imgarray[]={R.drawable.student_flipper_1,R.drawable.student_flipper_2,R.drawable.student_flipper_3};
        for (int i=0;i<imgarray.length;i++){
            showimage(imgarray[i]);
        }
        institute_name=pass_detail_popup.findViewById(R.id.pass_verification_institute_name);
        pass_source=pass_detail_popup.findViewById(R.id.pass_verification_source);
        pass_destination=pass_detail_popup.findViewById(R.id.pass_verification_destination);
        pass_amount=pass_detail_popup.findViewById(R.id.pass_verification_amount);
        pass_submit_date=pass_detail_popup.findViewById(R.id.pass_verification_submit_date);
        pass_days=pass_detail_popup.findViewById(R.id.pass_verification_days);
        verification_pass_type=pass_detail_popup.findViewById(R.id.pass_verification_pass_type);
        approve=pass_detail_popup.findViewById(R.id.approve);
        disapprove=pass_detail_popup.findViewById(R.id.disapprove);
        show_id_card=pass_detail_popup.findViewById(R.id.show_id_card);
        show_id_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog(position);
            }
        });
        findInstituteName(position);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassStatus(position,"3");
                recreate();

            }
        });

        disapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassStatus(position,"0");
                recreate();
            }
        });
    }


    private void setPassStatus(int position,String pass_status1) {
        String url1=Login.geturl()+"set_pass_status.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pass_id",passid[position]);
                params.put("pass_status",pass_status1);
                params.put("starting_date",current_date);
                params.put("ending_date",end_date);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        }

    public static String addDays(String startDate,int numberOfDays) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd",  Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_WEEK,numberOfDays);
        String resultDate=dateFormat.format(c.getTime());
        return resultDate;
    }

    private void getDialog(int position) {
        dialog_builder=new AlertDialog.Builder(this);
        final View pass_id_detail_popup=getLayoutInflater().inflate(R.layout.id_card_student,null);
        dialog_builder.setView(pass_id_detail_popup);
        dialog =dialog_builder.create();
        dialog.show();
        ImageView iv=pass_id_detail_popup.findViewById(R.id.id_card_depo_manager);
        String url21 = Login.geturl()+"images/" + id_card[position];
        Glide
                .with(DepoManagerPassVerification.this)
                .load(url21)
                .centerCrop()
                .placeholder(R.drawable.profile)
                .into(iv);


    }

    private void findInstituteName(int position) {

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

                    if (data.get(i).getInstitute_id().equals(institute_id[position])) {
                        institute_name_text=data.get(i).getInstitute_name();

                    }
                }
                setData(position);
            }

            @Override
            public void onFailure(Call<List<Get_Institute_Details>> call, Throwable t) {

            }
        });

    }

    private void setData(int position) {
        institute_name.setText(institute_name_text);
        pass_source.setText(source[position]);
        pass_destination.setText(destination[position]);
        pass_amount.setText(amount[position]);
        pass_submit_date.setText(submit_date[position]);
        pass_days.setText(days[position]);
        verification_pass_type.setText(pass_type[position]);

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
