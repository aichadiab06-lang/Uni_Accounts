import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Singleton : UN SEUL depot qui contient tous les comptes. */
public class AccountRepository {

    private final Map<String, StudentAccount> accounts = new LinkedHashMap<>();
    private final AuditLogger logger = AuditLogger.getInstance();

    private AccountRepository() { }

    private static class Holder {
        private static final AccountRepository INSTANCE = new AccountRepository();
    }

    public static AccountRepository getInstance() { return Holder.INSTANCE; }

    public synchronized void add(StudentAccount acc) {
        if (accounts.containsKey(acc.getId())) {
            logger.log("ECHEC ajout : id deja existant " + acc.getId());
            throw new IllegalArgumentException("id deja existant : " + acc.getId());
        }
        accounts.put(acc.getId(), acc);
        logger.log("AJOUT compte " + acc.getId() + " (" + acc.getFullName() + ")");
    }

    public synchronized StudentAccount findById(String id) {
        logger.log("RECHERCHE compte " + id);
        return accounts.get(id);
    }

    public synchronized int count() { return accounts.size(); }

    public synchronized List<StudentAccount> findAll() {
        return new ArrayList<>(accounts.values());
    }
}
