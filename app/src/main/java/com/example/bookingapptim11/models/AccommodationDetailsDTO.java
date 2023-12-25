package com.example.bookingapptim11.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
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
    List<Integer> created;
    String type;
    PriceType priceType;
    AccommodationStatus status;

    public AccommodationDetailsDTO(Long id, String ownerEmail, String name, String description, String location, Double defaultPrice, List<String> photos, int minGuests, int maxGuests, List<Integer> created) {
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

    public AccommodationDetailsDTO(Long id, String ownerEmail, String name, String description, String location, Double defaultPrice, List<String> photos, int minGuests, int maxGuests, List<Integer> created, String type, PriceType priceType, AccommodationStatus status) {
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
        this.type = type;
        this.priceType = priceType;
        this.status = status;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public AccommodationStatus getStatus() {
        return status;
    }

    public void setStatus(AccommodationStatus status) {
        this.status = status;
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

    public List<Integer> getCreated() {
        return created;
    }

    public void setCreated(List<Integer> created) {
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
        created = new ArrayList<>();
        in.readList(created, Integer.class.getClassLoader());
        type = in.readString();
        priceType = PriceType.valueOf(in.readString());
        status = AccommodationStatus.valueOf(in.readString());
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
        dest.writeList(created);
        dest.writeString(type);
        dest.writeString(priceType.name());
        dest.writeString(status.name());
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

