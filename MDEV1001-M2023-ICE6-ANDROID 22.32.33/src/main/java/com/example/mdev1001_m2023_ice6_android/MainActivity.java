package com.example.mdev1001_m2023_ice6_android;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomClickListener {
    public static boolean isUpdated = false;
    moviesAdapter moviesAdapter;
    private RecyclerView recyclerView;
    private Button addButton;
    private Button logoutButton;
    private List<MovieDTO> itemList;
    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        onClick();
        if (isUpdated) {
            getMoviesFromDb();
        } else {
            parseJSON();
        }

    }

    private void getMoviesFromDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Perform database operation here
                AppDatabase appDatabase = ((MovieApplication) getApplication()).getAppDatabase();
                itemList = appDatabase.movieDao().getItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter();
                    }
                });
            }
        }).start();
    }

    private void onClick() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
                intent.putExtra("add", "addMovie");
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(v -> {
            Toast.makeText(this, "Logout successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUpdated) {
            Log.e("onResume", "onResume");
            getMoviesFromDb();
        } else {
            parseJSON();
        }
    }

    private void initUI() {
        recyclerView = findViewById(R.id.moviesRL);
        addButton = findViewById(R.id.addBT);
        logoutButton = findViewById(R.id.logoutBT);
    }

    private void parseJSON() {
        // Get a reference to the AssetManager
        AssetManager assetManager = getAssets();
        try {
            // Open the JSON file
            InputStream inputStream = assetManager.open("Movies.json");
            // Read the contents of the file
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            // Convert the data to a JSON string
            jsonString = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        itemList = new Gson().fromJson(jsonString, new TypeToken<List<MovieDTO>>() {
        }.getType());
        AppDatabase appDatabase = ((MovieApplication) getApplication()).getAppDatabase();
        AsyncTask.execute(() -> {
            appDatabase.movieDao().insertItems(itemList);
        });
        setAdapter();
    }

    private void setAdapter() {
        moviesAdapter = new moviesAdapter(itemList, this);
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    public void onItemClick(int position, int type) {
        if (type == 0) {
            Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
            intent.putExtra("movieId", itemList.get(position).getMovieID());
            startActivity(intent);
        } else if (type == 1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase appDatabase = ((MovieApplication) getApplication()).getAppDatabase();
                    appDatabase.movieDao().deleteMovieById(itemList.get(position).getMovieID());
                    itemList.remove(position);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getMoviesFromDb();
                        }
                    });
                }
            }).start();
        }
    }
}