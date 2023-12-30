package com.example.mdev1001_m2023_ice6_android;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItems(List<MovieDTO> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieDTO movieDTO);

    @Query("SELECT * FROM movie WHERE movieID = :movieId")
    MovieDTO getMovieById(int movieId);

    @Query("UPDATE movie SET title = :title, studio = :studio , genres = :genres, directors = :directors, writers = :writers, actors = :actors, year = :year,length = :length, shortDescription = :shortDescription, mpaRating = :mpaRating, criticsRating = :criticsRating WHERE movieID = :movieId")
    void updateMovieById(int movieId, String title, String studio, String genres, String directors, String writers, String actors, String year, String length, String shortDescription, String mpaRating, String criticsRating);

    @Query("SELECT * FROM movie")
    List<MovieDTO> getItems();

    @Query("DELETE FROM movie WHERE movieID = :movieId")
    void deleteMovieById(int movieId);
}