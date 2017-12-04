package com.gournet.app.rest;

import com.gournet.app.model.Token;
import com.gournet.app.model.User;
import com.gournet.app.model.UserPass;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiEndpointInterface {

    interface loginService {
        @POST("api/token/?format=json")
        Call<Token> doLogin(@Body UserPass body);
    }

    interface accountService {
        @GET("api/users/?info=1&format=json")
        Call<User> getData();
    }

    interface myAvatarService {
        @GET("/images/{type}/avatar/{size}/")
        Call<ResponseBody> getImage(@Path("type") String type,
                                    @Path("size") int size);
    }

    interface homeService {
        @GET("api/home/")
        Call<ResponseBody> getHome();
    }

    interface myFullSizeAvatar {
        @GET("/images/{type}/avatar/")
        Call<ResponseBody> getImage(@Path("type") String type);
    }

    /*interface FullSizeAvatar {
        @GET("api/images/{type}/{id}/avatar/")
        Call<ResponseBody> getImage(@Path("type") String type,
                                     @Path("id") int id);
    }*/

    interface avatarService {
        @GET("/images/{type}/{id}/avatar/{size}/")
        Call<ResponseBody> getImage(@Path("type") String type,
                                   @Path("id") int id,
                                   @Path("size") int size);
    }
}

