package com.example.bookingapptim11.dto;

public class NumberOfCancellationsDTO {
    private String guestId;
    private Integer numberOfCancellations;

    public NumberOfCancellationsDTO(String guestId, Integer numberOfCancellations) {
        this.guestId = guestId;
        this.numberOfCancellations = numberOfCancellations;
    }

    public NumberOfCancellationsDTO() {
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public Integer getNumberOfCancellations() {
        return numberOfCancellations;
    }

    public void setNumberOfCancellations(Integer numberOfCancellations) {
        this.numberOfCancellations = numberOfCancellations;
    }
}
