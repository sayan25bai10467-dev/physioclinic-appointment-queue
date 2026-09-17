package com.vit.physioclinic.repository;

import com.vit.physioclinic.model.Patient;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientRepository {
    private final Path file;
    private final List<Patient> patients = new ArrayList<>();

    public PatientRepository(Path dataDirectory) {
        this.file = dataDirectory.resolve("patients.txt");
        load();
    }

    public synchronized void save(Patient patient) {
        patients.add(patient);
        persist();
    }

    public synchronized Optional<Patient> findById(String id) {
        return patients.stream().filter(p -> p.getId().equalsIgnoreCase(id)).findFirst();
    }

    public synchronized List<Patient> findByName(String name) {
        String needle = name.toLowerCase();
        return patients.stream().filter(p -> p.getFullName().toLowerCase().contains(needle)).toList();
    }

    public synchronized List<Patient> findAll() {
        return List.copyOf(patients);
    }

    private void load() {
        DataStore.ensureDirectory(file.getParent());
        for (String line : DataStore.readLines(file)) {
            if (line.isBlank() || line.startsWith("#")) continue;
            String[] parts = line.split("\\|", -1);
            if (parts.length != 6) continue;
            try {
                patients.add(new Patient(
                        parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], parts[4],
                        java.time.LocalDateTime.parse(parts[5])
                ));
            } catch (RuntimeException ignored) {
                // Ignore malformed records so one bad line does not prevent startup.
            }
        }
    }

    private void persist() {
        List<String> lines = new ArrayList<>();
        lines.add("# id|fullName|age|phone|medicalConcern|registeredAt");
        for (Patient p : patients) {
            lines.add(String.join("|",
                    DataStore.clean(p.getId()), DataStore.clean(p.getFullName()), String.valueOf(p.getAge()),
                    DataStore.clean(p.getPhone()), DataStore.clean(p.getMedicalConcern()), p.getRegisteredAt().toString()));
        }
        DataStore.writeLines(file, lines);
    }
}
