package com.vit.physioclinic.repository;

import com.vit.physioclinic.model.Appointment;
import com.vit.physioclinic.model.AppointmentStatus;
import com.vit.physioclinic.model.PriorityLevel;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointmentRepository {
    private final Path file;
    private final List<Appointment> appointments = new ArrayList<>();

    public AppointmentRepository(Path dataDirectory) {
        this.file = dataDirectory.resolve("appointments.txt");
        load();
    }

    public synchronized void save(Appointment appointment) {
        appointments.add(appointment);
        persist();
    }

    public synchronized Optional<Appointment> findById(String id) {
        return appointments.stream().filter(a -> a.getId().equalsIgnoreCase(id)).findFirst();
    }

    public synchronized List<Appointment> findAll() {
        return List.copyOf(appointments);
    }

    public synchronized void persistChanges() {
        persist();
    }

    private void load() {
        DataStore.ensureDirectory(file.getParent());
        for (String line : DataStore.readLines(file)) {
            if (line.isBlank() || line.startsWith("#")) continue;
            String[] parts = line.split("\\|", -1);
            if (parts.length != 8) continue;
            try {
                appointments.add(new Appointment(
                        parts[0], parts[1], parts[2], java.time.LocalDate.parse(parts[3]),
                        java.time.LocalTime.parse(parts[4]), PriorityLevel.fromString(parts[5]),
                        AppointmentStatus.valueOf(parts[6]), parts[7]
                ));
            } catch (RuntimeException ignored) {
                // Ignore malformed records.
            }
        }
    }

    private void persist() {
        List<String> lines = new ArrayList<>();
        lines.add("# id|patientId|therapistId|date|time|priority|status|notes");
        for (Appointment a : appointments) {
            lines.add(String.join("|",
                    DataStore.clean(a.getId()), DataStore.clean(a.getPatientId()), DataStore.clean(a.getTherapistId()),
                    a.getDate().toString(), a.getTime().toString(), a.getPriority().name(), a.getStatus().name(),
                    DataStore.clean(a.getNotes())));
        }
        DataStore.writeLines(file, lines);
    }
}
