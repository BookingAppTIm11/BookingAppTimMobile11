package clients.services;

import com.example.bookingapptim11.models.Report;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReportService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reports")
    Call<ArrayList<Report>> getReports();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reports/{id}")
    Call<Report> getReportById(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("reports")
    Call<Report> createReport(@Body Report reportDTO, @Header("Authorization") String authorizationHeader);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("reports/{id}")
    Call<Report> updateReport(@Path("id") Long id, @Body Report reportDTO);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("users/{email}/block")
    Call<Void> blockUser(@Path("email") String email, @Header("Authorization") String authorizationHeader);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("reports/{id}")
    Call<Void> deleteReport(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("users/owners/{guestEmail}")
    Call<List<String>> getOwnersForGuestReport(@Path("guestEmail") String guestEmail, @Header("Authorization") String authorizationHeader);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("users/guests/{ownerEmail}")
    Call<List<String>> getGuestsForOwnerReport(@Path("ownerEmail") String ownerEmail, @Header("Authorization") String authorizationHeader);
}
