package com.example.bookingapptim11.accommodationCreation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class TimeSlot implements Parcelable {

    @SerializedName("startDate")
    @Expose
    private LocalDate startDate;
    @SerializedName("endDate")
    @Expose
    private LocalDate endDate;

    public TimeSlot() {
    }

    public TimeSlot(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected TimeSlot(Parcel in) {
    }



    public static final Creator<TimeSlot> CREATOR = new Creator<TimeSlot>() {
        @Override
        public TimeSlot createFromParcel(Parcel in) {
            return new TimeSlot(in);
        }

        @Override
        public TimeSlot[] newArray(int size) {
            return new TimeSlot[size];
        }
    };

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}
