package com.example.bookingapptim11.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bookingapptim11.LoginScreenActivity;
import com.example.bookingapptim11.NavigationActivity;
import com.example.bookingapptim11.R;
import com.example.bookingapptim11.SplashScreenActivity;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;


public class AmenityDetailsFragment extends Fragment implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private GoogleMap googleMap;
    AccommodationDetailsDTO accommodation;
    public AmenityDetailsFragment(AccommodationDetailsDTO accommodation){
        this.accommodation = accommodation;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_amenity_details, container, false);


        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMaps);
        mapFragment.getMapAsync(this);

        TextView accommodationName = root.findViewById(R.id.accommodationName);
        TextView locationTextView = root.findViewById(R.id.locationTextView);
        TextView priceTextView = root.findViewById(R.id.priceTextView);
        TextView accommodationCapacityTextView = root.findViewById(R.id.accommodationCapacityTextView);
        TextView ratingTextView = root.findViewById(R.id.ratingTextView);
        ImageSlider imageSlider = root.findViewById(R.id.imageSlider);
        // ... find other views similarly

        // Set values from the Accommodation object to the views
        accommodationName.setText(accommodation.getName());
        locationTextView.setText(accommodation.getLocation());
        priceTextView.setText("$" + String.valueOf(accommodation.getDefaultPrice()));
        accommodationCapacityTextView.setText(String.valueOf(accommodation.getMinGuests() + "-" + accommodation.getMaxGuests()));
        ratingTextView.setText(String.valueOf("4.8"+"/5.0"));
        //searchInMap(accommodation.getLocation());

        loadImagesToSlider(imageSlider);


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

    private void loadImagesToSlider(ImageSlider imageSlider) {
        if(accommodation.getPhotos().size() > 0){
            ArrayList<SlideModel> slideModels = new ArrayList<>();
            for(String photo: accommodation.getPhotos()){
                slideModels.add(new SlideModel("http://10.0.2.2:8083/pictures/"+photo, ScaleTypes.FIT));
            }
            imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with location-related tasks
                // ... (e.g., start location updates)
            } else {
                // Permission denied, handle this case (e.g., show a message to the user)
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Customize the map as needed (e.g., set markers, move camera, etc.)
        // Example:
        if (googleMap != null) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            // Add markers, set camera position, etc.
        }
    }
}