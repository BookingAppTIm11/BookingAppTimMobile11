package clients.services;

import com.example.bookingapptim11.models.Availability;
import com.example.bookingapptim11.models.OwnerReservation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReservationService {

    @GET("reservations/owner/{email}")
    Call<ArrayList<OwnerReservation>> getOwnersReservations(@Path("email") String email);
    @PUT("reservations/accept/{reservationId}")
    Call<OwnerReservation> acceptReservation(
            @Path("reservationId") Long reservationId
    );

    @PUT("reservations/decline/{reservationId}")
    Call<OwnerReservation> declineReservation(
            @Path("reservationId") Long reservationId
    );
}
