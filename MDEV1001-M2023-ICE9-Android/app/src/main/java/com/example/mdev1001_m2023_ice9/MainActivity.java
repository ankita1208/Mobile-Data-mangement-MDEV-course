package com.example.mdev1001_m2023_ice9;

import static com.example.mdev1001_m2023_ice9.MovieApplication.apiService;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

    private static final long UPDATE_INTERVAL = 5000; // 5 seconds
    private Handler updateHandler;
    private Runnable updateRunnable;
    private long lastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        lastUpdated = System.currentTimeMillis();
        setupUpdateHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hitGetMoviesApi();
        startUpdateHandler();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopUpdateHandler();
    }

    private void setupUpdateHandler() {
        updateHandler = new Handler();
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                checkForUpdates();
                startUpdateHandler(); // Repeat after UPDATE_INTERVAL
            }
        };
    }

    private void startUpdateHandler() {
        updateHandler.postDelayed(updateRunnable, UPDATE_INTERVAL);
    }

    private void stopUpdateHandler() {
        updateHandler.removeCallbacks(updateRunnable);
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
        recyclerView = findViewById(R.id.moviesRL);
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
                    setAdapter();
                    Toast.makeText(MainActivity.this, "Movie deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Response Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Api Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkForUpdates() {
        Call<UpdatedResponse> call = apiService.checkForUpdates();
        call.enqueue(new Callback<UpdatedResponse>() {
            @Override
            public void onResponse(Call<UpdatedResponse> call, Response<UpdatedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpdatedResponse updatedResponse = response.body();
                    long lastUpdatedRemotely = updatedResponse.getLastUpdated();
                    if (lastUpdated < lastUpdatedRemotely) {
                        lastUpdated = lastUpdatedRemotely;
                        hitGetMoviesApi();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdatedResponse> call, Throwable t) {
                Log.e("failed","failed");
            }
        });
    }
}
