# PhysioClinic Appointment Queue System

**Student:** Sayan Manna  
**Registration No.:** 25BAI10467  
**Branch:** CSE (AI ML)  
**Institution:** VIT Bhopal University  
**Course:** Programming in Java

## 1. Project Overview

PhysioClinic Appointment Queue System is a command-line Java application for handling basic clinic appointment work. It allows users to register patients, add therapists, manage therapist working hours, book appointments, handle priority-based queues, and maintain appointment status.

The project is made for the Programming in Java course. It uses object-oriented programming, Java collections, file handling, validation, exception handling, logging, and a `PriorityQueue`.

The project does not use any external Java library or database server. It can be compiled and run directly from a terminal with Java 17 or later.

## 2. Problem Statement

A small physiotherapy clinic needs a simple system to keep patient records, manage therapist schedules, book appointments, avoid double-booking, and handle urgent appointments before routine appointments.

This project provides these functions through a command-line interface. Patient and appointment information is stored locally so that the data is available when the application is started again.

## 3. Main Modules

### 3.1 Patient Registration and Search

- Register new patients.
- Store patient records in local files.
- Search patients by name.
- Mask phone numbers when patient information is displayed.

### 3.2 Therapist Scheduling

- Add therapists and their specializations.
- Store therapist working hours.
- Check whether an appointment falls within the therapist's working hours.

### 3.3 Appointment Booking and Validation

- Book an appointment for a patient with a therapist.
- Prevent a therapist from being booked for two appointments at the same time.
- Prevent a patient from having a conflicting appointment.
- Validate required fields, dates, and times.

### 3.4 Priority Appointment Queue

The application uses Java's `PriorityQueue` to handle appointments according to priority.

Priority order:

```text
EMERGENCY -> URGENT -> ROUTINE
```

If two appointments have the same priority, their appointment date and time are used to keep the queue order consistent.

### 3.5 Appointment Status and Daily Report

- Call the next appointment from the waiting queue.
- Mark an appointment as `IN_PROGRESS`.
- Complete an appointment.
- Cancel an appointment.
- Generate a daily appointment report.

## 4. Technologies Used

- Java 17 or later
- Java Standard Library
- `PriorityQueue`
- `List`
- `Optional`
- `Stream`
- `LocalDate`
- `LocalTime`
- `LocalDateTime`
- File-based storage using UTF-8 text files
- `java.util.logging`
- Git and GitHub

No external Java dependencies are required.

## 5. Project Structure

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

## 6. Requirements

Install a JDK that supports Java 17 or later.

Check the installed version with:

```bash
java -version
javac -version
```

## 7. Running the Application

### macOS / Linux

Clone the repository and move into the project directory:

```bash
git clone https://github.com/sayan25bai10467-dev/physioclinic-appointment-queue.git
cd physioclinic-appointment-queue
```

Then run:

```bash
bash run.sh
```

### Windows CMD

```bat
git clone https://github.com/sayan25bai10467-dev/physioclinic-appointment-queue.git
cd physioclinic-appointment-queue
run.bat
```

### Run Without the Helper Script

The project can also be compiled directly from the terminal.

macOS / Linux:

```bash
rm -rf out
mkdir out
javac --release 17 -d out @sources.txt
java -cp out com.vit.physioclinic.Main
```

Windows:

```bat
if exist out rmdir /s /q out
mkdir out
javac --release 17 -d out @sources.txt
java -cp out com.vit.physioclinic.Main
```

## 8. Running the Tests

### macOS / Linux

```bash
bash test.sh
```

### Windows CMD

```bat
test.bat
```

### Direct Test Command

```bash
rm -rf out
mkdir out
javac --release 17 -d out @sources.txt
java -cp out com.vit.physioclinic.tests.TestRunner
```

The current test suite checks:

- Patient registration
- Appointment booking
- Double-booking prevention
- Priority queue ordering
- Calling and completing an appointment
- Working-hours validation

Expected result:

```text
Passed: 6
Failed: 0
```

## 9. How to Use the Program

After starting the application:

1. Add a therapist using menu option `4`.
2. Register a patient using menu option `1`.
3. Use menu option `6` to book an appointment.
4. Select the required priority: `1` for emergency, `2` for urgent, or `3` for routine.
5. Use menu option `7` to view the waiting queue.
6. Use menu option `8` to call the next appointment.
7. Use option `9` to complete an appointment that is in progress.
8. Use option `10` to cancel an appointment when required.
9. Use option `11` to generate a daily report.

## 10. Data Storage

The application stores runtime data in the `data/` directory.

The following files are created automatically when required:

```text
data/patients.txt
data/therapists.txt
data/appointments.txt
```

Application logs are stored in:

```text
logs/clinic.log
```

Generated runtime files are excluded from Git using the project's `.gitignore` file.

## 11. Privacy Measures in the Project

This is an academic project and is not intended for production healthcare use.

The application takes a few basic privacy measures:

- Phone numbers are masked when displayed in the console.
- Patient initials are used in queue-related displays.
- Logs avoid storing full patient contact information.

The local text files are not encrypted. A production healthcare system would need stronger access control, encryption, auditing, and appropriate privacy and legal safeguards.

## 12. Java Concepts Used

The project applies the following Java concepts:

- Classes and objects
- Encapsulation
- Enums
- Interfaces and comparable ordering
- Collections
- `PriorityQueue`
- Lists and streams
- File input/output
- Date and time API
- Exception handling
- Input validation
- Logging
- Modular package structure

The main application is divided into model, repository, service, UI, utility, and test packages so that different responsibilities are kept separate.

## 13. Documentation Included

The repository also contains the project documentation required for the course:

- `README.md`
- `statement.md`
- Functional and non-functional requirements
- Architecture documentation
- Workflow and UML diagrams
- Design decisions
- Testing documentation
- User guide
- Command-line run and test scripts
- Project report PDF

## 14. Student Declaration

This repository is submitted as part of the Programming in Java course project in VITyarthi.

**Sayan Manna**  
**25BAI10467**  
**CSE (AI ML)**  
**VIT Bhopal University**
