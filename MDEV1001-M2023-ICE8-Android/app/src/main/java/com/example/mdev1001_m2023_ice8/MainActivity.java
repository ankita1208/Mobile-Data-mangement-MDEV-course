package com.example.mdev1001_m2023_ice8;

import static com.example.mdev1001_m2023_ice8.MovieApplication.apiService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import java.io.Serializable;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CustomClickListener {
    private RecyclerView recyclerView;
    private List<MovieDTO> itemList;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        addButton = findViewById(R.id.addBT);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivity(intent);
            }
        });
    }
    

    private void setAdapter() {
        MoviesAdapter moviesAdapter = new MoviesAdapter(itemList, this);
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    public void onItemClick(int position, int type) {
        if (type == 0) {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra("movieDTO", (Serializable) itemList.get(position));
            startActivity(intent);
        } else if (type == 1) {
            deleteMovie(position);
        }
    }

    private void deleteMovie(int position) {
        Call<ResponseBody> call = apiService.deleteMovie(itemList.get(position).getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    itemList.remove(position);
                    hitGetMoviesApi();
                } else {
                    Toast.makeText(MainActivity.this, "Response Unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Api Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}