package clients.services;

import com.example.bookingapptim11.accommodationCreation.AccommodationDetails;
import com.example.bookingapptim11.models.Amenity;
import com.example.bookingapptim11.models.Availability;
import com.example.bookingapptim11.models.Price;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/{id}/amenity")
    Call<List<Amenity>> getAmenitiesByAccommodationId(@Path("id")Long id);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("availabilities/available/{id}")
    Call<List<Availability>> getAvailabilitiesByAccommodationId(@Path("id")Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/prices/{id}")
    Call<List<Price>> getPricesByAccommodationId(@Path("id")Long id);


    @Multipart
    @POST("files/uploadMobile")
    Call<List<String>> uploadPhotos(@Part List<MultipartBody.Part> files);

}
