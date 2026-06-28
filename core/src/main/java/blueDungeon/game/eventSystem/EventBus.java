package blueDungeon.game.eventSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import blueDungeon.game.eventSystem.gameEvent.GameEvent;
import blueDungeon.logging.Logger;

/**
 * @author Galexis
 */
public class EventBus {

    private final Map<Class<? extends GameEvent>, List<GameEventListener>> listeners = new HashMap<>();

    /**
     * Enregistre une classe qui écoute un evenement.
     * @param eventType
     * @param listener
     */
    public void register(Class<? extends GameEvent> eventType, GameEventListener listener){
        Objects.requireNonNull(eventType);
        Objects.requireNonNull(listener);
        //Ajoute l'écouteur et crée une liste s'il y en à pas.
        listeners.computeIfAbsent(eventType, e-> new ArrayList<>()).add(listener);
        Logger.debug("EventBus: listener enregistré pour " + eventType.getSimpleName());
    }

    /**
     * Désenregistre une classe qui écoute un evenement.
     * @param eventType
     * @param listener
     */
    public void unregister(Class<? extends GameEvent> eventType, GameEventListener listener){
        Objects.requireNonNull(eventType);
        Objects.requireNonNull(listener);

        List<GameEventListener> list = listeners.get(eventType);
        if (list == null) return;

        list.remove(listener);
        if (list.isEmpty()) listeners.remove(eventType);
        Logger.debug("EventBus: listener désenregistré pour " + eventType.getSimpleName());
    }

    /**
     * Envoit l'evenement à tous ceux qui l'écoute.
     * @param event
     */
    public void raiseEvent(GameEvent event){
        Objects.requireNonNull(event);

        Class<? extends GameEvent> eventType = event.getClass();
        List<GameEventListener> list = listeners.get(eventType);
        if(list == null || list.isEmpty()){
            Logger.debug("EventBus: " + eventType.getSimpleName() + " émis sans aucun listener");
            return;
        }

        var copyList = new ArrayList<>(list);
        Logger.debug("EventBus: " + eventType.getSimpleName() + " émis vers " + copyList.size() + " listener(s)");

        for(GameEventListener listener : copyList){
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                //On logue l'exeption (au lieu de l'avaler silencieusement) pour que la boucle for continue
                Logger.error("EventBus: erreur dans un listener de " + eventType.getSimpleName(), e);
            }
        }

    }
    
}