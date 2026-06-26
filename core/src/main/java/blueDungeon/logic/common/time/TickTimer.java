package blueDungeon.logic.common.time;

import blueDungeon.engine.timer.PhysiqueTimerSensitive;
import blueDungeon.utils.Observable;
import blueDungeon.utils.ObserverManager;

/**
 * @author Galexis
 */
public class TickTimer implements PhysiqueTimerSensitive, Observable<TickSensitive> {

    private final ObserverManager<TickSensitive> observerManager = new ObserverManager<>();

    /** Accumule les delta temps et diminue pour déclancher un tick */
    private float accumulator = 0f;
    private final float tickDuration;
    private int actualTick = 0;

    /**
     * Initialise un timer pour les ticks.
     * Si l'argument est un float alors cela correspond à la durée d'un tick en seconde.
     * Si l'argument est un entier alors cela correspond au nombre de tick par seconde.
     * @param tickDuration
     */
    public TickTimer(float tickDuration){
        this.tickDuration = tickDuration;
    }

    /**
     * Initialise un timer pour les ticks.
     * Si l'argument est un float alors cela correspond à la durée d'un tick en seconde.
     * Si l'argument est un entier alors cela correspond au nombre de tick par seconde.
     * @param tickRate
     */
    public TickTimer(int tickRate){
        this.tickDuration = (float) 1.0/tickRate;
    }

    public int getActualTick(){
        return actualTick;
    }

    /**
     * Réinitialise l'accumulateur et le nombre de tick déjà écoulés.
     */
    public void reset(){
        accumulator = 0f;
        actualTick = 0;
    }

    // interface PhysiqueTimerUpdate

    @Override
    public void onFrameUpdate(float dt){
        
        accumulator += dt;

        //Utilisation d'un while car un dt peut être plus grand qu'un tick
        while(accumulator >= tickDuration){
            accumulator -= tickDuration;
            notifyObservers(o->o.onTickUpdate(++actualTick));
        }
    }

    // interface Observable

    @Override
    public ObserverManager<TickSensitive> getObserverManager(){
        return this.observerManager;
    }
    
}