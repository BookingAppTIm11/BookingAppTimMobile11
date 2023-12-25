package com.example.bookingapptim11.models;

import java.util.List;

public class TimeSlotDateNum {
    private List<Integer> startDate;
    private List<Integer> endDate;

    public TimeSlotDateNum(List<Integer> startDate, List<Integer> endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public List<Integer> getStartDate() {
        return startDate;
    }

    public void setStartDate(List<Integer> startDate) {
        this.startDate = startDate;
    }

    public TimeSlotDateNum() {
    }

    public List<Integer> getEndDate() {
        return endDate;
    }

    public void setEndDate(List<Integer> endDate) {
        this.endDate = endDate;
    }
}
