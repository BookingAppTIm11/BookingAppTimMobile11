package com.example.bookingapptim11.dto;

public class AccommodationNumberOfReservations {
    private String accommodationName;
    private Integer numberOfReservations;

    public AccommodationNumberOfReservations(String accommodationName, Integer numberOfReservations) {
        this.accommodationName = accommodationName;
        this.numberOfReservations = numberOfReservations;
    }

    public AccommodationNumberOfReservations() {
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public Integer getNumberOfReservations() {
        return numberOfReservations;
    }

    public void setNumberOfReservations(Integer numberOfReservations) {
        this.numberOfReservations = numberOfReservations;
    }
}
