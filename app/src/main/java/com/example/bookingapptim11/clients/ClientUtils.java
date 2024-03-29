package com.example.bookingapptim11.clients;

import java.util.concurrent.TimeUnit;


import com.example.bookingapptim11.clients.services.AccommodationService;
import com.example.bookingapptim11.clients.services.ProfileService;
import com.example.bookingapptim11.clients.services.AccommodationCreationService;

import com.example.bookingapptim11.clients.services.AuthService;
import com.example.bookingapptim11.clients.services.ReportService;
import com.example.bookingapptim11.clients.services.ReservationService;
import com.example.bookingapptim11.clients.services.ReviewService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {

    public static final String SERVICE_API_PATH = "http://10.0.2.2:8083/api/";
    public static OkHttpClient test(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        return client;
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(test())
            .build();

    public static ProfileService profileService = retrofit.create(ProfileService.class);
    public static AccommodationService accommodationService = retrofit.create(AccommodationService.class);
    public static AuthService authService = retrofit.create(AuthService.class);

    //public static AccommodationService accommodationService = retrofit.create(AccommodationService.class);
    public static AccommodationCreationService accommodationCreationService = retrofit.create(AccommodationCreationService.class);
    public static ReviewService reviewService = retrofit.create(ReviewService.class);

    public static ReportService reportService = retrofit.create(ReportService.class);

    public static ReservationService reservationService = retrofit.create(ReservationService.class);

}
