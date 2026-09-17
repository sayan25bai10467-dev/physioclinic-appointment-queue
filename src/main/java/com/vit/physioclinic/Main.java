package com.vit.physioclinic;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path dataDirectory = Path.of("data");
        Path logDirectory = Path.of("logs");

        if (args.length >= 1) dataDirectory = Path.of(args[0]);
        if (args.length >= 2) logDirectory = Path.of(args[1]);

        try {
            new ClinicApplication(dataDirectory, logDirectory).run();
        } catch (RuntimeException e) {
            System.err.println("Application startup failed: " + e.getMessage());
            System.exit(1);
        }
    }
}
