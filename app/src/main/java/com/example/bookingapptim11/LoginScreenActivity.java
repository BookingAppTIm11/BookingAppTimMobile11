package com.example.bookingapptim11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button logIn= findViewById(R.id.logInBtn);



        TextView signUp = findViewById(R.id.textViewSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreenActivity.this, RegisterScreenActivity.class);
                startActivity(intent);
            }
        });

        ImageButton homeButton = findViewById(R.id.homeLoginImageButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreenActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

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