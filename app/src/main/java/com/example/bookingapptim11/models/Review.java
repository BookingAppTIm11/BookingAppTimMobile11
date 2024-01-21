package com.example.bookingapptim11.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Review implements Parcelable {


    public Review(Long id, String guestEmail, String description, int rating, Long date, boolean reported, String ownerEmail, Long accommodationId, boolean approved) {
        this.id = id;
        this.guestEmail = guestEmail;
        this.description = description;
        this.rating = rating;
        this.date = date;
        this.reported = reported;
        this.ownerEmail = ownerEmail;
        this.accommodationId = accommodationId;
        this.approved = approved;
    }

    private Long id;
    private String guestEmail;
    private String description;
    private int rating;
    private Long date;
    private boolean reported;
    private String ownerEmail;
    private Long accommodationId;
    private boolean approved;

    protected Review(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        guestEmail = in.readString();
        description = in.readString();
        rating = in.readInt();
        if (in.readByte() == 0) {
            date = null;
        } else {
            date = in.readLong();
        }
        reported = in.readByte() != 0;
        ownerEmail = in.readString();
        if (in.readByte() == 0) {
            accommodationId = null;
        } else {
            accommodationId = in.readLong();
        }
        approved = in.readByte() != 0;
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

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
        dest.writeString(guestEmail);
        dest.writeString(description);
        dest.writeInt(rating);
        if (date == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(date);
        }
        dest.writeByte((byte) (reported ? 1 : 0));
        dest.writeString(ownerEmail);
        if (accommodationId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(accommodationId);
        }
        dest.writeByte((byte) (approved ? 1 : 0));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
