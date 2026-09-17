package com.vit.physioclinic.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private final AtomicInteger patientSequence;
    private final AtomicInteger therapistSequence;
    private final AtomicInteger appointmentSequence;

    public IdGenerator(int initialPatient, int initialTherapist, int initialAppointment) {
        this.patientSequence = new AtomicInteger(initialPatient);
        this.therapistSequence = new AtomicInteger(initialTherapist);
        this.appointmentSequence = new AtomicInteger(initialAppointment);
    }

    public String nextPatientId() { return String.format("P%03d", patientSequence.getAndIncrement()); }
    public String nextTherapistId() { return String.format("T%03d", therapistSequence.getAndIncrement()); }
    public String nextAppointmentId() { return String.format("A%04d", appointmentSequence.getAndIncrement()); }
}
