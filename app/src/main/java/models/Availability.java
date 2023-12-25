package models;

public class Availability {
    private Long id;

    private TimeSlot timeSlot;

    public Availability() {
    }

    public Availability(Long id, TimeSlot timeSlot) {
        this.id = id;
        this.timeSlot = timeSlot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
}
