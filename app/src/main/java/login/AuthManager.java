package login;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

public class AuthManager {
    private static final String SHARED_PREF_NAME = "pref_file";
    private static final String EMAIL_KEY = "username";
    private static final String ROLE_KEY = "role";
    private static final String TOKEN = "role";
    private static final String SIGNING_KEY = "korisnickoime";

    private static SharedPreferences sharedPreferences;

    public static void initialize(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void saveInfo(String token) {
        if (sharedPreferences == null) {
            throw new IllegalStateException("SharedPreferences not initialized. Call initialize method first.");
        }
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
        }
    }

    public static String getUserEmail() {
        if (sharedPreferences == null) {
            throw new IllegalStateException("SharedPreferences not initialized. Call initialize method first.");
        }
        return sharedPreferences.getString(EMAIL_KEY, null);
    }

    public static String getUserRole() {
        if (sharedPreferences == null) {
            throw new IllegalStateException("SharedPreferences not initialized. Call initialize method first.");
        }
        return sharedPreferences.getString(ROLE_KEY, null);
    }


}
