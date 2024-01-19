package com.example.bookingapptim11.fragments;

import static clients.ClientUtils.accommodationService;
import static clients.ClientUtils.profileService;

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
import com.example.bookingapptim11.dto.NotificationDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import adapters.AccommodaitonAmenitiesDTOAdapter;
import adapters.NotificationAdapter;
import login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationOwnersFragment extends Fragment {

    RecyclerView notificationRecyclerView;
    private List<NotificationDTO> notificationDTOS;
    private NotificationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notification_owners, container, false);
        loadFavoriteAccommodations(root);
        return root;
    }

    private void loadFavoriteAccommodations(View root) {
        notificationRecyclerView = root.findViewById(R.id.notificationRecyclerView);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));


        Call<Collection<NotificationDTO>> call = profileService.getNotificationsByEmail(AuthManager.getUserEmail());
        call.enqueue(new Callback<Collection<NotificationDTO>>() {
            @Override
            public void onResponse(Call<Collection<NotificationDTO>> call, Response<Collection<NotificationDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    notificationDTOS = new ArrayList<>();
                    notificationDTOS.addAll(response.body());
                    Toast.makeText(getContext(), "Successfully loaded notifications! ", Toast.LENGTH_LONG).show();
                    refreshAccommodationAdapter();
                } else {
                    Toast.makeText(getContext(), "No notifications! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Collection<NotificationDTO>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refreshAccommodationAdapter() {
        adapter = new NotificationAdapter();
        adapter.setNotifications(notificationDTOS);
        notificationRecyclerView.setAdapter(adapter);
    }

}