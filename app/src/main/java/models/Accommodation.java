package models;

import android.os.Parcel;
import android.os.Parcelable;

public class Accommodation implements Parcelable {
    String name;
    String location;
    Double rating;
    Double price;
    Integer capacity;

    public Accommodation() {
    }

    public Accommodation(String name, String location, Double rating, Double price, Integer capacity) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.price = price;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    protected Accommodation(Parcel in) {
        name = in.readString();
        location = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        if (in.readByte() == 0) {
            capacity = null;
        } else {
            capacity = in.readInt();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(location);
        if (rating != null) {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        } else {
            dest.writeByte((byte) 0);
        }
        if (price != null) {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        } else {
            dest.writeByte((byte) 0);
        }
        if (capacity != null) {
            dest.writeByte((byte) 1);
            dest.writeInt(capacity);
        } else {
            dest.writeByte((byte) 0);
        }
    }

    public static final Creator<Accommodation> CREATOR = new Creator<Accommodation>() {
        @Override
        public Accommodation createFromParcel(Parcel in) {
            return new Accommodation(in);
        }

        @Override
        public Accommodation[] newArray(int size) {
            return new Accommodation[size];
        }
    };
}
