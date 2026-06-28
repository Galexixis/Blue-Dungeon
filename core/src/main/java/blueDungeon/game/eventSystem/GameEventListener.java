package blueDungeon.game.eventSystem;

import blueDungeon.game.eventSystem.gameEvent.GameEvent;

/**
 * @author Galexis
 */
@FunctionalInterface
public interface GameEventListener{
    
    /**
     * A la réception d'un evenement.
     * @param event
     */
    public void onEvent(GameEvent event);
}
