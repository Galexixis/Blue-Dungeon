package blueDungeon.logging;

/**
 * Une entrée de log immuable : quand, à quel niveau, quel message,
 * et l'exception associée si elle existe.
 *
 * @author Romain Vandooren
 */
public class LogEntry {

    private final long timestamp;
    private final LogLevel level;
    private final String message;
    private final Throwable error;

    public LogEntry(long timestamp, LogLevel level, String message, Throwable error){
        this.timestamp = timestamp;
        this.level = level;
        this.message = message;
        this.error = error;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public LogLevel getLevel(){
        return level;
    }

    public String getMessage(){
        return message;
    }

    public Throwable getError(){
        return error;
    }

    @Override
    public String toString(){
        String base = "[" + timestamp + "][" + level + "] " + message;
        if(error != null){
            base += " (" + error.getClass().getSimpleName() + ": " + error.getMessage() + ")";
        }
        return base;
    }
}
