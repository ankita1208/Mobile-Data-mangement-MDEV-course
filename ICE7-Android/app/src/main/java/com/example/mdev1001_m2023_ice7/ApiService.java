package com.example.mdev1001_m2023_ice7;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("list")
    Call<List<MovieDTO>> getItems();
}
