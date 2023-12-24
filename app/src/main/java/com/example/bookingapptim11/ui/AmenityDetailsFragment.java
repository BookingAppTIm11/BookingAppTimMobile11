package com.example.bookingapptim11.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bookingapptim11.NavigationActivity;
import com.example.bookingapptim11.R;
import com.example.bookingapptim11.models.Accommodation;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.example.bookingapptim11.ui.util.MapFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AmenityDetailsFragment extends Fragment {

    AccommodationDetailsDTO accommodation;
    public AmenityDetailsFragment(AccommodationDetailsDTO accommodation){
        this.accommodation = accommodation;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_amenity_details, container, false);

        Fragment mapFragment = new MapFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.googleMaps, mapFragment);
        transaction.commit();

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
        searchInMap(accommodation.getLocation());

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


    private void searchInMap(String locationName) {
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.googleMaps);
        if (mapFragment != null) {
            mapFragment.searchLocation(locationName);
        }
    }
}