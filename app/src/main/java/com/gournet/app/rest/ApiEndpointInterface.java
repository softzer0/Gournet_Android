package com.gournet.app.rest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gournet.app.model.Token;
import com.gournet.app.model.User;
import com.gournet.app.model.UserPass;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface ApiEndpointInterface {

    interface loginService {
        @POST("api/token/?format=json")
        Observable<Token> doLogin(@Body UserPass body);
    }

    interface accountService {
        @GET("api/users/?info=1&format=json")
        Observable<User> getData();
    }

    interface myAvatarService {
        @GET("/images/{type}/avatar/{size}/")
        Observable<ResponseBody> getImage(@Path("type") String type,
                                    @Path("size") int size);
    }

    interface homeService {
        @GET("api/home/")
        Observable<ResponseBody> getHome();
    }

    interface myFullSizeAvatar {
        @GET("/images/{type}/avatar/")
        @Streaming
        Observable<Bitmap> getImage(@Path("type") String type);
    }

    /*interface FullSizeAvatar {
        @GET("api/images/{type}/{id}/avatar/")
        Call<ResponseBody> getImage(@Path("type") String type,
                                     @Path("id") int id);
    }*/

    interface businessAvatar {
        @GET("/images/{type}/{id}/avatar/{size}/")
        @Streaming
        Observable<Bitmap> getImage(@Path("type") String type,
                                    @Path("id") int id,
                                    @Path("size") int size);
    }
}

