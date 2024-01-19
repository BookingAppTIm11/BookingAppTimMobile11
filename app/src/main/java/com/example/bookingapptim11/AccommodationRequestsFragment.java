package com.example.bookingapptim11;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.AccommodationRequestAdapter;
import clients.ClientUtils;

import com.example.bookingapptim11.models.AccommodationDetailsDTO;

import login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccommodationRequestsFragment extends Fragment {

    public AccommodationRequestsFragment() {

    }
    List<AccommodationDetailsDTO> accommodations;
    AccommodationRequestAdapter adapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_accommodation_requests, container, false);

        recyclerView = root.findViewById(R.id.accommodationRequests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        accommodations = new ArrayList<>();
        Call<ArrayList<AccommodationDetailsDTO>> call = ClientUtils.accommodationService.getInactiveAccommodations("Bearer " + AuthManager.getToken());
        call.enqueue(new Callback<ArrayList<AccommodationDetailsDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDetailsDTO>> call, Response<ArrayList<AccommodationDetailsDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accommodations = response.body();
                    Toast.makeText(getContext(), "Successfully loaded accommodations! ", Toast.LENGTH_LONG).show();
                    refreshAccommodationRequestsAdapter();
                } else {
                    Toast.makeText(getContext(), "No accommodations! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AccommodationDetailsDTO>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    private void refreshAccommodationRequestsAdapter() {
        adapter = new AccommodationRequestAdapter(getActivity(), accommodations);
        recyclerView.setAdapter(adapter);

    }

}