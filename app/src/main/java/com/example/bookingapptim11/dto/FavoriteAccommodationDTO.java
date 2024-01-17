package com.example.bookingapptim11.dto;

public class FavoriteAccommodationDTO {
    private Long accommodationId;
    private boolean favorite;

    public FavoriteAccommodationDTO(Long accommodationId, boolean favorite) {
        this.accommodationId = accommodationId;
        this.favorite = favorite;
    }

    public FavoriteAccommodationDTO() {
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
