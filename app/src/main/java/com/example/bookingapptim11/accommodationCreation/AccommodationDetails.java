package com.example.bookingapptim11.accommodationCreation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingapptim11.models.AccommodationStatus;
import com.example.bookingapptim11.models.PriceType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;

public class AccommodationDetails implements Parcelable {

    @SerializedName("id")
    @Expose
    Long id;
    @SerializedName("ownerEmail")
    @Expose
    String ownerEmail;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("location")
    @Expose
    String location;
    @SerializedName("defaultPrice")
    @Expose
    Double defaultPrice;
    @SerializedName("photos")
    @Expose
    List<String> photos;
    @SerializedName("minGuests")
    @Expose
    int minGuests;
    @SerializedName("maxGuests")
    @Expose
    int maxGuests;
    @SerializedName("created")
    @Expose
    LocalDate created;
    @SerializedName("type")
    @Expose
    String type;
    @SerializedName("priceType")
    @Expose
    PriceType priceType;
    @SerializedName("status")
    @Expose
    AccommodationStatus status;

    public AccommodationDetails() {
    }

    public AccommodationDetails(Long id, String ownerEmail, String name, String description, String location, Double defaultPrice, List<String> photos, int minGuests, int maxGuests, LocalDate created, String type, PriceType priceType, AccommodationStatus status) {
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

    protected AccommodationDetails(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        ownerEmail = in.readString();
        name = in.readString();
        description = in.readString();
        location = in.readString();
        if (in.readByte() == 0) {
            defaultPrice = null;
        } else {
            defaultPrice = in.readDouble();
        }
        photos = in.createStringArrayList();
        minGuests = in.readInt();
        maxGuests = in.readInt();
        type = in.readString();
    }

    public static final Creator<AccommodationDetails> CREATOR = new Creator<AccommodationDetails>() {
        @Override
        public AccommodationDetails createFromParcel(Parcel in) {
            return new AccommodationDetails(in);
        }

        @Override
        public AccommodationDetails[] newArray(int size) {
            return new AccommodationDetails[size];
        }
    };

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
        dest.writeString(ownerEmail);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(location);
        if (defaultPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(defaultPrice);
        }
        dest.writeStringList(photos);
        dest.writeInt(minGuests);
        dest.writeInt(maxGuests);
        dest.writeString(type);
    }
}
