package com.example.bookingapptim11.dto;

import java.util.List;

public class AccommodationYearlyProfitDTO {
    private String accommodationName;
    private List<Double> monthlyProfits;

    public AccommodationYearlyProfitDTO(String accommodationName, List<Double> monthlyProfits) {
        this.accommodationName = accommodationName;
        this.monthlyProfits = monthlyProfits;
    }

    public AccommodationYearlyProfitDTO() {
    }


    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public List<Double> getMonthlyProfits() {
        return monthlyProfits;
    }

    public void setMonthlyProfits(List<Double> monthlyProfits) {
        this.monthlyProfits = monthlyProfits;
    }
}
