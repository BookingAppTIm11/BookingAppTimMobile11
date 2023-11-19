package models;

public class Accommodation {
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


}
