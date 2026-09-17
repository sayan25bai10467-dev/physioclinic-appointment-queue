# Requirements Specification

## Functional Requirements

### FR-01: Patient Registration
The system shall create a unique patient record after validating name, age, phone number, and medical concern.

### FR-02: Patient Search
The system shall allow staff to search stored patients by name.

### FR-03: Therapist Management
The system shall create therapist records containing name, specialization, and working hours.

### FR-04: Appointment Booking
The system shall create an appointment for a valid patient, therapist, date, time, priority, and note.

### FR-05: Working-Hour Validation
The system shall reject appointment requests outside the selected therapist's working hours.

### FR-06: Double-Booking Prevention
The system shall reject an appointment when the same therapist already has an active appointment at the same date and time.

### FR-07: Patient Conflict Prevention
The system shall reject a conflicting appointment for the same patient at the same date and time.

### FR-08: Priority Queue
The system shall maintain waiting appointments in a Java `PriorityQueue`, with emergency cases ahead of urgent cases and routine cases.

### FR-09: Queue Processing
The system shall allow staff to call the highest-priority waiting appointment and move it to `IN_PROGRESS`.

### FR-10: Appointment Completion/Cancellation
The system shall allow in-progress appointments to be completed and active appointments to be cancelled according to status rules.

### FR-11: Daily Report
The system shall produce counts for total, waiting, in-progress, completed, and cancelled appointments for a selected date.

### FR-12: Persistence
The system shall save patients, therapists, and appointments in local UTF-8 text files.

## Non-Functional Requirements

### NFR-01: Reliability
Validation and conflict checks shall prevent invalid appointment states and double-booking.

### NFR-02: Usability
The application shall provide a numbered, command-line menu with clear prompts and error messages.

### NFR-03: Maintainability
Business logic, storage, user interface, models, and utilities shall be separated into packages/classes.

### NFR-04: Performance
For the expected educational workload, queue insertion/removal shall use Java's `PriorityQueue`, providing efficient priority operations.

### NFR-05: Privacy
Phone numbers shall be masked in patient list displays, and application logs shall avoid full patient contact details.

### NFR-06: Portability
The application shall use only standard Java APIs and shall run on Java 17+ without a database server or GUI.

### NFR-07: Error Handling
Invalid input, missing records, conflicts, and illegal status transitions shall produce controlled error messages rather than silent failures.

### NFR-08: Resource Efficiency
The system uses local text storage and in-memory collections, avoiding external runtime infrastructure for the project scope.
