package com.example.ICE12;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
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
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyADyYAwrfId6kvcpYavp4v7c5eL1Dvtx3Y")
                .setApplicationId("1:82028275109:android:b814d5f6fab5bf8cadf44c")
                .setProjectId("mdev1001-ice")
                .build();
        FirebaseApp.initializeApp(this, options);
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
