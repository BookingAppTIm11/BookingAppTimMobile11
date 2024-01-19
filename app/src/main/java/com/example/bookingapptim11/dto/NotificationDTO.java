package com.example.bookingapptim11.dto;

public class NotificationDTO {
    private Long id;
    private String receiverEmail;
    private NotificationType type;
    private String message;


    public NotificationDTO(Long id, String receiverEmail, NotificationType type, String message) {
        this.id = id;
        this.receiverEmail = receiverEmail;
        this.type = type;
        this.message = message;
    }

    public NotificationDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
