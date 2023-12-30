package com.example.mdev1001_m2023_ice10;

import static com.example.mdev1001_m2023_ice10.MovieApplication.apiService;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
    }

    private void initUI() {
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

        Map<String, String> data = new HashMap<>();
        data.put("FirstName", firstName);
        data.put("LastName", lastName);
        data.put("EmailAddress", email);
        data.put("username", username);
        data.put("password", password);

        Call<RegisterResponse> call = apiService.register(data);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse != null && registerResponse.isSuccess()) {
                        Toast.makeText(RegisterActivity.this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String errorMessage = registerResponse != null && registerResponse.getMsg() != null
                                ? registerResponse.getMsg()
                                : "Unknown error";
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Failed to send request.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Failed to send request: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}