package com.vit.physioclinic.ui;

import com.vit.physioclinic.model.*;
import com.vit.physioclinic.service.ClinicService;
import com.vit.physioclinic.util.InputValidator;
import com.vit.physioclinic.util.PrivacyUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final ClinicService service;
    private final Scanner scanner;

    public ConsoleUI(ClinicService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void start() {
        printBanner();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> registerPatient();
                    case "2" -> listPatients();
                    case "3" -> searchPatients();
                    case "4" -> addTherapist();
                    case "5" -> listTherapists();
                    case "6" -> bookAppointment();
                    case "7" -> viewQueue();
                    case "8" -> callNext();
                    case "9" -> completeAppointment();
                    case "10" -> cancelAppointment();
                    case "11" -> dailyReport();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid menu option.");
                }
            } catch (RuntimeException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
        System.out.println("Goodbye. Clinic data has been saved.");
    }

    private void printBanner() {
        System.out.println("============================================================");
        System.out.println("          PHYSIOCLINIC APPOINTMENT QUEUE SYSTEM");
        System.out.println("============================================================");
        System.out.println("Command-line clinic scheduling and priority queue demo.");
    }

    private void printMenu() {
        System.out.println("\n--------------------- MAIN MENU ----------------------------");
        System.out.println("1. Register patient");
        System.out.println("2. List patients");
        System.out.println("3. Search patients");
        System.out.println("4. Add therapist");
        System.out.println("5. List therapists");
        System.out.println("6. Book appointment");
        System.out.println("7. View appointment priority queue");
        System.out.println("8. Call next appointment");
        System.out.println("9. Complete appointment");
        System.out.println("10. Cancel appointment");
        System.out.println("11. Daily report");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private void registerPatient() {
        System.out.println("\n--- Patient Registration ---");
        String name = prompt("Full name: ");
        int age = Integer.parseInt(prompt("Age: "));
        String phone = prompt("Phone: ");
        String concern = prompt("Medical concern / reason for visit: ");
        Patient patient = service.registerPatient(name, age, phone, concern);
        System.out.println("Patient registered successfully. ID: " + patient.getId());
    }

    private void listPatients() {
        List<Patient> patients = service.listPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        System.out.printf("%-8s %-24s %-5s %-16s %-30s%n", "ID", "Name", "Age", "Phone", "Concern");
        System.out.println("-".repeat(90));
        for (Patient p : patients) {
            System.out.printf("%-8s %-24s %-5d %-16s %-30s%n", p.getId(), p.getFullName(), p.getAge(),
                    PrivacyUtil.maskPhone(p.getPhone()), truncate(p.getMedicalConcern(), 30));
        }
    }

    private void searchPatients() {
        String query = prompt("Name search: ");
        List<Patient> patients = service.searchPatients(query);
        if (patients.isEmpty()) {
            System.out.println("No matching patients found.");
            return;
        }
        for (Patient p : patients) {
            System.out.printf("%s | %s | %d years | %s | %s%n", p.getId(), p.getFullName(), p.getAge(),
                    PrivacyUtil.maskPhone(p.getPhone()), p.getMedicalConcern());
        }
    }

    private void addTherapist() {
        System.out.println("\n--- Therapist Scheduling Setup ---");
        String name = prompt("Therapist name: ");
        String specialization = prompt("Specialization: ");
        LocalTime start = InputValidator.parseTime(prompt("Working start (HH:MM): "));
        LocalTime end = InputValidator.parseTime(prompt("Working end (HH:MM): "));
        Therapist therapist = service.addTherapist(name, specialization, start, end);
        System.out.println("Therapist added. ID: " + therapist.getId());
    }

    private void listTherapists() {
        List<Therapist> therapists = service.listTherapists();
        if (therapists.isEmpty()) {
            System.out.println("No therapists found. Add one first.");
            return;
        }
        System.out.printf("%-8s %-22s %-28s %-8s %-8s%n", "ID", "Name", "Specialization", "Start", "End");
        System.out.println("-".repeat(80));
        for (Therapist t : therapists) {
            System.out.printf("%-8s %-22s %-28s %-8s %-8s%n", t.getId(), t.getName(), t.getSpecialization(),
                    t.getWorkingStart(), t.getWorkingEnd());
        }
    }

    private void bookAppointment() {
        System.out.println("\n--- Book Appointment ---");
        String patientId = prompt("Patient ID: ");
        String therapistId = prompt("Therapist ID: ");
        LocalDate date = InputValidator.parseDate(prompt("Date (YYYY-MM-DD): "));
        LocalTime time = InputValidator.parseTime(prompt("Time (HH:MM): "));
        PriorityLevel priority = parsePriority(prompt("Priority (1=EMERGENCY, 2=URGENT, 3=ROUTINE): "));
        String notes = prompt("Notes: ");
        Appointment appointment = service.bookAppointment(patientId, therapistId, date, time, priority, notes);
        System.out.println("Appointment booked. ID: " + appointment.getId());
        System.out.println("Status: " + appointment.getStatus() + " | Priority: " + appointment.getPriority());
    }

    private void viewQueue() {
        List<Appointment> queue = service.getQueue();
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        System.out.printf("%-9s %-10s %-11s %-12s %-9s %-12s%n", "Position", "Appt ID", "Patient", "Therapist", "Priority", "Date/Time");
        System.out.println("-".repeat(85));
        int position = 1;
        for (Appointment a : queue) {
            Patient p = service.getPatient(a.getPatientId());
            Therapist t = service.getTherapist(a.getTherapistId());
            System.out.printf("%-9d %-10s %-11s %-12s %-9s %-12s%n", position++, a.getId(),
                    PrivacyUtil.initials(p.getFullName()), t.getId(), a.getPriority(), a.getDate() + " " + a.getTime());
        }
    }

    private void callNext() {
        Appointment appointment = service.callNextAppointment();
        Patient p = service.getPatient(appointment.getPatientId());
        Therapist t = service.getTherapist(appointment.getTherapistId());
        System.out.println("Next appointment called successfully.");
        System.out.println("Appointment: " + appointment.getId());
        System.out.println("Patient: " + PrivacyUtil.initials(p.getFullName()));
        System.out.println("Therapist: " + t.getName());
        System.out.println("Priority: " + appointment.getPriority());
    }

    private void completeAppointment() {
        String id = prompt("Appointment ID to complete: ");
        service.completeAppointment(id);
        System.out.println("Appointment marked COMPLETED.");
    }

    private void cancelAppointment() {
        String id = prompt("Appointment ID to cancel: ");
        service.cancelAppointment(id);
        System.out.println("Appointment marked CANCELLED.");
    }

    private void dailyReport() {
        LocalDate date = InputValidator.parseDate(prompt("Report date (YYYY-MM-DD): "));
        System.out.println("\n--- Daily Report ---");
        System.out.println(service.dailySummary(date));
    }

    private PriorityLevel parsePriority(String value) {
        return switch (value.trim()) {
            case "1" -> PriorityLevel.EMERGENCY;
            case "2" -> PriorityLevel.URGENT;
            case "3" -> PriorityLevel.ROUTINE;
            default -> throw new IllegalArgumentException("Priority must be 1, 2, or 3.");
        };
    }

    private String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private String truncate(String value, int max) {
        return value.length() <= max ? value : value.substring(0, max - 3) + "...";
    }
}
