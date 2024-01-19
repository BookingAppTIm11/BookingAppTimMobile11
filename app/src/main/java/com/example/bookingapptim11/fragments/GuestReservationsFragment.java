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
import com.example.bookingapptim11.models.GuestReservation;
import com.example.bookingapptim11.models.OwnerReservation;

import java.util.ArrayList;
import java.util.List;

import adapters.GuestReservationsAdapter;
import adapters.OwnerReservationsAdapter;
import clients.ClientUtils;
import login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GuestReservationsFragment extends Fragment {

    List<GuestReservation> reservations;
    GuestReservationsAdapter adapter;
    RecyclerView recyclerView;

    public GuestReservationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_guest_reservations, container, false);

        recyclerView = root.findViewById(R.id.guestsReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        reservations = new ArrayList<>();
        Call<ArrayList<GuestReservation>> call = ClientUtils.reservationService.getGuestsReservations(AuthManager.getUserEmail());
        call.enqueue(new Callback<ArrayList<GuestReservation>>() {
            @Override
            public void onResponse(Call<ArrayList<GuestReservation>> call, Response<ArrayList<GuestReservation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reservations = response.body();
                    refreshGuestsReservationsAdapter();
                } else {
                    Toast.makeText(getContext(), "No reservations! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<GuestReservation>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    private void refreshGuestsReservationsAdapter() {
        adapter = new GuestReservationsAdapter(getActivity(), reservations);
        recyclerView.setAdapter(adapter);
    }
}