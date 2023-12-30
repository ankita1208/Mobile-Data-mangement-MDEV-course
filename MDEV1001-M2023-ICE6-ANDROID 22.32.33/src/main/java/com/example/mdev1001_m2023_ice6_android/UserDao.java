package com.example.mdev1001_m2023_ice6_android;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    User getUserByUsernameAndPassword(String username, String password);
}

