package com.example.ICE12;

import static com.example.ICE12.MovieApplication.apiService;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void hitLoginApi() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        firestore.collection("usernames").document(username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String email = document.getString("email");
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(this, authTask -> {
                                        if (authTask.isSuccessful()) {
                                            Toast.makeText(this, "User logged in successfully.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        } else {
                                            String errorMessage = authTask.getException() != null ?
                                                    authTask.getException().getMessage() :
                                                    "Authentication Failed";
                                            displayErrorMessage(errorMessage);
                                        }
                                    });
                        } else {
                            displayErrorMessage("Username not found.");
                        }
                    } else {
                        displayErrorMessage("Authentication Failed");
                    }
                });
    }

    private void displayErrorMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClearLoginTextFields();
                    }
                })
                .show();
    }

    private void ClearLoginTextFields() {
        usernameEditText.setText("");
        passwordEditText.setText("");
        usernameEditText.requestFocus();
    }


private void initUI() {
    firebaseAuth = FirebaseAuth.getInstance();
    firestore = FirebaseFirestore.getInstance();
        usernameEditText = findViewById(R.id.usernameET);
        passwordEditText = findViewById(R.id.passwordET);
        loginButton = findViewById(R.id.loginBT);
        registerButton = findViewById(R.id.registerBT);
        loginButton.setOnClickListener(v->{
            hitLoginApi();
        });

        registerButton.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}