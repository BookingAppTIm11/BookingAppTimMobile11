package com.example.bookingapptim11.registration;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserRegistration implements Parcelable , Serializable {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("isActive")
    @Expose
    private boolean isActive;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("role")
    @Expose
    private String role;


    public UserRegistration(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber, String role) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
