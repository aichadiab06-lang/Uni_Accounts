package audit;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern SINGLETON : une seule instance de AuditLogger dans tout le programme.
 * Role : Singleton (constructeur prive + getInstance()).
 */
public class AuditLogger {

    private static AuditLogger instance;          // l'unique instance
    private final List<String> entries = new ArrayList<>();

    private AuditLogger() { }                     // interdit new AuditLogger() depuis l'exterieur

    public static synchronized AuditLogger getInstance() {
        if (instance == null) {
            instance = new AuditLogger();
        }
        return instance;
    }

    public void log(String message) {
        entries.add(message);
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
