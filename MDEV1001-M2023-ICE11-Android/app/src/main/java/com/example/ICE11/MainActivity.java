package com.example.ICE11;

import static com.example.ICE11.MovieApplication.apiService;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    private Button logoutButton;
    private static final long UPDATE_INTERVAL = 5000; // 5 seconds
    private Handler updateHandler;
    private Runnable updateRunnable;
    private long lastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyADyYAwrfId6kvcpYavp4v7c5eL1Dvtx3Y")
                .setApplicationId("1:82028275109:android:b814d5f6fab5bf8cadf44c")
                .setProjectId("mdev1001-ice")
                .build();
        FirebaseApp.initializeApp(this, options);
        initUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
        hitGetMoviesApi();

    }






    private void stopUpdateHandler() {
        updateHandler.removeCallbacks(updateRunnable);
    }

    private void hitGetMoviesApi() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference moviesCollection = db.collection("movies");

        moviesCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    itemList = querySnapshot.toObjects(MovieDTO.class);
                    for (int i = 0; i < itemList.size(); i++) {
                        MovieDTO movie = itemList.get(i);
                        movie.setDocumentId(querySnapshot.getDocuments().get(i).getId());
                    }
                    setAdapter();
                } else {
                    Toast.makeText(MainActivity.this, "No movies found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch movies.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
        recyclerView = findViewById(R.id.moviesRL);
        addButton = findViewById(R.id.addBT);
        logoutButton = findViewById(R.id.logoutButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(v->{
            logoutButtonPressed();
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
            intent.putExtra("documentId", itemList.get(position).getDocumentId());
            startActivity(intent);
        } else if (type == 1) {
            deleteMovie(position);
        }
    }

    private void deleteMovie(int position) {
        MovieDTO movieToDelete = itemList.get(position);
        String documentId = movieToDelete.getDocumentId();
        if (documentId != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("movies").document(documentId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            itemList.remove(position);
                            setAdapter();
                            Toast.makeText(MainActivity.this, "Movie deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Failed to delete movie", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "Document ID not available for deletion", Toast.LENGTH_SHORT).show();
        }
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

    public void logoutButtonPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
