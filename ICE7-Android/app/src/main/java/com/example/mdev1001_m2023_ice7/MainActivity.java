package com.example.mdev1001_m2023_ice7;

import static com.example.mdev1001_m2023_ice7.MovieApplication.apiService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MovieDTO> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        hitGetMoviesApi();
    }

    private void hitGetMoviesApi() {
        Call<List<MovieDTO>> call = apiService.getItems();
        call.enqueue(new Callback<List<MovieDTO>>() {
            @Override
            public void onResponse(Call<List<MovieDTO>> call, Response<List<MovieDTO>> response) {
                if (response.isSuccessful()) {
                     itemList = response.body();
                      setAdapter();
                } else {
                    Toast.makeText(MainActivity.this, "Response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MovieDTO>> call, Throwable t) {
             Toast.makeText(MainActivity.this, "Api Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
        recyclerView = (RecyclerView)  findViewById(R.id.moviesRL);
    }
    

    private void setAdapter() {
        MoviesAdapter moviesAdapter = new MoviesAdapter(itemList);
        recyclerView.setAdapter(moviesAdapter);
    }
}