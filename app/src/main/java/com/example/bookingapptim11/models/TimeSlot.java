package com.example.bookingapptim11.models;

import java.time.LocalDate;
import java.util.List;

public class TimeSlot {
    private List<Integer> startDate;
    private List<Integer> endDate;

    public TimeSlot(List<Integer> startDate, List<Integer> endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public List<Integer> getStartDate() {
        return startDate;
    }

    public void setStartDate(List<Integer> startDate) {
        this.startDate = startDate;
    }

    public TimeSlot() {
    }

    public List<Integer> getEndDate() {
        return endDate;
    }

    public void setEndDate(List<Integer> endDate) {
        this.endDate = endDate;
    }
}
