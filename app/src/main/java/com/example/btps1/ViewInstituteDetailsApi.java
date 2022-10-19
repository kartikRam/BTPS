package com.example.btps1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ViewInstituteDetailsApi {

    @GET("institute_profile.php")
    Call<List<Get_Institute_Details>> Get_Institute_Details();
}
