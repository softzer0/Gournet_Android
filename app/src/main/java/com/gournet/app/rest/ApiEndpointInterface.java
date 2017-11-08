package com.gournet.app.rest;

import com.gournet.app.model.Token;
import com.gournet.app.model.User;
import com.gournet.app.model.UserPass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiEndpointInterface {

    interface loginService {
        @POST("api/token/?format=json")
        Call<Token> doLogin(@Body UserPass body);
    }

    interface accountService {
        @GET("api/users/?info=1&format=json")
        Call<User> getData();
    }
}

