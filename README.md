# PhysioClinic Appointment Queue System

A command-line Java application for managing physiotherapy clinic patients, therapist schedules, appointments, and a priority-based appointment queue.

## 1. Project Overview

PhysioClinic is an educational clinic scheduling system designed for the Programming in Java course. It demonstrates object-oriented programming, Java collections, file-based persistence, validation, exception handling, modular design, logging, and a `PriorityQueue` for emergency/urgent/routine appointment handling.

The system is intentionally dependency-free: it uses only the Java Standard Library, so an evaluator can clone the repository and run it from a terminal without an IDE or GUI.

## 2. Problem Being Solved

Small physiotherapy clinics may need a simple way to register patients, maintain therapist working hours, prevent double-booking, and serve urgent cases before routine appointments. The project models those tasks in a structured command-line application.

## 3. Major Functional Modules

1. **Patient Registration & Search**
   - Register patient details.
   - Persist patient records to local storage.
   - Search patients by name.
   - Mask phone numbers when displayed.

2. **Therapist Scheduling**
   - Add therapists and their specializations.
   - Store daily working hours.
   - Reject appointment times outside working hours.

3. **Appointment Booking & Validation**
   - Book appointments for a patient and therapist.
   - Prevent therapist double-booking.
   - Prevent the same patient from having a conflicting slot.
   - Validate date/time and required fields.

4. **Priority Appointment Queue**
   - Uses Java `PriorityQueue`.
   - Priority order: `EMERGENCY` -> `URGENT` -> `ROUTINE`.
   - Uses date/time as tie-breakers.
   - Call the next appointment and move it to `IN_PROGRESS`.

5. **Appointment Lifecycle & Reports**
   - Complete or cancel appointments.
   - Produce daily appointment status summaries.

## 4. Technology Stack

- Java 17+
- Java Standard Library only
- `PriorityQueue`, `List`, `Optional`, `Stream`, `LocalDate`, `LocalTime`, `LocalDateTime`
- Text-file persistence using UTF-8
- `java.util.logging` for audit/application logs
- Git/GitHub for version control

## 5. Repository Structure

```text
PhysioClinicQueueSystem/
├── src/main/java/com/vit/physioclinic/
│   ├── Main.java
│   ├── ClinicApplication.java
│   ├── model/
│   │   ├── Appointment.java
│   │   ├── AppointmentStatus.java
│   │   ├── Patient.java
│   │   ├── PriorityLevel.java
│   │   └── Therapist.java
│   ├── repository/
│   │   ├── AppointmentRepository.java
│   │   ├── DataStore.java
│   │   ├── PatientRepository.java
│   │   └── TherapistRepository.java
│   ├── service/
│   │   └── ClinicService.java
│   ├── ui/
│   │   └── ConsoleUI.java
│   ├── util/
│   │   ├── IdGenerator.java
│   │   ├── InputValidator.java
│   │   ├── LoggerUtil.java
│   │   └── PrivacyUtil.java
│   └── tests/
│       └── TestRunner.java
├── data/
│   └── .gitkeep
├── logs/
│   └── .gitkeep
├── docs/
│   ├── architecture.md
│   ├── design-decisions.md
│   ├── diagrams.md
│   ├── requirements.md
│   ├── testing.md
│   └── user-guide.md
├── diagrams/
│   ├── architecture.dot
│   ├── class-diagram.dot
│   ├── sequence-diagram.dot
│   └── use-case.dot
├── README.md
├── statement.md
├── sources.txt
├── run.sh
├── run.bat
├── test.sh
├── test.bat
└── .gitignore
```

## 6. Prerequisites

Install a JDK that supports Java 17 or later.

Check:

```bash
java -version
javac -version
```

## 7. Run the Project from the Command Line

### macOS / Linux

```bash
git clone https://github.com/YOUR-USERNAME/physioclinic-appointment-queue.git
cd physioclinic-appointment-queue
bash run.sh
```

### Windows CMD

```bat
git clone https://github.com/YOUR-USERNAME/physioclinic-appointment-queue.git
cd physioclinic-appointment-queue
run.bat
```

### Direct Java commands

The project can also be compiled without the helper scripts:

```bash
rm -rf out
mkdir out
javac --release 17 -d out @sources.txt
java -cp out com.vit.physioclinic.Main
```

On Windows, create an `out` directory and run:

```bat
javac --release 17 -d out @sources.txt
java -cp out com.vit.physioclinic.Main
```

## 8. Run Tests

### macOS / Linux

```bash
bash test.sh
```

### Windows CMD

```bat
test.bat
```

### Direct test execution

```bash
rm -rf out
mkdir out
javac --release 17 -d out @sources.txt
java -cp out com.vit.physioclinic.tests.TestRunner
```

Expected result:

```text
Passed: 6
Failed: 0
```

## 9. First-Time Usage

1. Start the application.
2. Add at least one therapist using menu option `4`.
3. Register at least one patient using menu option `1`.
4. Use menu option `6` to book an appointment.
5. Choose `1` for emergency, `2` for urgent, or `3` for routine priority.
6. Use menu option `7` to inspect the current waiting queue.
7. Use menu option `8` to call the next appointment.
8. Use option `9` to complete an in-progress appointment.
9. Use option `10` to cancel an appointment when required.
10. Use option `11` to generate a daily report.

## 10. Persistence

Runtime data is stored in the `data/` directory:

- `patients.txt`
- `therapists.txt`
- `appointments.txt`

The application creates these files automatically on first use.

Application logs are written to:

```text
logs/clinic.log
```

The repository `.gitignore` excludes generated runtime files from Git.

## 11. Data Privacy Approach

The project is an educational prototype, not a production healthcare information system. The console masks phone numbers and uses patient initials inside queue-related displays. Logs avoid storing full patient contact information. Local text files are not encrypted; production healthcare deployment would require stronger access control, encryption, auditing, and legal/privacy compliance.

## 12. Design Highlights

- OOP model classes for patient, therapist, and appointment entities.
- Repository classes separate persistence from business logic.
- `ClinicService` contains validation and business rules.
- `ConsoleUI` handles user interaction.
- `PriorityQueue<Appointment>` implements appointment prioritization.
- `Comparable<Appointment>` defines deterministic queue ordering.
- Custom test runner keeps the project dependency-free.
- File-based storage makes the application restart-safe without requiring a database server.

## 13. VITyarthi Deliverables Included

This repository contains the source code plus the supporting documentation needed for the course project:

- `README.md`
- `statement.md`
- Functional/non-functional requirements
- System architecture documentation
- Workflow and UML diagrams
- Design decisions
- Testing documentation
- User guide
- Command-line run scripts
- Source code with 5-10+ meaningful Java modules/classes

## 14. Academic Integrity

This project should be reviewed and customized by the submitting student. Add your own student details, screenshots, testing evidence, Git history, and any course-specific changes before submission. The VITyarthi instructions require original work.
