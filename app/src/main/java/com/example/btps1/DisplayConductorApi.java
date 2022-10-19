package com.example.btps1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DisplayConductorApi {

    @GET("view_conductor.php")
    Call<List<Display_Conductor_Model>> Display_Conductor_Model();

}
