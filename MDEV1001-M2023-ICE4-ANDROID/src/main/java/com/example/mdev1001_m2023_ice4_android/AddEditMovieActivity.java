package com.example.mdev1001_m2023_ice4_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mdev1001_m2023_ice4_android.Database.AppDatabase;
import com.example.mdev1001_m2023_ice4_android.dto.MovieDTO;

public class AddEditMovieActivity extends AppCompatActivity {
    MovieDTO movieDTO;
    private String receivedData;
    private int movieId;
    private TextView titleText;
    private Button addButton;
    private EditText titleET;
    private EditText StudioET;
    private EditText genresET;
    private EditText directorsET;
    private EditText writersET;
    private EditText actorsET;
    private EditText lengthET;
    private EditText yearET;
    private EditText shortDescriptionET;
    private EditText mpaRatingET;
    private EditText criticRatingET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);
        initialiseViews();
        receiveIntent();
    }

    private void initialiseViews() {
        titleText = (TextView) findViewById(R.id.titleTextTV);
        addButton = (Button) findViewById(R.id.addBT);
        titleET = (EditText) findViewById(R.id.titleET);
        StudioET = (EditText) findViewById(R.id.StudioET);
        genresET = (EditText) findViewById(R.id.genresET);
        directorsET = (EditText) findViewById(R.id.directorsET);
        writersET = (EditText) findViewById(R.id.writersET);
        actorsET = (EditText) findViewById(R.id.actorsET);
        lengthET = (EditText) findViewById(R.id.lengthET);
        yearET = (EditText) findViewById(R.id.yearET);
        shortDescriptionET = (EditText) findViewById(R.id.shortDescriptionET);
        mpaRatingET = (EditText) findViewById(R.id.mpaRatingET);
        criticRatingET = (EditText) findViewById(R.id.criticRatingET);
    }

    private void receiveIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("add")) {
            receivedData = "add";
        } else if (intent.hasExtra("movieId")) {
            movieId = intent.getIntExtra("movieId", 0);
        }
        if (receivedData != null && !receivedData.isEmpty()) {
            titleText.setText("Add Movie");
            addButton.setText("Add");
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Perform database operation here
                            AppDatabase appDatabase = ((MovieApplication) getApplication()).getAppDatabase();
                            MovieDTO movieDTO = new MovieDTO();
                            movieDTO.setTitle(titleET.getText().toString());
                            movieDTO.setActors(actorsET.getText().toString());
                            movieDTO.setDirectors(directorsET.getText().toString());
                            movieDTO.setGenres(genresET.getText().toString());
                            movieDTO.setCriticsRating(criticRatingET.getText().toString());
                            movieDTO.setMpaRating(mpaRatingET.getText().toString());
                            movieDTO.setStudio(StudioET.getText().toString());
                            movieDTO.setLength(lengthET.getText().toString());
                            movieDTO.setShortDescription(shortDescriptionET.getText().toString());
                            movieDTO.setWriters(writersET.getText().toString());
                            movieDTO.setYear(yearET.getText().toString());
                            appDatabase.movieDao().insertMovie(movieDTO);
                            MainActivity.isUpdated = true;
                            finish();
                        }
                    }).start();
                }
            });
        } else {
            titleText.setText("Edit Movie");
            addButton.setText("Update");
            initDB();
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("here", "here");
                            // Perform database operation here
                            AppDatabase appDatabase = ((MovieApplication) getApplication()).getAppDatabase();
                            appDatabase.movieDao().updateMovieById(movieId, titleET.getText().toString(), StudioET.getText().toString(), genresET.getText().toString(), directorsET.getText().toString(), writersET.getText().toString(), actorsET.getText().toString(), yearET.getText().toString(), lengthET.getText().toString(), shortDescriptionET.getText().toString(), mpaRatingET.getText().toString(), criticRatingET.getText().toString());
                            MainActivity.isUpdated = true;
                            finish();
                        }
                    }).start();
                }
            });
        }
    }

    private void populateViewsWithData() {
        titleET.setText(movieDTO.getTitle());
        StudioET.setText(movieDTO.getStudio());
        genresET.setText(movieDTO.getGenres());
        directorsET.setText(movieDTO.getDirectors());
        writersET.setText(movieDTO.getWriters());
        actorsET.setText(movieDTO.getActors());
        lengthET.setText(movieDTO.getLength());
        yearET.setText(movieDTO.getYear());
        shortDescriptionET.setText(movieDTO.getShortDescription());
        mpaRatingET.setText(movieDTO.getMpaRating());
        criticRatingET.setText(movieDTO.getCriticsRating());
    }

    private void initDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Perform database operation here
                AppDatabase appDatabase = ((MovieApplication) getApplication()).getAppDatabase();
                movieDTO = appDatabase.movieDao().getMovieById(movieId);
                // Update the UI on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (movieDTO != null) {
                            populateViewsWithData();
                        } else {
                            Log.e("AddEditMovieActivity", "movie dto is null");
                        }
                    }
                });
            }
        }).start();
    }
}