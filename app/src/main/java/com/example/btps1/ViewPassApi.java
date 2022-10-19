package com.example.btps1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ViewPassApi {

    @GET("display_pass.php")
    Call<List<Get_Pass_Model>> Get_Pass_Model();

}
