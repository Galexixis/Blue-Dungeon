package blueDungeon.logic.common.grid.cell.event;

/**
 * Représente un context dans le quel des evenements se propage et peuvent être annulée.
 * @author Galexis
 */
public class CellEventContext {

    private boolean cancelled = false;

    /**
     * Permet de savoir si la propagation d'evenement dans le context est annulée.
     * @return
     */
    public boolean isCancelled(){
        return cancelled;
    }
    
    /**
     * Empèche la propagation de d'évement(s) dans le context.
     */
    public void cancel(){
        this.cancelled = true;
    }
}
