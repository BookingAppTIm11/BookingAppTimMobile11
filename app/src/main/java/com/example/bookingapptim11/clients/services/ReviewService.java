package com.example.bookingapptim11.clients.services;

import com.example.bookingapptim11.models.Review;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReviewService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reviews/{id}")
    Call<Review> getReviewById(@Path("id") Long id, @Header("Authorization") String authorizationHeader);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reviews")
    Call<ArrayList<Review>> getReviews();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reviews/owner/{email}")
    Call<ArrayList<Review>> getReviewsByOwnerEmail(@Path("email") String email, @Header("Authorization") String authorizationHeader);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reviews/accommodation/{id}")
    Call<ArrayList<Review>> getReviewsByAccommodationId(@Path("id") Long id, @Header("Authorization") String authorizationHeader);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("reviews")
    Call<Review> createReview(@Body Review reviewDTO, @Header("Authorization") String authorizationHeader);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("reviews/{id}")
    Call<Review> updateReview(@Path("id") Long id, @Body Review reviewDTO, @Header("Authorization") String authorizationHeader);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("reviews/{id}")
    Call<Void> deleteReview(@Path("id") Long id, @Header("Authorization") String authorizationHeader);
}
