package com.example.mdev1001_m2023_ice10;

import android.app.Application;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MovieApplication extends Application {
    static Retrofit retrofit;
    static ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        Moshi moshi = new Moshi.Builder()
                .addLast(new KotlinJsonAdapterFactory())
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://mdev1001-m2023-api.onrender.com/api/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

         apiService = retrofit.create(ApiService.class);
    }

}
