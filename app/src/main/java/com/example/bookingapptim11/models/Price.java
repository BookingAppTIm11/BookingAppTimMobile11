package com.example.bookingapptim11.models;

import com.example.bookingapptim11.accommodationCreation.TimeSlot;

public class Price {
    private TimeSlot timeSlot;
    private Double price;

    public Price() {
    }

    public Price(TimeSlot timeSlot, Double price) {
        this.timeSlot = timeSlot;
        this.price = price;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
