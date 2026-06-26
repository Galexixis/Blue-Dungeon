package blueDungeon.logic.common.grid.cell.event;

import blueDungeon.logic.common.grid.cell.component.CellComponent;

/**
 * CellEvent modélise des evenments utilisé par les composants d'une cellule.
 * @author Galexis
 */
public interface CellEvent {

    /**
     * Effectue le double-dispatch : l'événement appelle sur le composant
    * la méthode correspondant à son type (ex : onEnter, onLeave , ...)
     * @param cellComponent
     * @param cellEventContext
     */
    public void dispatch(CellComponent cellComponent, CellEventContext cellEventContext);
    
}
