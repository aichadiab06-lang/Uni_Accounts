package app;

import audit.AuditLogger;
import model.StudentAccount;
import repository.AccountRepository;

/**
 * UniAccounts - TP1 (Creational Design Patterns) : demonstration.
 * Groupe 04 : Aicha Diab - Radhia Bahri
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("=== UniAccounts - TP1 (Creational Patterns) ===");
        System.out.println("Equipe : Aicha Diab - Radhia Bahri (Groupe 04)");

        AccountRepository repo = AccountRepository.getInstance();

        // ---------- 1) SINGLETON ----------
        System.out.println("\n--- Singleton ---");
        System.out.println("Meme depot ? " + (repo == AccountRepository.getInstance()));
        System.out.println("Meme logger ? " + (AuditLogger.getInstance() == AuditLogger.getInstance()));

        // ---------- 2) BUILDER ----------
        System.out.println("\n--- Builder ---");
        StudentAccount minimal = new StudentAccount.Builder("2024001", "Radhia Bahri", "radhia.bahri@univ.dz").build();
        System.out.println(minimal);

        StudentAccount full = new StudentAccount.Builder("2024002", "Aicha Diab", "aicha.diab@univ.dz")
                .phone("0555123456").level("L2").major("Informatique")
                .scholarship(true).addCourse("POO").addCourse("BDD").build();
        System.out.println(full);

        try {
            new StudentAccount.Builder("2024003", "Test", "email-sans-arobase").build();
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }
        try {
            new StudentAccount.Builder("2024004", "Test", "test@univ.dz").level("L9").build();
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }

        repo.add(minimal);
        repo.add(full);

        // ---------- 3) PROTOTYPE ----------
        System.out.println("\n--- Prototype ---");
        StudentAccount template = new StudentAccount.Builder("TEMPLATE", "Modele", "modele@univ.dz")
                .level("L2").major("Informatique").addCourse("SE1").addCourse("POO").addCourse("Reseaux").build();

        StudentAccount s1 = template.clone().withIdentity("2024010", "Sara Mansouri", "sara@univ.dz");
        StudentAccount s2 = template.clone().withIdentity("2024011", "Yacine Hadj", "yacine@univ.dz");
        repo.add(s1);
        repo.add(s2);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println("Meme objet que le modele ? " + (s1 == template));
        System.out.println("Modele inchange : " + template.getFullName());

        // Copie profonde : modifier s1 ne touche ni s2 ni le modele
        s1.enroll("IA");
        System.out.println("Modules de s1      : " + s1.getCourses());
        System.out.println("Modules de s2      : " + s2.getCourses());
        System.out.println("Modules du modele  : " + template.getCourses());

        // ---------- Doublon ----------
        System.out.println("\n--- Test doublon ---");
        try {
            repo.add(template.clone().withIdentity("2024010", "Doublon", "d@univ.dz"));
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }

        System.out.println("\nNombre de comptes : " + repo.count());
        System.out.println();
        AuditLogger.getInstance().printAll();
    }
}
