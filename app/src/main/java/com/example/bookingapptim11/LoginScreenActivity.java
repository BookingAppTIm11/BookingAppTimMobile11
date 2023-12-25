package com.example.bookingapptim11;

import static clients.ClientUtils.authService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import login.AuthManager;
import login.AuthResponse;
import login.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreenActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button logIn;
    private TextView signUp;
    private ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        logIn = findViewById(R.id.logInBtn);
        signUp = findViewById(R.id.textViewSignUp);
        homeButton = findViewById(R.id.homeLoginImageButton);

        AuthManager.initialize(this);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToRegisterScreen();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToHomeScreen();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                Login login = new Login(userEmail,userPassword);

                loginUser(login);

                changeToHomeScreen();
            }
        });

    }
    private void loginUser(Login login) {
        Call<AuthResponse> call = authService.login(login);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AuthResponse authResponse = response.body();
                        String token = authResponse.getToken();
                        AuthManager.saveInfo(token);

                    } else {
                        Toast.makeText(LoginScreenActivity.this, "Wrong email or password! ", Toast.LENGTH_LONG).show();
                    }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(LoginScreenActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void changeToRegisterScreen(){
        Intent intent = new Intent(LoginScreenActivity.this, RegisterScreenActivity.class);
        startActivity(intent);
    }

    private void changeToHomeScreen(){
        Intent intent = new Intent(LoginScreenActivity.this, NavigationActivity.class);
        startActivity(intent);
    }

    private void putUserToPreferences(String token){
        SharedPreferences.Editor editor = getSharedPreferences("pref_file", MODE_PRIVATE).edit();
        editor.putString("token", token);
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        getDelegate().onStart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }
    @Override
    protected void onRestart(){
        super.onRestart();

    }
    @Override
    protected void onResume(){
        super.onResume();

    }
}