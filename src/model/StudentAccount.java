package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern BUILDER  : StudentAccount.Builder construit le compte (champs obligatoires + optionnels).
 * Pattern PROTOTYPE: StudentAccount implemente Cloneable; clone() copie un modele, withIdentity() change l'identite.
 */
public class StudentAccount implements Cloneable {

    // Champs obligatoires
    private String id;
    private String fullName;
    private String email;

    // Champs optionnels
    private String phone;          // defaut : null
    private String level;          // defaut : "L1"
    private String major;
    private boolean scholarship;   // defaut : false
    private List<String> courses;  // defaut : liste vide

    // Constructeur prive : seul le Builder peut creer un compte
    private StudentAccount(Builder b) {
        this.id = b.id;
        this.fullName = b.fullName;
        this.email = b.email;
        this.phone = b.phone;
        this.level = b.level;
        this.major = b.major;
        this.scholarship = b.scholarship;
        this.courses = new ArrayList<>(b.courses);
    }

    // ---------- Getters ----------
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getLevel() { return level; }
    public String getMajor() { return major; }
    public boolean hasScholarship() { return scholarship; }
    public List<String> getCourses() { return new ArrayList<>(courses); }

    // ---------- PROTOTYPE ----------
    // Role : ConcretePrototype. Copie profonde (la liste des cours est dupliquee).
    @Override
    public StudentAccount clone() {
        try {
            StudentAccount copy = (StudentAccount) super.clone();
            copy.courses = new ArrayList<>(this.courses);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    // Change uniquement l'identite de la copie (id, nom, email) et valide les regles
    public StudentAccount withIdentity(String id, String fullName, String email) {
        Builder.validate(id, fullName, email);
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "StudentAccount{id=" + id
                + ", name=" + fullName
                + ", email=" + email
                + ", phone=" + phone
                + ", level=" + level
                + ", major=" + major
                + ", scholarship=" + scholarship
                + ", courses=" + courses + "}";
    }

    // =====================================================
    // BUILDER
    // Role : Builder (classe interne statique).
    // =====================================================
    public static class Builder {
        // obligatoires
        private final String id;
        private final String fullName;
        private final String email;
        // optionnels avec valeurs par defaut
        private String phone = null;
        private String level = "L1";
        private String major = null;
        private boolean scholarship = false;
        private final List<String> courses = new ArrayList<>();

        public Builder(String id, String fullName, String email) {
            validate(id, fullName, email);
            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }

        static void validate(String id, String fullName, String email) {
            if (id == null || id.trim().isEmpty())
                throw new IllegalArgumentException("L'id ne doit pas etre vide");
            if (fullName == null || fullName.trim().isEmpty())
                throw new IllegalArgumentException("Le nom complet ne doit pas etre vide");
            if (email == null || !email.contains("@"))
                throw new IllegalArgumentException("Email invalide (doit contenir @) : " + email);
        }

        public Builder phone(String phone) { this.phone = phone; return this; }

        public Builder level(String level) {
            List<String> valid = java.util.Arrays.asList("L1", "L2", "L3", "M1", "M2");
            if (!valid.contains(level))
                throw new IllegalArgumentException("Niveau invalide : " + level);
            this.level = level;
            return this;
        }

        public Builder major(String major) { this.major = major; return this; }

        public Builder scholarship(boolean scholarship) { this.scholarship = scholarship; return this; }

        public Builder addCourse(String course) { this.courses.add(course); return this; }

        public StudentAccount build() { return new StudentAccount(this); }
    }
}
