package eventSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blueDungeon.logic.common.eventSystem.EventBus;
import blueDungeon.logic.common.eventSystem.GameEventListener;
import blueDungeon.logic.common.eventSystem.gameEvent.GameEvent;

/**
 * @author Galexis
 */
public class eventBusTest {

    private EventBus eventBus;

    private class TestEvent implements GameEvent{

        private String message;

        public TestEvent(String message){
            this.message = message;
        }

        public String getMessage(){
            return message;
        } 
    }
    
    @BeforeEach
    private void init(){
        this.eventBus = new EventBus();
    }

    @Test
    /**
     * Test l'envoit d'un evenement et sa reception par un listener.
     */
    void sendAndRecieveTest(){
        TestEvent event = new TestEvent("succes");
        final String receiveMessage[] = { "error" };

        eventBus.register(TestEvent.class, onEvent -> {
            TestEvent e = (TestEvent) onEvent;
            receiveMessage[0] = e.getMessage();
        });
        eventBus.raiseEvent(event);
        
        assertEquals("succes", receiveMessage[0]);
    }

    @Test
    /**
     * Test le désenregistrement d'un listener
     */
    void unregisterEvent(){
        TestEvent event = new TestEvent(null);
        int[] count = { 0 };

        GameEventListener listener = onEvent -> {
            count[0]++;
        };

        eventBus.register(TestEvent.class, listener);
        eventBus.raiseEvent(event);
        assertEquals(1, count[0]);

        eventBus.unregister(TestEvent.class, listener);
        eventBus.raiseEvent(event);
        assertEquals(1, count[0]);
        
    }

    @Test
    /**
     * Test qu'une execption ne bloque pas les autres listener
     */
    void continueOnExeption(){
        TestEvent event = new TestEvent(null);
        int[] count = { 0 };

        eventBus.register(TestEvent.class, onEvent -> {
            count[0]++;
        });
        eventBus.register(TestEvent.class, onEvent -> {
            throw new RuntimeException("Exeption de test");
        });
        eventBus.register(TestEvent.class, onEvent -> {
            count[0]++;
        });
        eventBus.raiseEvent(event);

        assertEquals(2, count[0]);
    }
}