import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/** Singleton : UN SEUL journal d'audit pour tout le programme. */
public class AuditLogger {

    private final List<String> entries = new ArrayList<>();

    private AuditLogger() { }   // constructeur prive

    // Holder idiom : thread-safe + chargement paresseux (lazy)
    private static class Holder {
        private static final AuditLogger INSTANCE = new AuditLogger();
    }

    public static AuditLogger getInstance() { return Holder.INSTANCE; }

    public synchronized void log(String action) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        entries.add("[" + time + "] " + action);
    }

    public synchronized void printAll() {
        System.out.println("===== AUDIT LOG =====");
        for (String e : entries) System.out.println(e);
    }
}
