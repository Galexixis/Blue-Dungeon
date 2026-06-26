package blueDungeon.engine.timer;

import blueDungeon.utils.Observable;
import blueDungeon.utils.ObserverManager;

/**
 * Timer en temps réel sur base des frames.
 * @author Galexis
 */
public class PhysiqueTimer implements Observable<PhysiqueTimerSensitive>{

    private final ObserverManager<PhysiqueTimerSensitive> observerManager = new ObserverManager<>();

    /**
     * Appelé à chaque frame par LibGDX.
     * @param dt Le temps écoulé depuis la dernière frame.
     */
    public void newFrameUpdate(float dt) {
        notifyObservers(o -> o.onFrameUpdate(dt));
    }

    // interface Observable

    @Override
    public ObserverManager<PhysiqueTimerSensitive> getObserverManager(){
        return observerManager;
    }
    
}