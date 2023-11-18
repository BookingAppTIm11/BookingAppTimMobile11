package com.example.bookingapptim11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
    public void onAmenityClick(int position) {
        FrameLayout fragmentAmenityCardsContainer= findViewById(R.id.fragmentContainer);
        fragmentAmenityCardsContainer.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerAmenitiesDetails, new AmenityDetailsFragment())
                .commit();
    }
}