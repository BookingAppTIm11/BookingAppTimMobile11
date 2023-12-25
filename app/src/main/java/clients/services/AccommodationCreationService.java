package clients.services;

import com.example.bookingapptim11.accommodationCreation.AccommodationDetails;
import com.example.bookingapptim11.models.Amenity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AccommodationCreationService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("amenities")
    Call<List<Amenity>> getAllAmenities();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodations")
    Call<AccommodationDetails> createAccommodation(@Body AccommodationDetails accommodation);

}
