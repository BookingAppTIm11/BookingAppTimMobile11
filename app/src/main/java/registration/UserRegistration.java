package registration;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UserRegistration implements Parcelable {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("name")
    private String name;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("address")
    private String address;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("role")
    private String role;

    protected UserRegistration(Parcel in) {
        email = in.readString();
        password = in.readString();
        isActive = in.readByte() != 0;
        name = in.readString();
        lastName = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        role = in.readString();
    }

    public static final Creator<UserRegistration> CREATOR = new Creator<UserRegistration>() {
        @Override
        public UserRegistration createFromParcel(Parcel in) {
            return new UserRegistration(in);
        }

        @Override
        public UserRegistration[] newArray(int size) {
            return new UserRegistration[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeString(name);
        dest.writeString(lastName);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeString(role);
    }
}
