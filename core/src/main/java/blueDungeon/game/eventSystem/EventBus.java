package blueDungeon.game.eventSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import blueDungeon.game.eventSystem.gameEvent.GameEvent;

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
    }

    /**
     * Envoit l'evenement à tous ceux qui l'écoute.
     * @param event
     */
    public void raiseEvent(GameEvent event){
        Objects.requireNonNull(event);

        Class<? extends GameEvent> eventType = event.getClass();
        List<GameEventListener> list = listeners.get(eventType);
        if(list == null || list.isEmpty()) return;

        var copyList = new ArrayList<>(list);

        for(GameEventListener listener : copyList){
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                // On catch l'exeption pour que la boucle for continue
            }
        }

    }
    
}