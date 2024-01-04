package com.example.bookingapptim11.accommodationCreation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlot implements Parcelable {

    @SerializedName("startEpochTime")
    @Expose
    private Long startDate;
    @SerializedName("endEpochTime")
    @Expose
    private Long endDate;

    public TimeSlot() {
    }

    public TimeSlot(Long startDate, Long endDate) {
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

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
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
