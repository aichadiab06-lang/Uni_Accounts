package repository;

import audit.AuditLogger;
import model.StudentAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern SINGLETON : un seul depot central pour tous les comptes.
 * Role : Singleton (constructeur prive + getInstance()).
 */
public class AccountRepository {

    private static AccountRepository instance;    // l'unique instance
    private final List<StudentAccount> accounts = new ArrayList<>();

    private AccountRepository() { }

    public static synchronized AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    // Ajoute un compte ; refuse un id deja present (id unique dans le depot)
    public void add(StudentAccount account) {
        for (StudentAccount a : accounts) {
            if (a.getId().equals(account.getId())) {
                throw new IllegalArgumentException("Id deja existant : " + account.getId());
            }
        }
        accounts.add(account);
        AuditLogger.getInstance().log("Compte ajoute : " + account.getId() + " (" + account.getFullName() + ")");
    }

    public int count() {
        return accounts.size();
    }

    public List<StudentAccount> findAll() {
        return new ArrayList<>(accounts);
    }
}
