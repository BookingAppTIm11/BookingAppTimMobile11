package login;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.bookingapptim11.NavigationActivity;
import com.example.bookingapptim11.R;
import com.example.bookingapptim11.interfaces.UserRoleChangeListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class AuthManager {
    private static final String SHARED_PREF_NAME = "pref_file";
    private static final String EMAIL_KEY = "username";
    private static final String ROLE_KEY = "role";
    private static final String TOKEN = "token";
    private static final String SIGNING_KEY = "korisnickoime";

    private static final List<UserRoleChangeListener> listeners = new ArrayList<>();


    private static SharedPreferences sharedPreferences;

    public static void initialize(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    /*public static String getToken() {
        checkSharedPreferences();
        return sharedPreferences.getString(TOKEN, null);
    }*/


    private static void checkSharedPreferences(){
        if (sharedPreferences == null) {
            throw new IllegalStateException("SharedPreferences not initialized. Call initialize method first.");
        }
    }
    public static void saveInfo(String token) {
        checkSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);

        JWT jwt = new JWT(token);
        Claim claim = jwt.getClaim("role");
        String role = claim.asString();
        String email = jwt.getSubject();


        if (email != null) {
            editor.putString(EMAIL_KEY, email);
            editor.putString(ROLE_KEY, role);
            editor.apply();
            notifyListeners();
        }
    }

    public static String getUserEmail() {
        checkSharedPreferences();
        return sharedPreferences.getString(EMAIL_KEY, null);
    }

    public static String getUserRole() {
        checkSharedPreferences();
        return sharedPreferences.getString(ROLE_KEY, null);
    }


    public static String logOut(Context context){
        checkSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getString(EMAIL_KEY, null) != null && sharedPreferences.getString(ROLE_KEY, null) != null) {
            editor.remove(EMAIL_KEY);
            editor.remove(ROLE_KEY);
            editor.apply();
            notifyListeners();

            return "Logout successful";
        }else {
            return "No user logged in";
        }
    }

    public static void addListener(UserRoleChangeListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(UserRoleChangeListener listener) {
        listeners.remove(listener);
    }

    private static void notifyListeners() {
        for (UserRoleChangeListener listener : listeners) {
            listener.onUserRoleChanged(sharedPreferences.getString(EMAIL_KEY, null));
        }
    }
}
