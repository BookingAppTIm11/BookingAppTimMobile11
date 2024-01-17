package clients.services;

import android.database.Observable;

import com.example.bookingapptim11.dto.AccommodationWithAmenitiesDTO;
import com.example.bookingapptim11.dto.FavoriteAccommodationDTO;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.example.bookingapptim11.models.Availability;
import com.example.bookingapptim11.models.AvailabilityDateNum;
import com.example.bookingapptim11.models.ReservationDTO;
import com.example.bookingapptim11.models.ReservationForShowDTO;

import java.util.ArrayList;

import login.AuthManager;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.PUT;

public interface AccommodationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations")
    Call<ArrayList<AccommodationDetailsDTO>> getAccommodations();


    @GET("accommodations/search/detailed")
    Call<ArrayList<AccommodationDetailsDTO>> searchAccommodations(
            @Query("guests") Integer guests,
            @Query("location") String location,
            @Query("startDate") Long startDate, // Assuming you pass date strings in a specific format
            @Query("endDate") Long endDate
    );

    @GET("availabilities/accommodation/{accommodation_id}")
    Call<ArrayList<Availability>> getAccommodationAvailability(@Path("accommodation_id") Long accommodationId);

    @POST("reservations")
    Call<ReservationForShowDTO> createReservation(@Body ReservationDTO reservationDTO);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/inactive")
    Call<ArrayList<AccommodationDetailsDTO>> getInactiveAccommodations();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/owner/{email}")
    Call<ArrayList<AccommodationDetailsDTO>> getOwnersAccommodations(@Path("email") String email);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/{id}")
    Call<AccommodationDetailsDTO> getAccommodation(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodations/{id}")
    Call<AccommodationDetailsDTO> updateAccommodation(@Path("id") Long id, @Body AccommodationDetailsDTO accommodationDetailsDTO);


    @PUT("users/{username}/favorite_accommodation")
    Call<FavoriteAccommodationDTO> setFavoriteAccommodation(
            @Path("username") String username,
            @Body FavoriteAccommodationDTO param
    );
    @GET("users/{username}/favorite_accommodation/{accommodationId}")
    Call<FavoriteAccommodationDTO> isUsersFavoriteAccommodation(
            @Path("username") String username,
            @Path("accommodationId") Long accommodationId
    );


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type: application/json"
    })
    @GET("users/{username}/favorite_accommodation")
    Call<ArrayList<AccommodationWithAmenitiesDTO>> getFavoriteAccommodationsForGuest(
            @Path("username") String userEmail,
            @Header("Authorization") String authorizationHeader
    );
}

