package com.example.bookingapptim11.models;

public class ReservationForShowDTO {
    private long id;
    private String accommodation;
    private String guest;
    private String startDate;
    private String endDate;
    private int numberOfGuests;
    private ReservationStatus status;
    private Double price; // Use Double to represent nullable numeric values

    public ReservationForShowDTO(long id, String accommodation, String guest, String startDate, String endDate, int numberOfGuests, ReservationStatus status, Double price) {
        this.id = id;
        this.accommodation = accommodation;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.price = price;
    }

    public ReservationForShowDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

