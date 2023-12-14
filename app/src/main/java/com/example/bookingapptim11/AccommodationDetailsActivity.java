package com.example.bookingapptim11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import models.Accommodation;
import ui.AccommodationDetailsFragment;

public class AccommodationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_details);
        if (getIntent().hasExtra("accommodation")) {
            Accommodation accommodation = getIntent().getParcelableExtra("accommodation");

            AccommodationDetailsFragment fragment = new AccommodationDetailsFragment(accommodation);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentAccommodationDetailsContainer, fragment)
                    .commit();
        }
    }
}