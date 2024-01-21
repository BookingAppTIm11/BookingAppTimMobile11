package com.example.bookingapptim11.dto;

import java.util.Collection;
import java.util.List;

public class AccommodationWithAmenitiesDTO {
    private Long id;
    private String ownerEmail;
    private String name;
    private String description;
    private String location;
    private Double defaultPrice;
    private List<String> photos;
    private int minGuests;
    private int maxGuests;
    private Long created;
    private String type;
    private Collection<AmenityOutputDTO> amenities;

    public AccommodationWithAmenitiesDTO(Long id, String ownerEmail, String name, String description,
                                                String location, Double defaultPrice, List<String> photos,
                                                int minGuests, int maxGuests, Long created, String type,
                                                Collection<AmenityOutputDTO> amenities) {
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
        this.amenities = amenities;
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<AmenityOutputDTO> getAmenities() {
        return amenities;
    }

    public void setAmenities(Collection<AmenityOutputDTO> amenities) {
        this.amenities = amenities;
    }
}
