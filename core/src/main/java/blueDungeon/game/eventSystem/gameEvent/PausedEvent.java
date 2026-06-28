package blueDungeon.game.eventSystem.gameEvent;

/**
 * Evenement qui signal l'état de la mise en pause du jeu.
 * Permet de savoir si l'evement vient d'une source automatique ou manuel.
 * @author Galexis
 */
public class PausedEvent implements GameEvent {

    private boolean pausedValue;
    private boolean isManualAction;

    /**
     * @param pausedValue l'état de pause vers le quel basculer.
     * @param isManualAction précise si l'evement vient d'une source manuel ou non.
     */
    public PausedEvent(boolean pausedValue, boolean isManualAction){
        this.pausedValue = pausedValue;
        this.isManualAction = isManualAction;
    }

    public boolean getPausedValue(){
        return pausedValue;
    }

    public boolean isManualAction(){
        return isManualAction;
    }
}
