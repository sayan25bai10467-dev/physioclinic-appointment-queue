package com.vit.physioclinic.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public final class InputValidator {
    private InputValidator() {}

    public static void requireText(String value, String fieldName, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        if (value.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " must be at most " + maxLength + " characters.");
        }
        if (value.contains("|")) {
            throw new IllegalArgumentException(fieldName + " cannot contain the | character.");
        }
    }

    public static void requireAge(int age) {
        if (age < 1 || age > 120) {
            throw new IllegalArgumentException("Age must be between 1 and 120.");
        }
    }

    public static void requirePhone(String phone) {
        if (phone == null || !phone.matches("[0-9+() -]{7,20}")) {
            throw new IllegalArgumentException("Phone number format is invalid.");
        }
    }

    public static LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date must use YYYY-MM-DD format.");
        }
    }

    public static LocalTime parseTime(String value) {
        try {
            return LocalTime.parse(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Time must use HH:MM format.");
        }
    }
}
