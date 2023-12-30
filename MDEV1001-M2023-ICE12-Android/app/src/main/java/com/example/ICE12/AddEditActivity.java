package com.example.ICE12;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.UUID;

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
    private FirebaseFirestore firestore;
    private String documentId;

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
        firestore = FirebaseFirestore.getInstance();
    }

    private void receiveIntent() {
        documentId = getIntent().getStringExtra("documentId");
        if (documentId ==  null) {
            titleText.setText("Add Movie");
            addButton.setText("Add");
            addButton.setOnClickListener(v->{
                hitAddMovieApi();
            });
        } else {
            titleText.setText("Edit Movie");
            addButton.setText("Update");
            loadMovieData(documentId);
            addButton.setOnClickListener( v->{
                hitAddMovieApi();
            });
        }
    }

    private void loadMovieData(String documentId) {
        firestore.collection("movies").document(documentId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            MovieDTO movie = document.toObject(MovieDTO.class);
                            if (movie != null) {
                                populateViewsWithData(movie);
                            }
                        }
                    }
                });
    }

    private void populateViewsWithData(MovieDTO movieDTO) {
        idET.setText(String.valueOf(movieDTO.getMovieID()));
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
        movieReq.setMovieID(Long.parseLong(idET.getText().toString().trim()));
        movieReq.setTitle(titleET.getText().toString().trim());
        movieReq.setStudio(StudioET.getText().toString().trim());
        movieReq.setGenres(Collections.singletonList(genresET.getText().toString().trim()));
        movieReq.setDirectors(Collections.singletonList(directorsET.getText().toString().trim()));
        movieReq.setWriters(Collections.singletonList(writersET.getText().toString().trim()));
        movieReq.setActors(Collections.singletonList(actorsET.getText().toString().trim()));
        movieReq.setYear(Integer.valueOf(yearET.getText().toString().trim()));
        movieReq.setLength(Integer.valueOf(lengthET.getText().toString().trim()));
        movieReq.setShortDescription(shortDescriptionET.getText().toString().trim());
        movieReq.setMpaRating(mpaRatingET.getText().toString().trim());
        movieReq.setCriticsRating(Double.valueOf(criticRatingET.getText().toString().trim()));
        if (movieDTO == null) {
            firestore.collection("movies")
                    .add(movieReq)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                DocumentReference documentReference = task.getResult();
                                if (documentReference != null) {
                                    String autoGeneratedDocumentId = documentReference.getId();
                                    movieReq.setDocumentId(autoGeneratedDocumentId);
                                    Toast.makeText(AddEditActivity.this, "Movie added successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            } else {
                                Toast.makeText(AddEditActivity.this, "Failed to add movie", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            firestore.collection("movies").document(documentId)
                    .set(movieReq)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddEditActivity.this, "Movie updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddEditActivity.this, "Failed to update movie", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}