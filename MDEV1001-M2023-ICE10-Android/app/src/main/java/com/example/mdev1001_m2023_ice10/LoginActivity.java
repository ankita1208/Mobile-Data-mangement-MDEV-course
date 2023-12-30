package com.example.mdev1001_m2023_ice10;

import static com.example.mdev1001_m2023_ice10.MovieApplication.apiService;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void hitLoginApi() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        Call<LoginResponse> call = apiService.login(credentials);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.isSuccess() && loginResponse.getToken() != null) {
                        Toast.makeText(LoginActivity.this, "User logged in successfully.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        String errorMessage = loginResponse != null && loginResponse.getMsg() != null
                                ? loginResponse.getMsg()
                                : "Unknown error";
                        Toast.makeText(LoginActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to send request.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed to send request: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
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