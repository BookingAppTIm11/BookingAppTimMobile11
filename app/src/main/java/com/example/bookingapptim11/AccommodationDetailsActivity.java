package com.example.bookingapptim11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import ui.AmenityDetailsFragment;

public class AccommodationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_details);
        if (getIntent().hasExtra("accommodation")) {
            AccommodationDetailsDTO accommodation = getIntent().getParcelableExtra("accommodation");

            AmenityDetailsFragment fragment = new AmenityDetailsFragment(accommodation);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentAccommodationDetailsContainer, fragment)
                    .commit();
        }
    }
}