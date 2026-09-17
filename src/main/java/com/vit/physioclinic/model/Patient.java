package com.vit.physioclinic.model;

import java.time.LocalDateTime;

public class Patient {
    private final String id;
    private final String fullName;
    private final int age;
    private final String phone;
    private final String medicalConcern;
    private final LocalDateTime registeredAt;

    public Patient(String id, String fullName, int age, String phone, String medicalConcern, LocalDateTime registeredAt) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.phone = phone;
        this.medicalConcern = medicalConcern;
        this.registeredAt = registeredAt;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String getMedicalConcern() { return medicalConcern; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
}
