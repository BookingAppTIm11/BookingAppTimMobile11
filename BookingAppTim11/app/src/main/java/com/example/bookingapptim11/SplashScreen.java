package com.example.bookingapptim11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }
    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Create a Handler to delay the navigation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Define the target activity you want to switch to
                Intent intent = new Intent(SplashScreen.this, HomeScreen.class);
                startActivity(intent);

                // Finish the current activity so that the user can't navigate back to it
                finish();
            }
        }, 5000); // 5000 milliseconds (5 seconds)
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