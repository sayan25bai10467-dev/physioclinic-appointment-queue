package com.vit.physioclinic.tests;

import com.vit.physioclinic.model.*;
import com.vit.physioclinic.repository.AppointmentRepository;
import com.vit.physioclinic.repository.PatientRepository;
import com.vit.physioclinic.repository.TherapistRepository;
import com.vit.physioclinic.service.ClinicService;
import com.vit.physioclinic.util.IdGenerator;
import com.vit.physioclinic.util.LoggerUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

public class TestRunner {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) throws Exception {
        Path temp = Files.createTempDirectory("physio-clinic-test-");
        Path logs = temp.resolve("logs");
        ClinicService service = new ClinicService(
                new PatientRepository(temp),
                new TherapistRepository(temp),
                new AppointmentRepository(temp),
                new IdGenerator(1, 1, 1),
                LoggerUtil.createLogger(logs)
        );

        run("Patient registration", () -> {
            Patient p = service.registerPatient("Test Patient", 21, "9876543210", "Back pain");
            assertEquals("P001", p.getId(), "Patient ID should be generated");
            assertTrue(service.hasAnyPatients(), "Patient repository should contain data");
        });

        Therapist therapist = service.addTherapist("Test Therapist", "Sports Physiotherapy",
                LocalTime.of(9, 0), LocalTime.of(17, 0));
        Patient patient = service.listPatients().get(0);
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        run("Successful appointment booking", () -> {
            Appointment a = service.bookAppointment(patient.getId(), therapist.getId(), tomorrow,
                    LocalTime.of(10, 0), PriorityLevel.ROUTINE, "Routine review");
            assertEquals("A0001", a.getId(), "Appointment ID should be generated");
        });

        run("Double-booking prevention", () -> assertThrows(() -> service.bookAppointment(patient.getId(), therapist.getId(), tomorrow,
                LocalTime.of(10, 0), PriorityLevel.EMERGENCY, "Conflicting slot")));

        run("Priority queue ordering", () -> {
            service.bookAppointment(patient.getId(), therapist.getId(), tomorrow,
                    LocalTime.of(11, 0), PriorityLevel.EMERGENCY, "Emergency case");
            Appointment next = service.getQueue().get(0);
            assertEquals(PriorityLevel.EMERGENCY, next.getPriority(), "Emergency appointment should be first");
        });

        run("Call next and complete", () -> {
            Appointment next = service.callNextAppointment();
            assertEquals(AppointmentStatus.IN_PROGRESS, next.getStatus(), "Next appointment should become IN_PROGRESS");
            service.completeAppointment(next.getId());
            assertEquals(AppointmentStatus.COMPLETED, service.findAppointment(next.getId()).getStatus(),
                    "Appointment should become COMPLETED");
        });

        run("Working-hours validation", () -> assertThrows(() -> service.bookAppointment(patient.getId(), therapist.getId(), tomorrow,
                LocalTime.of(8, 0), PriorityLevel.ROUTINE, "Outside hours")));

        System.out.println("\n================ TEST RESULTS ================");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("==============================================");
        if (failed > 0) System.exit(1);
    }

    private static void run(String testName, Runnable test) {
        try {
            test.run();
            passed++;
            System.out.println("[PASS] " + testName);
        } catch (Throwable t) {
            failed++;
            System.out.println("[FAIL] " + testName + " -> " + t.getMessage());
        }
    }

    private static void assertTrue(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(message + " (expected=" + expected + ", actual=" + actual + ")");
        }
    }

    private static void assertThrows(Runnable action) {
        boolean thrown = false;
        try {
            action.run();
        } catch (RuntimeException e) {
            thrown = true;
        }
        if (!thrown) throw new AssertionError("Expected an exception but no exception was thrown.");
    }
}
