package com.vit.physioclinic.model;

public enum PriorityLevel {
    EMERGENCY(1),
    URGENT(2),
    ROUTINE(3);

    private final int rank;

    PriorityLevel(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public static PriorityLevel fromString(String value) {
        return value == null ? ROUTINE : PriorityLevel.valueOf(value.trim().toUpperCase());
    }
}
