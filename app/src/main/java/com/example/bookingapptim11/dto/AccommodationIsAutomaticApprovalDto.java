package com.example.bookingapptim11.dto;

public class AccommodationIsAutomaticApprovalDto {
    private long id;
    private boolean automaticApproval;

    // Empty constructor
    public AccommodationIsAutomaticApprovalDto() {
    }

    // Full constructor
    public AccommodationIsAutomaticApprovalDto(long id, boolean automaticApproval) {
        this.id = id;
        this.automaticApproval = automaticApproval;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAutomaticApproval() {
        return automaticApproval;
    }

    public void setAutomaticApproval(boolean automaticApproval) {
        this.automaticApproval = automaticApproval;
    }
}