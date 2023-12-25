package clients.services;

import models.AccommodationDetailsDTO;
import models.Availability;
import models.ReservationDTO;
import models.ReservationForShowDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
            @Query("startDate") String startDate, // Assuming you pass date strings in a specific format
            @Query("endDate") String endDate
    );

    @GET("availabilities/accommodation/{accommodation_id}")
    Call<ArrayList<Availability>> getAccommodationAvailability(@Path("accommodation_id") Long accommodationId);

    @POST("reservations") // Replace "your_endpoint_path" with the actual endpoint path
    Call<ReservationForShowDTO> createReservation(@Body ReservationDTO reservationDTO);
}
