package repository;

import audit.AuditLogger;
import model.StudentAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * UniAccounts - TP1 : depot central des comptes.
 *
 * @author Aicha Diab
 * @author Radhia Bahri
 */
// Singleton : un seul depot pour tous les comptes
// (sinon les comptes seraient perdus entre plusieurs copies du depot).
public class AccountRepository {

    private static AccountRepository instance;    // l'unique instance
    private final List<StudentAccount> accounts = new ArrayList<>();

    private AccountRepository() { }               // constructeur prive

    // Singleton : point d'acces unique
    public static synchronized AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    // Ajoute un compte ; refuse un id deja present (id unique dans le depot)
    public void add(StudentAccount account) {
        if (account == null)
            throw new IllegalArgumentException("Compte null");
        if (findById(account.getId()).isPresent())
            throw new IllegalArgumentException("Id deja existant : " + account.getId());
        accounts.add(account);
        AuditLogger.getInstance().log("Compte ajoute : " + account.getId() + " (" + account.getFullName() + ")");
    }

    public Optional<StudentAccount> findById(String id) {
        for (StudentAccount a : accounts) {
            if (a.getId().equals(id)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

    public int count() {
        return accounts.size();
    }

    public List<StudentAccount> findAll() {
        return Collections.unmodifiableList(accounts);
    }
}
