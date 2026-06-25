package blueDungeon.logic.common.grid.cell.event;

import blueDungeon.logic.common.grid.cell.component.CellComponent;

/**
 * CellEvent modélise des evenments utilisé par les composants d'une cellule.
 * @author Galexis
 */
public interface CellEvent {

    /**
     * Appèle la ou les methode(s) sur un composant de cellule.
     * @param cellComponent
     * @param cellEventContext
     */
    public void dispatch(CellComponent cellComponent, CellEventContext cellEventContext);
    
}
