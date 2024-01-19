package com.example.bookingapptim11.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class OwnerReservation implements Parcelable {

    Long id;
    Long accommodationId;
    String guest;
    String startDate;
    String endDate;
    int numberOfGuests;
    ReservationStatus status;
    double price;
    int cancelledReservations;

    public OwnerReservation(Long id, Long accommodationId, String guest, String startDate, String endDate, int numberOfGuests, ReservationStatus status, double price, int cancelledReservations) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.price = price;
        this.cancelledReservations = cancelledReservations;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setReservationStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCancelledReservations(int cancelledReservations) {
        this.cancelledReservations = cancelledReservations;
    }

    public Long getId() {
        return id;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public String getGuest() {
        return guest;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public int getCancelledReservations() {
        return cancelledReservations;
    }


    protected OwnerReservation(Parcel in) {
        id = in.readLong();
        accommodationId = in.readLong();
        guest = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        numberOfGuests = in.readInt();
        status = ReservationStatus.valueOf(in.readString());
        price = in.readDouble();
        cancelledReservations = in.readInt();
    }

    public static final Creator<OwnerReservation> CREATOR = new Creator<OwnerReservation>() {
        @Override
        public OwnerReservation createFromParcel(Parcel in) {
            return new OwnerReservation(in);
        }

        @Override
        public OwnerReservation[] newArray(int size) {
            return new OwnerReservation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(accommodationId);
        parcel.writeString(guest);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeInt(numberOfGuests);
        parcel.writeString(status.name());
        parcel.writeDouble(price);
        parcel.writeInt(cancelledReservations);
    }
}
