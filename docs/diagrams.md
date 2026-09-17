# Design Diagrams

The repository also contains Graphviz `.dot` files under `/diagrams` for generating PNG/SVG diagrams.

## 1. Use Case Diagram

```mermaid
flowchart LR
    Staff[Clinic Staff]
    Staff --> R[Register Patient]
    Staff --> T[Manage Therapist]
    Staff --> B[Book Appointment]
    Staff --> Q[View Priority Queue]
    Staff --> N[Call Next Appointment]
    Staff --> C[Complete / Cancel Appointment]
    Staff --> D[Generate Daily Report]
```

## 2. Workflow Diagram

```mermaid
flowchart TD
    A[Start Application] --> B[Main Menu]
    B --> C[Register Patient]
    B --> D[Add Therapist]
    B --> E[Book Appointment]
    E --> F{Valid Patient/Therapist?}
    F -- No --> X[Show Error]
    F -- Yes --> G{Valid Hours and No Conflict?}
    G -- No --> X
    G -- Yes --> H[Save Appointment as WAITING]
    H --> I[Priority Queue]
    I --> J[Call Next]
    J --> K[IN_PROGRESS]
    K --> L{Complete or Cancel}
    L --> M[COMPLETED]
    L --> N[CANCELLED]
    B --> O[Daily Report]
    B --> P[Exit]
    X --> B
    M --> B
    N --> B
    O --> B
```

## 3. Sequence Diagram

```mermaid
sequenceDiagram
    actor Staff
    participant UI as ConsoleUI
    participant Service as ClinicService
    participant Queue as PriorityQueue
    participant Repo as AppointmentRepository
    participant Store as Text File

    Staff->>UI: Select Book Appointment
    UI->>Service: bookAppointment(...)
    Service->>Repo: Check existing appointments
    Repo-->>Service: Existing records
    Service->>Service: Validate working hours/conflicts
    Service->>Repo: save(new Appointment)
    Repo->>Store: Write appointments.txt
    Service-->>UI: Appointment created
    UI-->>Staff: Show appointment ID

    Staff->>UI: Call next appointment
    UI->>Service: callNextAppointment()
    Service->>Queue: Build WAITING queue
    Queue-->>Service: Highest-priority appointment
    Service->>Repo: Update status IN_PROGRESS
    Repo->>Store: Persist change
    Service-->>UI: Appointment details
    UI-->>Staff: Display called patient initials
```

## 4. Class Diagram

```mermaid
classDiagram
    class Patient {
      -String id
      -String fullName
      -int age
      -String phone
      -String medicalConcern
      -LocalDateTime registeredAt
    }
    class Therapist {
      -String id
      -String name
      -String specialization
      -LocalTime workingStart
      -LocalTime workingEnd
    }
    class Appointment {
      -String id
      -String patientId
      -String therapistId
      -LocalDate date
      -LocalTime time
      -PriorityLevel priority
      -AppointmentStatus status
      -String notes
      +compareTo(Appointment)
      +setStatus(AppointmentStatus)
    }
    class ClinicService {
      +registerPatient(...)
      +addTherapist(...)
      +bookAppointment(...)
      +getQueue()
      +callNextAppointment()
      +completeAppointment(...)
      +cancelAppointment(...)
    }
    class PatientRepository
    class TherapistRepository
    class AppointmentRepository

    ClinicService --> PatientRepository
    ClinicService --> TherapistRepository
    ClinicService --> AppointmentRepository
    Appointment --> Patient
    Appointment --> Therapist
```

## 5. ER/Storage View

The project uses file storage rather than a relational database. The logical relationships are:

```text
PATIENT (patient_id, name, age, phone, concern)
       1
       |
       | has many
       v
APPOINTMENT (appointment_id, patient_id, therapist_id, date, time, priority, status, notes)
       ^
       | many-to-one
       |
THERAPIST (therapist_id, name, specialization, start_time, end_time)
```
