package com.example.bookingapptim11.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingapptim11.accommodationCreation.TimeSlot;

import java.time.LocalDate;

public class Availability implements Parcelable {

    private Long id;

    private TimeSlot timeSlot;

    public Availability() {
    }


    public Availability(Long id, TimeSlot timeSlot) {
        this.id = id;
        this.timeSlot = timeSlot;
    }

    public Availability(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }


    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    protected Availability(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
    }

    public static final Creator<Availability> CREATOR = new Creator<Availability>() {
        @Override
        public Availability createFromParcel(Parcel in) {
            return new Availability(in);
        }

        @Override
        public Availability[] newArray(int size) {
            return new Availability[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }

    }
}
