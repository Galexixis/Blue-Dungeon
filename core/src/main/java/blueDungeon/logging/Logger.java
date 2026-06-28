package blueDungeon.logging;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Logger statique global. Garde les dernières entrées en mémoire (buffer circulaire)
 * pour pouvoir retracer les dernières actions exécutées lors d'un débogage,
 * en plus d'afficher chaque entrée sur la sortie standard/erreur.
 *
 * @author Romain Vandooren
 */
public final class Logger {

    private static final int MAX_ENTRIES = 500;
    private static final Deque<LogEntry> entries = new ArrayDeque<>(MAX_ENTRIES);

    private Logger(){}

    public static void debug(String message){
        log(LogLevel.DEBUG, message, null);
    }

    public static void info(String message){
        log(LogLevel.INFO, message, null);
    }

    public static void warn(String message){
        log(LogLevel.WARN, message, null);
    }

    public static void error(String message){
        log(LogLevel.ERROR, message, null);
    }

    public static void error(String message, Throwable error){
        log(LogLevel.ERROR, message, error);
    }

    private static synchronized void log(LogLevel level, String message, Throwable error){
        LogEntry entry = new LogEntry(System.currentTimeMillis(), level, message, error);

        entries.addLast(entry);
        if(entries.size() > MAX_ENTRIES){
            entries.removeFirst();
        }

        if(level == LogLevel.ERROR){
            System.err.println(entry);
            if(error != null){
                error.printStackTrace();
            }
        } else {
            System.out.println(entry);
        }
    }

    /**
     * @return les dernières entrées de log, de la plus ancienne à la plus récente.
     */
    public static synchronized List<LogEntry> getRecentLogs(){
        return Collections.unmodifiableList(List.copyOf(entries));
    }

    /**
     * Vide le buffer de log (utile entre deux tests).
     */
    public static synchronized void clear(){
        entries.clear();
    }
}
