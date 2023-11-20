
public class Amenity {
    private String name;
    private String price;
    private String place;

    public Amenity(String name, String price, String place) {
        this.name = name;
        this.price = price;
        this.place = place;
    }

    // Getters for properties
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPlace() {
        return place;
    }

    // Optional: Implement toString(), equals(), and hashCode()
    @Override
    public String toString() {
        return "Amenity{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", place='" + place + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amenity amenity = (Amenity) o;
        return name.equals(amenity.name) &&
                price.equals(amenity.price) &&
                place.equals(amenity.place);
    }
}
