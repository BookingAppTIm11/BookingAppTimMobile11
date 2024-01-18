package com.example.bookingapptim11.dto;

import java.util.List;

public class AccommodationYearlyNumberOfReservations {
    private String accommodationName;
    private List<Integer> monthlyNumberOfReservations;

    public AccommodationYearlyNumberOfReservations() {
    }

    public AccommodationYearlyNumberOfReservations(String accommodationName, List<Integer> monthlyNumberOfReservations) {
        this.accommodationName = accommodationName;
        this.monthlyNumberOfReservations = monthlyNumberOfReservations;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public List<Integer> getMonthlyNumberOfReservations() {
        return monthlyNumberOfReservations;
    }

    public void setMonthlyNumberOfReservations(List<Integer> monthlyNumberOfReservations) {
        this.monthlyNumberOfReservations = monthlyNumberOfReservations;
    }
}
