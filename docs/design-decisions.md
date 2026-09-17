# Design Decisions and Rationale

## 1. Java Standard Library Only

The project uses no third-party runtime libraries. This reduces setup failures and makes command-line evaluation straightforward.

## 2. PriorityQueue for Queue Management

Java `PriorityQueue` directly represents the requirement that some cases must be served before others. `Appointment.compareTo()` encapsulates the ordering rule.

## 3. Layered Package Structure

Models, repositories, services, UI, utilities, and tests are separated so each concern can be modified without rewriting the entire application.

## 4. File-Based Persistence

Text files were selected instead of a database because the course project needs a self-contained command-line application. The approach keeps the environment simple and demonstrates persistence without external infrastructure.

## 5. Appointment Status State Machine

Appointments move through explicit statuses: `WAITING -> IN_PROGRESS -> COMPLETED`, or `WAITING/IN_PROGRESS -> CANCELLED`. This prevents arbitrary state transitions.

## 6. Validation Before Persistence

Patient, therapist, date/time, working-hour, and conflict checks are performed before a record is saved. This protects the stored data from invalid appointment states.

## 7. Privacy-Aware Console Output

The project masks phone numbers and uses patient initials in queue output. This demonstrates privacy-conscious output handling while keeping the interface readable.

## 8. Dependency-Free Testing

A custom executable test runner was chosen so the project can demonstrate automated checks without requiring Maven, Gradle, or downloading JUnit dependencies.
