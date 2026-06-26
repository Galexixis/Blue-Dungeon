package blueDungeon.engine.timer;

/**
 * @author Galexis
 */
public interface PhysiqueTimerSensitive {

    /**
     * Methode appelée au changement de frame, contenant le delta temps de la dernière frame.
     * @param dt
     */
    public void onFrameUpdate(float dt);
    
}