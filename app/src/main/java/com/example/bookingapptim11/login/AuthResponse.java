package com.example.bookingapptim11.login;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AuthResponse implements Parcelable, Serializable {

    @SerializedName("token")
    @Expose
    String token;

    protected AuthResponse(Parcel in) {
        token = in.readString();
    }

    public static final Creator<AuthResponse> CREATOR = new Creator<AuthResponse>() {
        @Override
        public AuthResponse createFromParcel(Parcel in) {
            return new AuthResponse(in);
        }

        @Override
        public AuthResponse[] newArray(int size) {
            return new AuthResponse[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(token);
    }
}
