package com.example.bookingapptim11.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.models.Review;

import java.util.ArrayList;
import java.util.List;

import com.example.bookingapptim11.adapters.AdminReviewsAdapter;
import com.example.bookingapptim11.clients.ClientUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminRatingsFragment extends Fragment {

    List<Review> reviews;
    AdminReviewsAdapter adapter;
    RecyclerView recyclerView;

    public AdminRatingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_ratings, container, false);

        recyclerView = root.findViewById(R.id.adminRatings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        reviews = new ArrayList<>();
        Call<ArrayList<Review>> call = ClientUtils.reviewService.getReviews();
        call.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Review review : response.body()) {
                        if (review.isReported() || !review.isApproved()) {
                            reviews.add(review);
                        }
                    }

                    refreshAdminReviewsAdapter();
                } else {
                    Toast.makeText(getContext(), "No reservations! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    private void refreshAdminReviewsAdapter() {
        adapter = new AdminReviewsAdapter(getActivity(), reviews);
        recyclerView.setAdapter(adapter);
    }
}