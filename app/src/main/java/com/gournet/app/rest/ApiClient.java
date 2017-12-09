package com.gournet.app.rest;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gournet.app.model.Token;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private final static OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
    private final static Retrofit.Builder builder = new Retrofit.Builder()
           .baseUrl("http://mikisoft-64231.portmap.io:64231/")
              //.baseUrl("http://gournet.localtunnel.me")
            // .baseUrl("https://www.gournet.co/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient client = clientBuilder.build();
    public static Retrofit service = builder.client(client).build();
    public static Retrofit imageService;
    //public static Retrofit service = builder.build();

    public static void generateWToken(final String token, Context context) {
        OkHttpClient.Builder clientB;
        clientB = clientBuilder.addInterceptor(
                chain -> {
                    okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                    ongoing.addHeader("Authorization", "Bearer "+token);
                    return chain.proceed(ongoing.build());
                });
        client = clientB.build();
        OkHttpClient imageClient = clientB.addInterceptor(
                chain -> {
                    okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                    ongoing.cacheControl(new CacheControl.Builder()
                        .maxStale(30, TimeUnit.DAYS)
                        .build()
                    );
                    return chain.proceed(ongoing.build());
                })
                .cache(new Cache(new File(context.getCacheDir(), "avatar"), 10 * 1024 * 1024))
                .build();
        service = builder
                .client(client)
                .build();
        imageService = builder.client(imageClient).build();
    }


}
  //provera tokena
  //save token in sharedPreference