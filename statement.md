# Project Statement - PhysioClinic Appointment Queue System

## Problem Statement

A small physiotherapy clinic needs a lightweight system to organize patient registration, therapist availability, appointment booking, and the order in which patients are served. Manual scheduling can create duplicate time slots and makes it difficult to prioritize emergency cases consistently.

## Scope

The project covers:

- Patient registration and search
- Therapist registration and working hours
- Appointment booking
- Double-booking prevention
- Emergency/urgent/routine queue prioritization
- Appointment status lifecycle
- Daily appointment summary
- Local file-based persistence
- Basic privacy-aware console/log output

The project does not attempt to provide medical diagnosis, treatment recommendations, billing, insurance processing, or production healthcare compliance.

## Target Users

- Clinic receptionist or front-desk operator
- Physiotherapist/therapist managing a daily queue
- Academic evaluator examining Java design and data-structure usage

## High-Level Features

1. Register patients with validated contact and visit information.
2. Register therapists with specialization and working hours.
3. Book appointments while checking schedule conflicts.
4. Rank waiting appointments using Java `PriorityQueue`.
5. Call the next patient and update appointment status.
6. Complete or cancel appointments.
7. Generate daily status summaries.
8. Save data locally between application runs.
9. Maintain application logs without exposing full phone numbers.

## Main Inputs

- Patient name, age, phone, and concern
- Therapist name, specialization, start time, end time
- Patient/therapist IDs
- Appointment date/time
- Priority level
- Appointment notes

## Main Outputs

- Generated record IDs
- Validation messages
- Appointment confirmation
- Current queue order
- Appointment status updates
- Daily report
