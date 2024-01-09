package com.example.bookingapptim11.models;

import com.example.bookingapptim11.accommodationCreation.TimeSlot;

public class AvailabilityDateNum {
    private Long id;

    private TimeSlotDateNum timeSlot;

    public AvailabilityDateNum() {
    }

    public AvailabilityDateNum(Long id, TimeSlotDateNum timeSlot) {
        this.id = id;
        this.timeSlot = timeSlot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSlotDateNum getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlotDateNum timeSlot) {
        this.timeSlot = timeSlot;
    }
}
