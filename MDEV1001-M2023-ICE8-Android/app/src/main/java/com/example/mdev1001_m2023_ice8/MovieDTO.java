package com.example.mdev1001_m2023_ice8;

import java.io.Serializable;
import java.util.List;

public class MovieDTO implements Serializable {
    @com.squareup.moshi.Json(name = "_id")
    private String _id;
    @com.squareup.moshi.Json(name = "movieID")
    private String movieID;
    @com.squareup.moshi.Json(name = "title")
    private String title;
    @com.squareup.moshi.Json(name = "studio")
    private String studio;
    @com.squareup.moshi.Json(name = "genres")
    private List<String> genres;
    @com.squareup.moshi.Json(name = "directors")
    private List<String> directors;
    @com.squareup.moshi.Json(name = "writers")
    private List<String> writers;
    @com.squareup.moshi.Json(name = "actors")
    private List<String> actors;
    @com.squareup.moshi.Json(name = "year")
    private Integer year;
    @com.squareup.moshi.Json(name = "length")
    private Integer length;
    @com.squareup.moshi.Json(name = "shortDescription")
    private String shortDescription;
    @com.squareup.moshi.Json(name = "mpaRating")
    private String mpaRating;
    @com.squareup.moshi.Json(name = "criticsRating")
    private Double criticsRating;

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
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

    public Double getCriticsRating() {
        return criticsRating;
    }

    public void setCriticsRating(Double criticsRating) {
        this.criticsRating = criticsRating;
    }
}
