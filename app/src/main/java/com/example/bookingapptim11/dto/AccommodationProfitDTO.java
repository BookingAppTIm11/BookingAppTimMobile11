package com.example.bookingapptim11.dto;

public class AccommodationProfitDTO {
    private String accommodationName;
    private Double profit;

    public AccommodationProfitDTO(String accommodationName, Double profit) {
        this.accommodationName = accommodationName;
        this.profit = profit;
    }

    public AccommodationProfitDTO() {
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}
