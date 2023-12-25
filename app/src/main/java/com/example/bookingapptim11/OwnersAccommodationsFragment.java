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
import adapters.OwnersAccommodationsAdapter;
import clients.ClientUtils;
import login.AuthManager;
import models.AccommodationDetailsDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnersAccommodationsFragment extends Fragment {


    public OwnersAccommodationsFragment() {
    }

    List<AccommodationDetailsDTO> accommodations;
    OwnersAccommodationsAdapter adapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_owners_accommodations, container, false);

        recyclerView = root.findViewById(R.id.ownersAccommodations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        accommodations = new ArrayList<>();
        Call<ArrayList<AccommodationDetailsDTO>> call = ClientUtils.accommodationService.getOwnersAccommodations(AuthManager.getUserEmail());
        call.enqueue(new Callback<ArrayList<AccommodationDetailsDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDetailsDTO>> call, Response<ArrayList<AccommodationDetailsDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accommodations = response.body();
                    Toast.makeText(getContext(), "Successfully loaded accommodations! ", Toast.LENGTH_LONG).show();
                    refreshOwnersAccommodationsAdapter();
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

    private void refreshOwnersAccommodationsAdapter() {
        adapter = new OwnersAccommodationsAdapter(getActivity(), accommodations);
        recyclerView.setAdapter(adapter);

    }
}