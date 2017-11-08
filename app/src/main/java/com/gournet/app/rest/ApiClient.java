package com.gournet.app.rest;

import android.support.annotation.NonNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private final static OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    private final static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://gournet.localtunnel.me/")
            .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient client = clientBuilder.build();
    public static Retrofit service = builder.build();

    public static Retrofit generateWToken(final String token) {
        client = clientBuilder.addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Authorization", "Bearer "+token);
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();
        service = builder
                .client(client)
                .build();
        return service;
    }


}
