package com.vit.physioclinic.service;

import com.vit.physioclinic.model.*;
import com.vit.physioclinic.repository.AppointmentRepository;
import com.vit.physioclinic.repository.PatientRepository;
import com.vit.physioclinic.repository.TherapistRepository;
import com.vit.physioclinic.util.IdGenerator;
import com.vit.physioclinic.util.InputValidator;
import com.vit.physioclinic.util.PrivacyUtil;

import java.time.*;
import java.util.*;
import java.util.logging.Logger;

public class ClinicService {
    private final PatientRepository patientRepository;
    private final TherapistRepository therapistRepository;
    private final AppointmentRepository appointmentRepository;
    private final IdGenerator idGenerator;
    private final Logger logger;

    public ClinicService(PatientRepository patientRepository,
                         TherapistRepository therapistRepository,
                         AppointmentRepository appointmentRepository,
                         IdGenerator idGenerator,
                         Logger logger) {
        this.patientRepository = patientRepository;
        this.therapistRepository = therapistRepository;
        this.appointmentRepository = appointmentRepository;
        this.idGenerator = idGenerator;
        this.logger = logger;
    }

    public Patient registerPatient(String fullName, int age, String phone, String medicalConcern) {
        InputValidator.requireText(fullName, "Patient name", 80);
        InputValidator.requireAge(age);
        InputValidator.requirePhone(phone);
        InputValidator.requireText(medicalConcern, "Medical concern", 200);

        Patient patient = new Patient(idGenerator.nextPatientId(), fullName.trim(), age, phone.trim(),
                medicalConcern.trim(), LocalDateTime.now());
        patientRepository.save(patient);
        logger.info("Patient registered: id=" + patient.getId() + ", initials=" + PrivacyUtil.initials(patient.getFullName()));
        return patient;
    }

    public Therapist addTherapist(String name, String specialization, LocalTime start, LocalTime end) {
        InputValidator.requireText(name, "Therapist name", 80);
        InputValidator.requireText(specialization, "Specialization", 100);
        if (!start.isBefore(end)) throw new IllegalArgumentException("Working start time must be before end time.");

        Therapist therapist = new Therapist(idGenerator.nextTherapistId(), name.trim(), specialization.trim(), start, end);
        therapistRepository.save(therapist);
        logger.info("Therapist added: id=" + therapist.getId());
        return therapist;
    }

    public List<Patient> listPatients() {
        return patientRepository.findAll();
    }

    public List<Patient> searchPatients(String name) {
        InputValidator.requireText(name, "Search text", 80);
        return patientRepository.findByName(name);
    }

    public List<Therapist> listTherapists() {
        return therapistRepository.findAll();
    }

    public Appointment bookAppointment(String patientId, String therapistId, LocalDate date,
                                       LocalTime time, PriorityLevel priority, String notes) {
        if (date.atTime(time).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment date/time cannot be in the past.");
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));
        Therapist therapist = therapistRepository.findById(therapistId)
                .orElseThrow(() -> new IllegalArgumentException("Therapist not found: " + therapistId));

        if (time.isBefore(therapist.getWorkingStart()) || !time.isBefore(therapist.getWorkingEnd())) {
            throw new IllegalArgumentException("Selected time is outside the therapist's working hours.");
        }

        boolean therapistConflict = appointmentRepository.findAll().stream().anyMatch(a ->
                a.getTherapistId().equalsIgnoreCase(therapist.getId()) &&
                a.getDate().equals(date) && a.getTime().equals(time) &&
                a.getStatus() != AppointmentStatus.CANCELLED);
        if (therapistConflict) {
            throw new IllegalArgumentException("Double-booking prevented: therapist already has this time slot.");
        }

        boolean patientConflict = appointmentRepository.findAll().stream().anyMatch(a ->
                a.getPatientId().equalsIgnoreCase(patient.getId()) &&
                a.getDate().equals(date) && a.getTime().equals(time) &&
                a.getStatus() != AppointmentStatus.CANCELLED);
        if (patientConflict) {
            throw new IllegalArgumentException("Patient already has an appointment at this time.");
        }

        InputValidator.requireText(notes == null || notes.isBlank() ? "No additional notes" : notes, "Notes", 200);
        Appointment appointment = new Appointment(idGenerator.nextAppointmentId(), patient.getId(), therapist.getId(),
                date, time, priority == null ? PriorityLevel.ROUTINE : priority,
                AppointmentStatus.WAITING, notes == null || notes.isBlank() ? "No additional notes" : notes.trim());
        appointmentRepository.save(appointment);
        logger.info("Appointment booked: id=" + appointment.getId() + ", priority=" + appointment.getPriority());
        return appointment;
    }

    public List<Appointment> getQueue() {
        PriorityQueue<Appointment> queue = buildWaitingQueue();
        List<Appointment> sorted = new ArrayList<>();
        while (!queue.isEmpty()) sorted.add(queue.poll());
        return sorted;
    }

    public Appointment callNextAppointment() {
        PriorityQueue<Appointment> queue = buildWaitingQueue();
        if (queue.isEmpty()) throw new IllegalStateException("No waiting appointments in the queue.");
        Appointment next = queue.poll();
        next.setStatus(AppointmentStatus.IN_PROGRESS);
        appointmentRepository.persistChanges();
        logger.info("Appointment called: id=" + next.getId() + ", priority=" + next.getPriority());
        return next;
    }

    public void completeAppointment(String appointmentId) {
        Appointment appointment = findAppointment(appointmentId);
        if (appointment.getStatus() != AppointmentStatus.IN_PROGRESS) {
            throw new IllegalStateException("Only an in-progress appointment can be completed.");
        }
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.persistChanges();
        logger.info("Appointment completed: id=" + appointment.getId());
    }

    public void cancelAppointment(String appointmentId) {
        Appointment appointment = findAppointment(appointmentId);
        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Completed appointments cannot be cancelled.");
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.persistChanges();
        logger.info("Appointment cancelled: id=" + appointment.getId());
    }

    public Appointment findAppointment(String appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found: " + appointmentId));
    }

    public List<Appointment> appointmentsForDate(LocalDate date) {
        return appointmentRepository.findAll().stream()
                .filter(a -> a.getDate().equals(date))
                .sorted(Comparator.comparing(Appointment::getTime).thenComparing(Appointment::getId))
                .toList();
    }

    public String dailySummary(LocalDate date) {
        List<Appointment> list = appointmentsForDate(date);
        long completed = list.stream().filter(a -> a.getStatus() == AppointmentStatus.COMPLETED).count();
        long waiting = list.stream().filter(a -> a.getStatus() == AppointmentStatus.WAITING).count();
        long inProgress = list.stream().filter(a -> a.getStatus() == AppointmentStatus.IN_PROGRESS).count();
        long cancelled = list.stream().filter(a -> a.getStatus() == AppointmentStatus.CANCELLED).count();
        return String.format("Date: %s%nTotal: %d%nWaiting: %d%nIn Progress: %d%nCompleted: %d%nCancelled: %d",
                date, list.size(), waiting, inProgress, completed, cancelled);
    }

    public Patient getPatient(String id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + id));
    }

    public Therapist getTherapist(String id) {
        return therapistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Therapist not found: " + id));
    }

    public boolean hasAnyPatients() { return !patientRepository.findAll().isEmpty(); }
    public boolean hasAnyTherapists() { return !therapistRepository.findAll().isEmpty(); }

    private PriorityQueue<Appointment> buildWaitingQueue() {
        PriorityQueue<Appointment> queue = new PriorityQueue<>();
        appointmentRepository.findAll().stream()
                .filter(a -> a.getStatus() == AppointmentStatus.WAITING)
                .forEach(queue::offer);
        return queue;
    }
}
