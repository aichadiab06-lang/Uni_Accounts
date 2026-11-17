import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StudentAccount : le compte d'un etudiant.
 * - Builder   : construction pas a pas (classe interne Builder)
 * - Prototype : duplication via clone() (copie profonde)
 */
public class StudentAccount implements Cloneable {

    private static final List<String> LEVELS = Arrays.asList("L1", "L2", "L3", "M1", "M2");

    // ---- Champs obligatoires ----
    private String id;
    private String fullName;
    private String email;
    // ---- Champs optionnels ----
    private String phone;              // defaut : null
    private String level;              // defaut : "L1"
    private String major;
    private boolean scholarship;       // defaut : false
    private List<String> courses;      // defaut : liste vide

    // Constructeur PRIVE : on ne peut creer un compte que via le Builder
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

    // ================= PROTOTYPE =================
    @Override
    public StudentAccount clone() {
        try {
            StudentAccount copy = (StudentAccount) super.clone(); // copie superficielle
            copy.courses = new ArrayList<>(this.courses);         // copie PROFONDE de la liste
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e); // impossible : on implemente Cloneable
        }
    }

    /** Change uniquement l'identite de l'etudiant (utilise apres clone()). */
    public StudentAccount withIdentity(String id, String fullName, String email) {
        validateIdentity(id, fullName, email);
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        return this;
    }

    private static void validateIdentity(String id, String fullName, String email) {
        if (id == null || id.trim().isEmpty())
            throw new IllegalArgumentException("id obligatoire et non vide");
        if (fullName == null || fullName.trim().isEmpty())
            throw new IllegalArgumentException("fullName obligatoire");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("email invalide (doit contenir @)");
    }

    // ---- Getters ----
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getLevel() { return level; }
    public String getMajor() { return major; }
    public boolean hasScholarship() { return scholarship; }
    public List<String> getCourses() { return new ArrayList<>(courses); }

    @Override
    public String toString() {
        return "StudentAccount{id=" + id + ", name=" + fullName + ", email=" + email
                + ", phone=" + phone + ", level=" + level + ", major=" + major
                + ", scholarship=" + scholarship + ", courses=" + courses + "}";
    }

    // ================= BUILDER =================
    public static class Builder {
        // obligatoires
        private final String id;
        private final String fullName;
        private final String email;
        // optionnels avec valeurs par defaut
        private String phone = null;
        private String level = "L1";
        private String major;
        private boolean scholarship = false;
        private List<String> courses = new ArrayList<>();

        /** Les 3 champs obligatoires sont imposes des le depart. */
        public Builder(String id, String fullName, String email) {
            validateIdentity(id, fullName, email);
            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }

        public Builder phone(String phone) { this.phone = phone; return this; }

        public Builder level(String level) {
            if (!LEVELS.contains(level))
                throw new IllegalArgumentException("level doit etre parmi " + LEVELS);
            this.level = level;
            return this;
        }

        public Builder major(String major) { this.major = major; return this; }
        public Builder scholarship(boolean s) { this.scholarship = s; return this; }

        public Builder addCourse(String course) { this.courses.add(course); return this; }

        public StudentAccount build() { return new StudentAccount(this); }
    }
}
