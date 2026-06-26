package time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blueDungeon.logic.common.time.TickSensitive;
import blueDungeon.logic.common.time.TickTimer;

/**
 * @author Galexis
 */
public class TickTimerTest {

    private TickTimer tickTimer;
    private TickObserver tickObserver;

    private class TickObserver implements TickSensitive{
         
        public int lastTickUpdate = -1;
        public int value = 0;

        @Override
        public void onTickUpdate(int actualTick) {
            value++;
            lastTickUpdate = actualTick;
        }
    }

    @BeforeEach
    private void init(){
        this.tickTimer = new TickTimer(0.1f);
        this.tickObserver = new TickObserver();
        this.tickTimer.addObserver(tickObserver);
    }

    @Test
    /**
     * Test le déclanchement d'un tick avec une frame de même temps.
     */
    void basicTest(){
        tickTimer.onFrameUpdate(0.1f);
        assertEquals(1, tickObserver.lastTickUpdate);
    }

    @Test
    /**
     * Test losque la frame est plus grand que un tick.
     */
    void bigFrameTest(){
        tickTimer.onFrameUpdate(0.35f);
        assertEquals(3, tickObserver.value);
    }

    @Test
    /**
     * Test lorsque la frame est plus petite qu'un tick.
     */
    void smallFrameTest(){
        tickTimer.onFrameUpdate(0.06f);
        assertEquals(0, tickObserver.value);
        tickTimer.onFrameUpdate(0.06f);
        assertEquals(1, tickObserver.value);
    }

    @Test
    /**
     * Test le reset du timer.
     */
    void resetTest(){
        tickTimer.onFrameUpdate(0.19f);
        assertEquals(1, tickTimer.getActualTick());
        tickTimer.reset();
        tickTimer.onFrameUpdate(0.19f);
        assertEquals(1, tickTimer.getActualTick());
    }
    
}