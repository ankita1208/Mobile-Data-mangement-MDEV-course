package com.example.mdev1001_m2023_ice4_android.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieDTO {
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    @PrimaryKey
    @ColumnInfo(name = "movieID")
    private int movieID;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "studio")
    private String studio;

    @ColumnInfo(name = "genres")
    private String genres;

    @ColumnInfo(name = "directors")
    private String directors;

    @ColumnInfo(name = "writers")
    private String writers;

    @ColumnInfo(name = "actors")
    private String actors;

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getMpaRating() {
        return mpaRating;
    }

    public void setMpaRating(String mpaRating) {
        this.mpaRating = mpaRating;
    }

    public String getCriticsRating() {
        return criticsRating;
    }

    public void setCriticsRating(String criticsRating) {
        this.criticsRating = criticsRating;
    }

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "length")
    private String length;

    @ColumnInfo(name = "shortDescription")
    private String shortDescription;

    @ColumnInfo(name = "mpaRating")
    private String mpaRating;

    @ColumnInfo(name = "criticsRating")
    private String criticsRating;

}

