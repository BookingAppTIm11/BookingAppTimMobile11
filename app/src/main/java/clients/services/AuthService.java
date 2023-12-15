package clients.services;

import login.AuthResponse;
import login.Login;
import registration.UserRegistration;
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
