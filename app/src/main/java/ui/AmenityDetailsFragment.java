package ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookingapptim11.LoginScreenActivity;
import com.example.bookingapptim11.NavigationActivity;
import com.example.bookingapptim11.R;

import models.Accommodation;


public class AmenityDetailsFragment extends Fragment {

    Accommodation accommodation;
    public AmenityDetailsFragment(Accommodation accommodation){
        this.accommodation = accommodation;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_amenity_details, container, false);
        TextView accommodationName = root.findViewById(R.id.accommodationName);
        TextView locationTextView = root.findViewById(R.id.locationTextView);
        TextView priceTextView = root.findViewById(R.id.priceTextView);
        TextView accommodationCapacityTextView = root.findViewById(R.id.accommodationCapacityTextView);
        TextView ratingTextView = root.findViewById(R.id.ratingTextView);
        // ... find other views similarly

        // Set values from the Accommodation object to the views
        accommodationName.setText(accommodation.getName());
        locationTextView.setText(accommodation.getLocation());
        priceTextView.setText("$" + String.valueOf(accommodation.getPrice()));
        accommodationCapacityTextView.setText(String.valueOf(accommodation.getCapacity()));
        ratingTextView.setText(String.valueOf(accommodation.getRating() + "/5.0"));


        ImageButton homeButton = root.findViewById(R.id.homeAccommodationDetailsImageButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
            }
        });
        return  root;
    }
}