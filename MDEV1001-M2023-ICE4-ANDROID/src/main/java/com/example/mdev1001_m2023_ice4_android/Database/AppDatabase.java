package com.example.mdev1001_m2023_ice4_android.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mdev1001_m2023_ice4_android.DAO.MovieDao;
import com.example.mdev1001_m2023_ice4_android.dto.MovieDTO;

@Database(entities = {MovieDTO.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}