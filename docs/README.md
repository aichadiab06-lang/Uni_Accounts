# UniAccounts - TP1 (Creational Design Patterns)

University student account management system in Java.
Work 1: core of the system using three creational patterns.

## Team
- Bahri Radhia - Group 04
- Diab Aicha - Group 04

## Run (JDK 11 or later)

    mkdir out
    javac -d out src/model/*.java src/audit/*.java src/repository/*.java src/app/*.java
    java -cp out app.Main

## Project structure

    src/
      model/StudentAccount.java          Builder + Prototype
      audit/AuditLogger.java             Singleton
      repository/AccountRepository.java  Singleton
      app/Main.java                      Demo and validation tests

## Patterns used

| Pattern | Class | Role | Problem solved |
|---|---|---|---|
| Builder | StudentAccount.Builder | Concrete Builder | Account has 8 fields, some optional: avoids a constructor with 8 parameters |
| Singleton | AccountRepository | Singleton | One central repository, no accounts lost between copies |
| Singleton | AuditLogger | Singleton | One single audit log for all operations |
| Prototype | StudentAccount (copy, cloneWithIdentity) | Concrete Prototype | Clone a batch template and change only the student identity |

## Validation rules
- id: required, not empty, unique in the repository
- fullName: required
- email: required, must contain "@"
- level: L1, L2, L3, M1 or M2 (default L1)
- phone default null, scholarship default false, courses empty by default

## Sources
- Claude (Anthropic) - assistance in understanding the creational patterns and reviewing the code
- TP handout "Design Patterns - UniAccounts" (course material provided by the instructor)