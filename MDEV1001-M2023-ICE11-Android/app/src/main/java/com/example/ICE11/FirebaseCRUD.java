package com.example.ICE11;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseCRUD {
    private static final String COLLECTION_NAME = "movies";
    private FirebaseFirestore db;

    public FirebaseCRUD() {
        db = FirebaseFirestore.getInstance();
    }

    public void addMovie(MovieDTO movie, OnCompleteListener<DocumentReference> onCompleteListener) {
        db.collection(COLLECTION_NAME)
                .add(movie)
                .addOnCompleteListener(onCompleteListener);
    }

    public void editMovie(String documentId, MovieDTO updatedMovie, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME)
                .document(documentId)
                .set(updatedMovie)
                .addOnCompleteListener(onCompleteListener);
    }

    public void deleteMovie(String documentId, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME)
                .document(documentId)
                .delete()
                .addOnCompleteListener(onCompleteListener);
    }
}

