package com.example.bookingapptim11.clients.services;

import com.example.bookingapptim11.login.AuthResponse;
import com.example.bookingapptim11.login.Login;
import com.example.bookingapptim11.registration.UserRegistration;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("auth/logIn")
    Call<AuthResponse> login(@Body Login logIn);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("auth/logIn")
    Call<String> logOut();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("users/register")
    Call<UserRegistration> register(@Body UserRegistration registeredUser) throws Exception;


}
