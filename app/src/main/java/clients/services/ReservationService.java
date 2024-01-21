package clients.services;

import com.example.bookingapptim11.dto.NumberOfCancellationsDTO;
import com.example.bookingapptim11.models.Availability;
import com.example.bookingapptim11.models.GuestReservation;
import com.example.bookingapptim11.models.OwnerReservation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReservationService {

    @GET("reservations/owner/{email}")
    Call<ArrayList<OwnerReservation>> getOwnersReservations(@Path("email") String email);
    @GET("reservations/guest/{email}")
    Call<ArrayList<GuestReservation>> getGuestsReservations(@Path("email") String email);

    @GET("reservations/guest/{email}/cancellations")
    Call<NumberOfCancellationsDTO> getNumberOfCancellations(@Path("email") String email);

    @PUT("reservations/accept/{reservationId}")
    Call<OwnerReservation> acceptReservation(
            @Path("reservationId") Long reservationId
    );

    @PUT("reservations/decline/{reservationId}")
    Call<OwnerReservation> declineReservation(
            @Path("reservationId") Long reservationId
    );

    @PUT("reservations/cancel/{reservationId}")
    Call<GuestReservation> cancelReservation(
            @Path("reservationId") Long reservationId
    );
  
    @GET("reservations/owner/search")
    Call<ArrayList<OwnerReservation>> searchOwnersReservations(
            @Query("startDate") Long startDate,
            @Query("endDate") Long endDate,
            @Query("accommodationName") String accommodationName,
            @Query("email") String email
    );
    @GET("reservations/guest/search")
    Call<ArrayList<GuestReservation>> searchGuestReservations(
            @Query("startDate") Long startDate,
            @Query("endDate") Long endDate,
            @Query("accommodationName") String accommodationName,
            @Query("email") String email
    );
}
