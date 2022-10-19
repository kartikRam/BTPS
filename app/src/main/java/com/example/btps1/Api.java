package com.example.btps1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String url = Login.geturl();
    @GET ("view_bus.php")
    Call<List<Get_Bus_Model>> Get_Bus_Model();
}
