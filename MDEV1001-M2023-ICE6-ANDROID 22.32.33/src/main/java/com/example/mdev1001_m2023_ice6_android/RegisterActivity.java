package com.example.mdev1001_m2023_ice6_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstNameET;
    private EditText lastNameET;
    private EditText emailET;
    private EditText usernameET;
    private EditText passwordET;
    private EditText confirmpassET;
    private Button cancelBT;
    private Button registerBT;
    private AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        handleClick();
    }

    private void handleClick() {
        registerBT.setOnClickListener(v -> {
            if (!handleValidation()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = new User();
                        user.setFirstname(firstNameET.getText().toString().trim());
                        user.setLastname(lastNameET.getText().toString().trim());
                        user.setEmail(emailET.getText().toString().trim());
                        user.setUsername(usernameET.getText().toString().trim());
                        user.setPassword(passwordET.getText().toString().trim());
                        appDatabase.userDao().insert(user);
                        showToast("User registered successfully!");
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                }).start();
            }
        });
    }

    private void initUI() {
        firstNameET = (EditText) findViewById(R.id.firstNameET);
        lastNameET = (EditText) findViewById(R.id.lastNameET);
        emailET = (EditText) findViewById(R.id.emailET);
        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        confirmpassET = (EditText) findViewById(R.id.confirmpassET);
        cancelBT = (Button) findViewById(R.id.cancelBT);
        registerBT = (Button) findViewById(R.id.registerBT);
        appDatabase = ((MovieApplication) getApplication()).getAppDatabase();

    }

    private boolean handleValidation () {
        if (firstNameET.getText().toString().trim().isEmpty()) {
            showToast("Please enter first name");
        } else if (lastNameET.getText().toString().trim().isEmpty()) {
            showToast("Please enter last name");
        } else if (emailET.getText().toString().trim().isEmpty()) {
            showToast("Please enter email");
        } else if (usernameET.getText().toString().trim().isEmpty()) {
            showToast("Please enter username");
        } else if (passwordET.getText().toString().trim().isEmpty()) {
            showToast("Please enter password");
        } else if (confirmpassET.getText().toString().trim().isEmpty()) {
            showToast("Please enter confirm password");
        } else if (!passwordET.getText().toString().trim().equals(confirmpassET.getText().toString().trim())) {
            showToast("Password and confirm password doesn't match");
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