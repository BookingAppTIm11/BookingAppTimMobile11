package clients.services;

import models.AccommodationDetailsDTO;

import java.util.ArrayList;
import java.util.List;

import models.Profile;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccommodationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations")
    Call<ArrayList<AccommodationDetailsDTO>> getAccommodations();

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


}