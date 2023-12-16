package com.example.bookingapptim11.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.List;

public class AccommodationDetailsDTO implements Parcelable {
        Long id;
        String ownerEmail;
        String name;
        String description;
        String location;
        Double defaultPrice;
        List<String> photos;
        int minGuests;
        int maxGuests;
        LocalDate created;

    public AccommodationDetailsDTO(Long id, String ownerEmail, String name, String description, String location, Double defaultPrice, List<String> photos, int minGuests, int maxGuests, LocalDate created) {
        this.id = id;
        this.ownerEmail = ownerEmail;
        this.name = name;
        this.description = description;
        this.location = location;
        this.defaultPrice = defaultPrice;
        this.photos = photos;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.created = created;
    }

    public AccommodationDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
    protected AccommodationDetailsDTO(Parcel in) {
        id = in.readLong();
        ownerEmail = in.readString();
        name = in.readString();
        description = in.readString();
        location = in.readString();
        defaultPrice = in.readDouble();
        photos = in.createStringArrayList();
        minGuests = in.readInt();
        maxGuests = in.readInt();
        created = (LocalDate) in.readSerializable();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(ownerEmail);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeDouble(defaultPrice);
        dest.writeStringList(photos);
        dest.writeInt(minGuests);
        dest.writeInt(maxGuests);
        dest.writeSerializable(created);

    }

    public static final Creator<AccommodationDetailsDTO> CREATOR = new Creator<AccommodationDetailsDTO>() {
        @Override
        public AccommodationDetailsDTO createFromParcel(Parcel in) {
            return new AccommodationDetailsDTO(in);
        }

        @Override
        public AccommodationDetailsDTO[] newArray(int size) {
            return new AccommodationDetailsDTO[size];
        }
    };
}
