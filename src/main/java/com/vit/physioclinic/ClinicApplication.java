package com.vit.physioclinic;

import com.vit.physioclinic.repository.AppointmentRepository;
import com.vit.physioclinic.repository.PatientRepository;
import com.vit.physioclinic.repository.TherapistRepository;
import com.vit.physioclinic.service.ClinicService;
import com.vit.physioclinic.ui.ConsoleUI;
import com.vit.physioclinic.util.IdGenerator;
import com.vit.physioclinic.util.LoggerUtil;

import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClinicApplication {
    private final ClinicService service;

    public ClinicApplication(Path dataDirectory, Path logDirectory) {
        PatientRepository patientRepository = new PatientRepository(dataDirectory);
        TherapistRepository therapistRepository = new TherapistRepository(dataDirectory);
        AppointmentRepository appointmentRepository = new AppointmentRepository(dataDirectory);

        IdGenerator idGenerator = new IdGenerator(
                nextSequence(patientRepository.findAll().stream().map(p -> p.getId())),
                nextSequence(therapistRepository.findAll().stream().map(t -> t.getId())),
                nextSequence(appointmentRepository.findAll().stream().map(a -> a.getId()))
        );
        Logger logger = LoggerUtil.createLogger(logDirectory);
        this.service = new ClinicService(patientRepository, therapistRepository, appointmentRepository, idGenerator, logger);
    }

    public void run() {
        new ConsoleUI(service, new Scanner(System.in)).start();
    }

    private static int nextSequence(java.util.stream.Stream<String> ids) {
        return ids.mapToInt(ClinicApplication::numericPart).max().orElse(0) + 1;
    }

    private static int numericPart(String id) {
        try {
            return Integer.parseInt(id.replaceAll("\\D", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
