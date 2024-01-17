package clients;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private String jwtToken;

    public AuthInterceptor(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Add the JWT token to the headers
        Request requestWithToken = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + jwtToken)
                .build();

        return chain.proceed(requestWithToken);
    }
}