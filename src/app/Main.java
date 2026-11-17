package app;

import audit.AuditLogger;
import model.StudentAccount;
import repository.AccountRepository;

public class Main {
    public static void main(String[] args) {

        AccountRepository repo = AccountRepository.getInstance();

        // ---------- 1) SINGLETON ----------
        System.out.println("--- Singleton ---");
        System.out.println("Meme depot ? " + (repo == AccountRepository.getInstance()));
        System.out.println("Meme logger ? " + (AuditLogger.getInstance() == AuditLogger.getInstance()));

        // ---------- 2) BUILDER ----------
        System.out.println("\n--- Builder ---");
        StudentAccount minimal = new StudentAccount.Builder("2024001", "Radhia Ben", "radhia@univ.dz").build();
        System.out.println(minimal);

        StudentAccount full = new StudentAccount.Builder("2024002", "Amine Kaci", "amine@univ.dz")
                .phone("0555123456").level("L2").major("Informatique")
                .scholarship(true).addCourse("POO").addCourse("BDD").build();
        System.out.println(full);

        try {
            new StudentAccount.Builder("2024003", "Test", "email-sans-arobase").build();
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
