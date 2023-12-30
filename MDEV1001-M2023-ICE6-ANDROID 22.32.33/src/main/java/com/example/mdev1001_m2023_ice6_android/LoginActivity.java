package com.example.mdev1001_m2023_ice6_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button registerBT;
    private EditText usernameET;
    private EditText passwordET;
    private Button loginBT;
    private AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        handleClick();
    }

    private void handleClick() {
        registerBT.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        loginBT.setOnClickListener(v -> {
            if (!handleValidation()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String username = usernameET.getText().toString().trim();
                        String password = passwordET.getText().toString().trim();
                    User user = appDatabase.userDao().getUserByUsernameAndPassword(username, password);
                        if (user != null) {
                            showToast("Login successful!");
                           startActivity(new Intent(getApplication(),MainActivity.class));
                        } else {
                            showToast("Invalid username or password");
                        }
                    }
                }).start();
            }
        });

    }

    private void initUI() {
        registerBT = (Button) findViewById(R.id.registerBT);
        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        loginBT = (Button) findViewById(R.id.loginBT);
        appDatabase = ((MovieApplication) getApplication()).getAppDatabase();
    }

    private boolean handleValidation () {
        if (usernameET.getText().toString().trim().isEmpty()) {
            showToast("Please enter username");
        } else if (passwordET.getText().toString().trim().isEmpty()) {
            showToast("Please enter password");
        } else {
            return false;
        }
        return true;
    }


    private void showToast(String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}