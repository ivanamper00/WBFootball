package com.mega.wbfootball.database;

import com.mega.wbfootball.model.JumpModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JumpApi {

    String BASE_URL = "https://tindahans.com/";


    @GET("jump_code.php")
    Call<JumpModel> getJump();
}


