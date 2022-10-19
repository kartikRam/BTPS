package com.example.btps1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ViewUserProfileApi {

    @GET("view_user_profile.php")
    Call<List<Get_UserProfile_Model>> Get_UserProfile_Model();

}
