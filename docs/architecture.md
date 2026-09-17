# System Architecture

The system follows a lightweight layered architecture.

```text
+-----------------------+
|      ConsoleUI        |
| User interaction      |
+----------+------------+
           |
           v
+-----------------------+
|     ClinicService     |
| Business rules        |
| Validation            |
| Queue management      |
+----+-----------+------+
     |           |
     v           v
+---------+  +---------+
| Models  |  | Utility |
| Patient |  | Logging |
| Therapist| | IDs     |
| Appointment| Privacy|
+---------+  +---------+
     |
     v
+-----------------------+
|      Repositories     |
| PatientRepository     |
| TherapistRepository   |
| AppointmentRepository |
+-----------+-----------+
            |
            v
+-----------------------+
|   Text-file Storage   |
| data/*.txt            |
+-----------------------+
```

## Architectural Responsibilities

### Model Layer
Represents clinic entities and appointment states.

### Repository Layer
Loads, stores, and updates data files. It keeps persistence logic out of the user interface.

### Service Layer
Applies business rules such as working-hour validation and double-booking prevention. It also creates and processes the priority queue.

### Utility Layer
Handles IDs, input validation, privacy masking, and logging.

### UI Layer
Handles command-line prompts and renders user-facing output.

## Queue Design

`ClinicService` builds a `PriorityQueue<Appointment>` using appointments in `WAITING` status. `Appointment.compareTo()` provides deterministic ordering by:

1. Priority rank
2. Appointment date
3. Appointment time
4. Appointment ID
