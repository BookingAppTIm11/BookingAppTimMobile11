package com.example.bookingapptim11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookingapptim11.models.Accommodation;
import com.example.bookingapptim11.ui.accommodations.AccommodationCardsFragment;
import com.example.bookingapptim11.ui.accommodations.AccommodationDetailsFragment;

public class AmenitiesActivity extends AppCompatActivity implements AccommodationCardsFragment.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenities);

        if (savedInstanceState == null) {
            AccommodationCardsFragment fragment = new AccommodationCardsFragment();

            // Set the listener for item clicks in the fragment
            fragment.setOnItemClickListener(this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        }
        // Implement the interface method from AmenityCardsFragment


    }

    @Override
    public void onAmenityClick(Accommodation accommodation) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new AccommodationDetailsFragment(accommodation))
                .addToBackStack("name")
                .commit();
    }
}