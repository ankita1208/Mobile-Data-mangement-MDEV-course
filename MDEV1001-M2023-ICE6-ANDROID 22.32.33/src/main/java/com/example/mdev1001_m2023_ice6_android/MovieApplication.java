package com.example.mdev1001_m2023_ice6_android;

import android.app.Application;

import androidx.room.Room;

public class MovieApplication extends Application {
    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my-database")
                .fallbackToDestructiveMigration() .build();
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
