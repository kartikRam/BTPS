package com.example.btps1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DisplayBusApi {
    @GET("displaybus.php")
    Call<List<Display_Bus_Model>> Display_Bus_Model();
}
