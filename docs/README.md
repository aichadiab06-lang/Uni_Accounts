
## Patterns

| Pattern   | Class(es)                                  | Problem solved |
|-----------|--------------------------------------------|----------------|
| Builder   | `StudentAccount.Builder`                   | 8 fields, some optional: readable creation, validation, no constructor with 8 arguments |
| Singleton | `AccountRepository`, `AuditLogger`         | exactly one repository and one audit log in the whole program |
| Prototype | `StudentAccount.clone()` / `withIdentity()` | create many accounts of a cohort by copying a ready template (deep copy of the course list) |

## Class diagram

![UML](uml.png)

## Notes
- Every pattern is marked in the code with a comment (`// Singleton`, `// Builder`, `// Prototype`).
- Validation rules: id not empty and unique, full name not empty, email contains `@`, level in L1/L2/L3/M1/M2 (default L1)
