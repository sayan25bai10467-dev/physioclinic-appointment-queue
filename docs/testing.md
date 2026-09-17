# Testing Approach

The project includes an executable test runner at:

```text
src/main/java/com/vit/physioclinic/tests/TestRunner.java
```

## Test Cases

| Test | Purpose | Expected Result |
|---|---|---|
| Patient registration | Validate basic registration and ID creation | PASS |
| Successful appointment booking | Verify a valid appointment can be saved | PASS |
| Double-booking prevention | Reject duplicate therapist/date/time | PASS |
| Priority queue ordering | Emergency precedes routine | PASS |
| Call and complete | Verify status lifecycle | PASS |
| Working-hours validation | Reject an out-of-hours slot | PASS |

## Execute Tests

```bash
bash test.sh
```

Expected:

```text
[PASS] Patient registration
[PASS] Successful appointment booking
[PASS] Double-booking prevention
[PASS] Priority queue ordering
[PASS] Call next and complete
[PASS] Working-hours validation

Passed: 6
Failed: 0
```

## Error Handling Tested

- Invalid age
- Invalid phone format
- Invalid date/time format
- Missing patient ID
- Missing therapist ID
- Appointment in the past
- Appointment outside therapist working hours
- Therapist double-booking
- Patient time conflict
- Invalid appointment status transition
