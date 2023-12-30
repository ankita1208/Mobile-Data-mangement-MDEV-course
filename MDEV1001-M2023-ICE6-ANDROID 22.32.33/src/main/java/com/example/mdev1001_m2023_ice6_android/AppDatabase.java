package com.example.mdev1001_m2023_ice6_android;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MovieDTO.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract UserDao userDao();
}