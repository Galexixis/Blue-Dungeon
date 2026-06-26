package blueDungeon.utils;

import java.util.function.Consumer;

/**
 * Interface permettant de créer des observers rapidement
 * @author Galexis
 */
public interface Observable<T> {

    ObserverManager<T> getObserverManager();

    /**
     * Ajoute un observer.
     * @param observer
     */
    default void addObserver(T observer){
        getObserverManager().addObserver(observer);
    }

    /**
     * Retire la première occurance de l'oberver.
     * @param observer
     */
    default void removeObserver(T observer){
        getObserverManager().removeObserver(observer);
    }

    /**
     * Appèle un callBack (une methode de l'observer) sur tous les observers
     * @param callBack
     */
    default void notifyObservers(Consumer<T> callBack){
        getObserverManager().notifyObservers(callBack);
    }
    
}
