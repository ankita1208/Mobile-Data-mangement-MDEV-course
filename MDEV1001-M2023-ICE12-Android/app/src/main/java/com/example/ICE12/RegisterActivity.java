package com.example.ICE12;

import static com.example.ICE12.MovieApplication.apiService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;
    private Button cancelButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
    }

    private void initUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firstNameEditText = findViewById(R.id.firstNameET);
        lastNameEditText = findViewById(R.id.lastNameET);
        emailEditText = findViewById(R.id.emailET);
        usernameEditText = findViewById(R.id.usernameET);
        passwordEditText = findViewById(R.id.passwordET);
        confirmPasswordEditText = findViewById(R.id.confirmpassET);
        registerButton = findViewById(R.id.registerBT);
        cancelButton = findViewById(R.id.cancelBT);
        cancelButton.setOnClickListener(view -> {
            finish();
        });
        registerButton.setOnClickListener(v -> {
            hitRegisterApi();
        });
    }

    private void hitRegisterApi() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase registration
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        String userId = firebaseAuth.getCurrentUser().getUid();

                        // Store the username and email mapping in Firestore
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("FirstName", firstName);
                        userMap.put("LastName", lastName);
                        userMap.put("EmailAddress", email);
                        userMap.put("username", username);

                        firestore.collection("usernames").document(userId)
                                .set(userMap)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(RegisterActivity.this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(RegisterActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}