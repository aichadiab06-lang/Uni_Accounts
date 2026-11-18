package audit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * UniAccounts - TP1 : journal d'audit.
 *
 * @author Aicha Diab
 * @author Radhia Bahri
 */
// Singleton : une seule instance de AuditLogger dans tout le programme
// (constructeur prive + variable statique + getInstance()).
public class AuditLogger {

    private static AuditLogger instance;          // l'unique instance
    private final List<String> entries = new ArrayList<>();

    private AuditLogger() { }                     // interdit new AuditLogger() depuis l'exterieur

    // Singleton : point d'acces unique (synchronized = securise avec plusieurs threads)
    public static synchronized AuditLogger getInstance() {
        if (instance == null) {
            instance = new AuditLogger();
        }
        return instance;
    }

    public void log(String message) {
        entries.add(message);
    }

    public int size() {
        return entries.size();
    }

    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public void printAll() {
        System.out.println("--- Journal d'audit ---");
        if (entries.isEmpty()) {
            System.out.println("(vide)");
        }
        for (int i = 0; i < entries.size(); i++) {
            System.out.println((i + 1) + ". " + entries.get(i));
        }
    }
}
