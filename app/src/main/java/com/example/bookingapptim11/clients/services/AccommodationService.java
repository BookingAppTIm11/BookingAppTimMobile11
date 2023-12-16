package com.example.bookingapptim11.clients.services;

import com.example.bookingapptim11.login.AuthResponse;
import com.example.bookingapptim11.login.Login;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AccommodationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodations")
    Call<ArrayList<AccommodationDetailsDTO>> getAccommodations();
}
