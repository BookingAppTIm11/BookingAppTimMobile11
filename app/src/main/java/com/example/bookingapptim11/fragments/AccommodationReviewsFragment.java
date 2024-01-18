package com.example.bookingapptim11.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.models.Review;

import java.util.ArrayList;
import java.util.List;

import adapters.ReviewAdapter;
import clients.ClientUtils;
import login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationReviewsFragment extends Fragment {

    public AccommodationReviewsFragment() {
    }
    List<Review> accommodationReviews;
    ReviewAdapter adapter;
    RecyclerView recyclerView;
    String ownerEmail;


    public static AccommodationReviewsFragment newInstance(Long accommodationId, String ownerEmail) {
        AccommodationReviewsFragment fragment = new AccommodationReviewsFragment();
        Bundle args = new Bundle();
        args.putLong("accommodationId", accommodationId);
        args.putString("ownerEmail", ownerEmail);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_owner_reviews, container, false);
        ownerEmail = getArguments().getString("ownerEmail", "");

        recyclerView = root.findViewById(R.id.ownerReviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        accommodationReviews = new ArrayList<>();
        Long accommodationId = getArguments().getLong("accommodationId", 0L);
        Call<ArrayList<Review>> call = ClientUtils.reviewService.getReviewsByAccommodationId(accommodationId, "Bearer " + AuthManager.getToken());
        call.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accommodationReviews = response.body();
//                    Toast.makeText(getContext(), "Successfully loaded accommodation reviews! ", Toast.LENGTH_LONG).show();
                    refreshOwnerReviewsAdapter();
                } else {
//                    Toast.makeText(getContext(), "No owner reviews! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
    private void refreshOwnerReviewsAdapter() {
        adapter = new ReviewAdapter(getActivity(), accommodationReviews,ownerEmail);
        recyclerView.setAdapter(adapter);
    }
}