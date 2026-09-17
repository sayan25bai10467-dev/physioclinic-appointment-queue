package com.vit.physioclinic.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class DataStore {
    private DataStore() {}

    public static void ensureDirectory(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create data directory: " + directory, e);
        }
    }

    public static List<String> readLines(Path file) {
        try {
            if (!Files.exists(file)) return new ArrayList<>();
            return Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read " + file, e);
        }
    }

    public static void writeLines(Path file, List<String> lines) {
        try {
            Files.createDirectories(file.getParent());
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write " + file, e);
        }
    }

    public static String clean(String value) {
        if (value == null) return "";
        return value.replace("|", "/").replace("\n", " ").replace("\r", " ").trim();
    }
}
