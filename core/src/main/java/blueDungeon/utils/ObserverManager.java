package blueDungeon.utils;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Classe permettant de créer des observers rapidement
 * @author Galexis
 */
public class ObserverManager<T> {

    private final ArrayList<T> observers = new ArrayList<>();

    /**
     * Ajoute un observer.
     * @param observer
     */
    public void addObserver(T observer){
        observers.add(observer);
    }

    /**
     * Retire la première occurance de l'oberver.
     * @param observer
     */
    public void removeObserver(T observer){
        observers.remove(observer);
    }

    /**
     * Appèle un callBack (une methode de l'observer) sur tous les observers
     * @param callBack
     */
    public void notifyObservers(Consumer<T> callBack){
        for (T observer : new ArrayList<>(observers)) {
            callBack.accept(observer);
        }
    }
    
}
