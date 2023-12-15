package login;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class AuthManager {

    private static final String SHARED_PREF_NAME = "pref_file";
    private static final String EMAIL_KEY = "username";
    private static final String ROLE_KEY = "role";
    private static final String TOKEN = "role";

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
        editor.putString(TOKEN,token);

        Jws<Claims> claims = Jwts.parserBuilder().build().parseClaimsJws(token);
        Claims body = claims.getBody();

        String email = body.getSubject();

        if (email != null) {
            editor.putString(EMAIL_KEY, email);
            editor.putString(ROLE_KEY, (String) body.get("role"));
            editor.apply();
        }
    }

    public static String getUserEmail() {
        if (sharedPreferences == null) {
            throw new IllegalStateException("SharedPreferences not initialized. Call initialize method first.");
        }
        return sharedPreferences.getString(EMAIL_KEY, null);
    }

    public static String getUserRole(){
        if (sharedPreferences == null) {
            throw new IllegalStateException("SharedPreferences not initialized. Call initialize method first.");
        }
        return sharedPreferences.getString(ROLE_KEY, null);
    }


}
