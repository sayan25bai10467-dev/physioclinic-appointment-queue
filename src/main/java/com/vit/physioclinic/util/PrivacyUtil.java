package com.vit.physioclinic.util;

public final class PrivacyUtil {
    private PrivacyUtil() {}

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return "****";
        String digits = phone.replaceAll("\\D", "");
        if (digits.length() < 4) return "****";
        return "******" + digits.substring(digits.length() - 4);
    }

    public static String initials(String fullName) {
        if (fullName == null || fullName.isBlank()) return "NA";
        String[] parts = fullName.trim().split("\\s+");
        if (parts.length == 1) return parts[0].substring(0, 1).toUpperCase();
        return (parts[0].charAt(0) + "" + parts[parts.length - 1].charAt(0)).toUpperCase();
    }
}
