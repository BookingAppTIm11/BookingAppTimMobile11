package com.example.bookingapptim11.models;

public class ReservationDTO {
    private long id;
    private long accommodation;
    private String guest;
    private Long startDate;
    private Long endDate;
    private int numberOfGuests;

    public ReservationDTO(long id, long accommodation, String guest, Long startDate, Long endDate, int numberOfGuests) {
        this.id = id;
        this.accommodation = accommodation;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
    }

    public ReservationDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(long accommodation) {
        this.accommodation = accommodation;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}

