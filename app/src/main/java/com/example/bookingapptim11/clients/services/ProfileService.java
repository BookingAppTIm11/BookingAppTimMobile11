package com.example.bookingapptim11.clients.services;

import com.example.bookingapptim11.dto.NotificationDTO;
import com.example.bookingapptim11.models.Profile;
import com.example.bookingapptim11.models.ReservationForShowDTO;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfileService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("users/{email}")
    Call<Profile> getById(@Path("email") String email);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("users/{email}/update")
    Call<Profile> edit(@Path("email") String email, @Body Profile profile, @Header("Authorization") String authorizationHeader);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("users/{email}")
    Call<Void> delete(@Path("email") String email, @Header("Authorization") String authorizationHeader);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("notifications/user/{email}")
    Call<Collection<NotificationDTO>> getNotificationsByEmail(@Path("email") String userEmail);

    @POST("notifications")
    Call<NotificationDTO> createNotification(@Body NotificationDTO notificationDTO);
}
