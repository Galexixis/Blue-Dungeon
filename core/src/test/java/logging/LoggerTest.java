package logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blueDungeon.logging.LogEntry;
import blueDungeon.logging.LogLevel;
import blueDungeon.logging.Logger;

/**
 * @author Romain Vandooren
 */
public class LoggerTest {

    @BeforeEach
    void init(){
        Logger.clear();
    }

    @Test
    void testLogIsRecordedWithLevelAndMessage(){
        Logger.info("hello");

        List<LogEntry> logs = Logger.getRecentLogs();
        assertEquals(1, logs.size());
        assertEquals(LogLevel.INFO, logs.get(0).getLevel());
        assertEquals("hello", logs.get(0).getMessage());
        assertNull(logs.get(0).getError());
    }

    @Test
    void testErrorLogKeepsTheThrowable(){
        RuntimeException exception = new RuntimeException("boom");
        Logger.error("something failed", exception);

        LogEntry entry = Logger.getRecentLogs().get(0);
        assertEquals(LogLevel.ERROR, entry.getLevel());
        assertEquals(exception, entry.getError());
    }

    @Test
    void testBufferIsCircularAndKeepsOnlyMostRecentEntries(){
        for(int i = 0; i < 510; i++){
            Logger.debug("message " + i);
        }

        List<LogEntry> logs = Logger.getRecentLogs();
        assertEquals(500, logs.size());
        assertEquals("message 10", logs.get(0).getMessage());
        assertEquals("message 509", logs.get(499).getMessage());
    }

    @Test
    void testClearEmptiesTheBuffer(){
        Logger.info("hello");
        Logger.clear();

        assertTrue(Logger.getRecentLogs().isEmpty());
    }
}
