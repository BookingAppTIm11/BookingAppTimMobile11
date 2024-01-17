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
import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.example.bookingapptim11.models.Review;

import java.util.ArrayList;
import java.util.List;

import adapters.AccommodationRequestAdapter;
import adapters.ReviewAdapter;
import clients.ClientUtils;
import login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerReviewsFragment extends Fragment {

    public OwnerReviewsFragment() {
    }

    List<Review> ownerReviews;
    ReviewAdapter adapter;
    RecyclerView recyclerView;
    String ownerEmail;

    public static OwnerReviewsFragment newInstance(String ownerEmail) {
        OwnerReviewsFragment fragment = new OwnerReviewsFragment();
        Bundle args = new Bundle();
        args.putString("ownerEmail", ownerEmail);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_owner_reviews, container, false);

        recyclerView = root.findViewById(R.id.ownerReviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        ownerReviews = new ArrayList<>();
        ownerEmail = getArguments().getString("ownerEmail", "");
        Call<ArrayList<Review>> call = ClientUtils.reviewService.getReviewsByOwnerEmail(ownerEmail, "Bearer " + AuthManager.getToken());
        call.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ownerReviews = response.body();
                    Log.d("REVIEWS", ownerReviews.toString());
                    Toast.makeText(getContext(), "Successfully loaded owner reviews! ", Toast.LENGTH_LONG).show();
                    refreshOwnerReviewsAdapter();
                } else {
                    Toast.makeText(getContext(), "No owner reviews! ", Toast.LENGTH_LONG).show();
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
        adapter = new ReviewAdapter(getActivity(), ownerReviews,ownerEmail);
        recyclerView.setAdapter(adapter);
    }
}