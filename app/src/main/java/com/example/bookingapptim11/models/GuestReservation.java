package com.example.bookingapptim11.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class GuestReservation implements Parcelable {

    Long id;
    Long accommodation;
    String startDate;
    String endDate;
    int numberOfGuests;
    ReservationStatus status;
    double price;
    List<String> photos;

    public GuestReservation(Long id, Long accommodation, String startDate, String endDate, int numberOfGuests, ReservationStatus status, double price, List<String> photos) {
        this.id = id;
        this.accommodation = accommodation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.price = price;
        this.photos = photos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Long accommodation) {
        this.accommodation = accommodation;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    protected GuestReservation(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            accommodation = null;
        } else {
            accommodation = in.readLong();
        }
        startDate = in.readString();
        endDate = in.readString();
        numberOfGuests = in.readInt();
        status = ReservationStatus.valueOf(in.readString());
        price = in.readDouble();
        photos = in.createStringArrayList();
    }

    public static final Creator<GuestReservation> CREATOR = new Creator<GuestReservation>() {
        @Override
        public GuestReservation createFromParcel(Parcel in) {
            return new GuestReservation(in);
        }

        @Override
        public GuestReservation[] newArray(int size) {
            return new GuestReservation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(accommodation);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeInt(numberOfGuests);
        parcel.writeString(status.name());
        parcel.writeDouble(price);
        parcel.writeStringList(photos);
    }
}
