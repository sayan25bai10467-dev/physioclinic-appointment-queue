package com.vit.physioclinic.repository;

import com.vit.physioclinic.model.Therapist;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapistRepository {
    private final Path file;
    private final List<Therapist> therapists = new ArrayList<>();

    public TherapistRepository(Path dataDirectory) {
        this.file = dataDirectory.resolve("therapists.txt");
        load();
    }

    public synchronized void save(Therapist therapist) {
        therapists.add(therapist);
        persist();
    }

    public synchronized Optional<Therapist> findById(String id) {
        return therapists.stream().filter(t -> t.getId().equalsIgnoreCase(id)).findFirst();
    }

    public synchronized List<Therapist> findAll() {
        return List.copyOf(therapists);
    }

    private void load() {
        DataStore.ensureDirectory(file.getParent());
        for (String line : DataStore.readLines(file)) {
            if (line.isBlank() || line.startsWith("#")) continue;
            String[] parts = line.split("\\|", -1);
            if (parts.length != 5) continue;
            try {
                therapists.add(new Therapist(
                        parts[0], parts[1], parts[2],
                        java.time.LocalTime.parse(parts[3]), java.time.LocalTime.parse(parts[4])
                ));
            } catch (RuntimeException ignored) {
                // Ignore malformed records.
            }
        }
    }

    private void persist() {
        List<String> lines = new ArrayList<>();
        lines.add("# id|name|specialization|workingStart|workingEnd");
        for (Therapist t : therapists) {
            lines.add(String.join("|",
                    DataStore.clean(t.getId()), DataStore.clean(t.getName()), DataStore.clean(t.getSpecialization()),
                    t.getWorkingStart().toString(), t.getWorkingEnd().toString()));
        }
        DataStore.writeLines(file, lines);
    }
}
