package com.vit.physioclinic.model;

import java.time.LocalTime;

public class Therapist {
    private final String id;
    private final String name;
    private final String specialization;
    private final LocalTime workingStart;
    private final LocalTime workingEnd;

    public Therapist(String id, String name, String specialization, LocalTime workingStart, LocalTime workingEnd) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.workingStart = workingStart;
        this.workingEnd = workingEnd;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public LocalTime getWorkingStart() { return workingStart; }
    public LocalTime getWorkingEnd() { return workingEnd; }
}
