package blueDungeon.logic.common.time;

/**
 * @author Galexis
 */
public interface TickSensitive {

    /**
     * Signal envoyé au changement de tick.
     * @param actualTick
     */
    public void onTickUpdate(int actualTick);
    
}
