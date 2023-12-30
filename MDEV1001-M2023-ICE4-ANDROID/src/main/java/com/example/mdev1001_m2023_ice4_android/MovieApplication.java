package com.example.mdev1001_m2023_ice4_android;

import android.app.Application;

import androidx.room.Room;

import com.example.mdev1001_m2023_ice4_android.Database.AppDatabase;

public class MovieApplication extends Application {
    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my-database")
                .build();
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
