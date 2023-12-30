package com.example.mdev1001_m2023_ice10;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> login(@Body Map<String, String> credentials);

    @POST("register")
    Call<RegisterResponse> register(@Body Map<String, String> data);

    @GET("list")
    Call<List<MovieDTO>> getItems();

    @POST("add")
    Call<MovieDTO> addMovie(@Body MovieDTO object);

    @PUT("update/{id}")
    Call<MovieDTO> editMovie(@Body MovieDTO object, @Path("id") String id);

    @DELETE("delete/{id}")
    Call<ResponseBody> deleteMovie(@Path("id") String id);

    @GET("has-updates")
    Call<UpdatedResponse> checkForUpdates();
}
