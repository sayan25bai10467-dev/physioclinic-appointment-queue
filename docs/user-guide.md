# Command-Line User Guide

## Typical Demo Flow

### 1. Add therapist

Choose `4` and enter a therapist such as:

```text
Name: Ananya Sharma
Specialization: Sports Physiotherapy
Working start: 09:00
Working end: 17:00
```

### 2. Register patient

Choose `1` and enter patient information. The system generates a patient ID such as `P001`.

### 3. Book appointments

Choose `6` and select a patient, therapist, date, time, priority, and notes.

Try creating:

- One routine appointment at 10:00
- One emergency appointment at 11:00

### 4. Inspect priority queue

Choose `7`. The emergency appointment should appear before the routine appointment.

### 5. Call next appointment

Choose `8`. The highest-priority waiting appointment is moved to `IN_PROGRESS`.

### 6. Complete

Choose `9` and enter the appointment ID. The status becomes `COMPLETED`.

### 7. Report

Choose `11` and enter a date in `YYYY-MM-DD` format to view appointment counts.
