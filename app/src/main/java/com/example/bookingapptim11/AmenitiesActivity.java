package com.example.bookingapptim11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookingapptim11.models.Accommodation;
import ui.AmenityCardsFragment;
import ui.AmenityDetailsFragment;

public class AmenitiesActivity extends AppCompatActivity implements AmenityCardsFragment.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenities);

        if (savedInstanceState == null) {
            AmenityCardsFragment fragment = new AmenityCardsFragment();

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
                .replace(R.id.fragmentContainer, new AmenityDetailsFragment(accommodation))
                .addToBackStack("name")
                .commit();
    }
}