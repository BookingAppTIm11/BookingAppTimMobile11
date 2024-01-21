package com.example.bookingapptim11.fragments;

import static com.example.bookingapptim11.clients.ClientUtils.accommodationService;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.dto.AccommodationWithAmenitiesDTO;

import java.util.ArrayList;
import java.util.List;

import com.example.bookingapptim11.adapters.AccommodaitonAmenitiesDTOAdapter;

import com.example.bookingapptim11.login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteAccommodationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private AccommodaitonAmenitiesDTOAdapter accommodationAdapter;

    private List<AccommodationWithAmenitiesDTO> favoriteAccommodations;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favorite_accommodations, container, false);

        loadFavoriteAccommodations(root);

        return root;
    }

    private void loadFavoriteAccommodations(View root) {
        recyclerView = root.findViewById(R.id.favoriteAccommodationRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        Call<ArrayList<AccommodationWithAmenitiesDTO>> call = accommodationService.getFavoriteAccommodationsForGuest(AuthManager.getUserEmail(), "Bearer "+AuthManager.getToken());
        call.enqueue(new Callback<ArrayList<AccommodationWithAmenitiesDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationWithAmenitiesDTO>> call, Response<ArrayList<AccommodationWithAmenitiesDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    favoriteAccommodations = response.body();
                    Toast.makeText(getContext(), "Successfully loaded accommodations! ", Toast.LENGTH_LONG).show();
                    refreshAccommodationAdapter();
                } else {
                    Toast.makeText(getContext(), "No accommodations! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AccommodationWithAmenitiesDTO>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refreshAccommodationAdapter() {
        accommodationAdapter = new AccommodaitonAmenitiesDTOAdapter();
        accommodationAdapter.setAccommodations(favoriteAccommodations);
        recyclerView.setAdapter(accommodationAdapter);
    }
}