package com.example.mdev1001_m2023_ice8;

import static com.example.mdev1001_m2023_ice8.MovieApplication.apiService;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mdev1001_m2023_ice8.MovieDTO;
import com.example.mdev1001_m2023_ice8.R;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditActivity extends AppCompatActivity {
    MovieDTO movieDTO;
    private String receivedData;
    private int movieId;
    private TextView titleText;
    private Button addButton;
    private EditText titleET;
    private EditText idET;
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
        setContentView(R.layout.activity_add_edit);
        initialiseViews();
        receiveIntent();
    }

    private void initialiseViews() {
        titleText = (TextView) findViewById(R.id.titleTextTV);
        idET = (EditText) findViewById(R.id.idET);
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
        movieDTO = (MovieDTO) intent.getSerializableExtra("movieDTO");

        if (movieDTO ==  null) {
            titleText.setText("Add Movie");
            addButton.setText("Add");
            addButton.setOnClickListener(v->{
                hitAddMovieApi();
            });
        } else {
            titleText.setText("Edit Movie");
            addButton.setText("Update");
            populateViewsWithData();
            addButton.setOnClickListener( v->{
                hitAddMovieApi();
            });
        }
    }

    private void populateViewsWithData() {
        idET.setText(movieDTO.getMovieID().toString());
        titleET.setText(movieDTO.getTitle().toString());
        StudioET.setText(movieDTO.getStudio());
        String genres = TextUtils.join(", ", movieDTO.getGenres());
        genresET.setText(genres);
        String directors = TextUtils.join(", ", movieDTO.getDirectors());
        directorsET.setText(directors);
        String writers = TextUtils.join(", ", movieDTO.getWriters());
        writersET.setText(writers);
        String actors = TextUtils.join(", ", movieDTO.getActors());
        actorsET.setText(actors);
        lengthET.setText(movieDTO.getLength().toString());
        yearET.setText(movieDTO.getYear().toString());
        shortDescriptionET.setText(movieDTO.getShortDescription().toString());
        mpaRatingET.setText(movieDTO.getMpaRating().toString());
        criticRatingET.setText(String.valueOf(movieDTO.getCriticsRating().toString()));
    }

    private void hitAddMovieApi() {
        MovieDTO movieReq = new MovieDTO();
        if (movieDTO == null) {
            movieReq.setId(UUID.randomUUID().toString());
        }
        movieReq.setMovieID( idET.getText().toString().trim());
        movieReq.setTitle( titleET.getText().toString().trim());
        movieReq.setStudio( StudioET.getText().toString().trim());
        movieReq.setGenres(Collections.singletonList(genresET.getText().toString().trim()));
        movieReq.setDirectors(Collections.singletonList(directorsET.getText().toString().trim()));
        movieReq.setWriters(Collections.singletonList(writersET.getText().toString().trim()));
        movieReq.setActors(Collections.singletonList(actorsET.getText().toString().trim()));
        movieReq.setYear(Integer.valueOf(yearET.getText().toString().trim()));
        movieReq.setLength(Integer.valueOf(lengthET.getText().toString().trim()));
        movieReq.setShortDescription(shortDescriptionET.getText().toString().trim());
        movieReq.setMpaRating(mpaRatingET.getText().toString().trim());
        movieReq.setCriticsRating(Double.valueOf(criticRatingET.getText().toString().trim()));
        Call<MovieDTO> call;
        if (movieDTO == null) {
            call = apiService.addMovie(movieReq);
        } else {
            call = apiService.editMovie(movieReq, movieDTO.getId());
        }

       call.enqueue(new Callback<MovieDTO>() {
           @Override
           public void onResponse(Call<MovieDTO> call, Response<MovieDTO> response) {
               if (response.isSuccessful()) {
                   Toast.makeText(AddEditActivity.this, "Movie added successfully",Toast.LENGTH_SHORT).show();
                   finish();
               } else {
                   Toast.makeText(AddEditActivity.this, "Response Unsuccessful",Toast.LENGTH_SHORT).show();
               }

           }

           @Override
           public void onFailure(Call<MovieDTO> call, Throwable t) {
               Toast.makeText(AddEditActivity.this, "Api Failed",Toast.LENGTH_SHORT).show();
           }
       });
    }

}