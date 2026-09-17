package com.vit.physioclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment implements Comparable<Appointment> {
    private final String id;
    private final String patientId;
    private final String therapistId;
    private final LocalDate date;
    private final LocalTime time;
    private final PriorityLevel priority;
    private AppointmentStatus status;
    private final String notes;

    public Appointment(String id, String patientId, String therapistId, LocalDate date, LocalTime time,
                       PriorityLevel priority, AppointmentStatus status, String notes) {
        this.id = id;
        this.patientId = patientId;
        this.therapistId = therapistId;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.status = status;
        this.notes = notes;
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public String getTherapistId() { return therapistId; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public PriorityLevel getPriority() { return priority; }
    public AppointmentStatus getStatus() { return status; }
    public String getNotes() { return notes; }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    @Override
    public int compareTo(Appointment other) {
        int priorityCompare = Integer.compare(this.priority.getRank(), other.priority.getRank());
        if (priorityCompare != 0) return priorityCompare;

        int dateCompare = this.date.compareTo(other.date);
        if (dateCompare != 0) return dateCompare;

        int timeCompare = this.time.compareTo(other.time);
        if (timeCompare != 0) return timeCompare;

        return this.id.compareTo(other.id);
    }
}
