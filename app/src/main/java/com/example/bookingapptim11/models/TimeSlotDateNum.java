package com.example.bookingapptim11.models;

import java.util.List;

public class TimeSlotDateNum {
    private Long startDate;
    private Long endDate;

    public TimeSlotDateNum(Long startDate, Long endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public TimeSlotDateNum() {
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
}
