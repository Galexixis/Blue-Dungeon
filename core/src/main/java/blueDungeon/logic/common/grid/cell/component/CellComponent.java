package blueDungeon.logic.common.grid.cell.component;

import blueDungeon.logic.common.grid.cell.Cell;
import blueDungeon.logic.common.grid.cell.CellPriority;
import blueDungeon.logic.common.grid.cell.event.CellEventContext;
import blueDungeon.logic.common.grid.cell.event.EnterCellEvent;
import blueDungeon.logic.common.grid.cell.event.LeaveCellEvent;

/**
 * @author Galexis
 */
public interface CellComponent {

    /**
     * Permet de connaitre la priortié du composant.
     * @return
     */
    default CellPriority getPriority(){
        return CellPriority.DEFAULT;
    }

    /**
     * CallBack lorsque le composant se fait attacher à une cellule.
     * (ex : une porte ou un piège (basique) ne doit pas connaitre sa position,
     * en revanche un spawneur doit connaitre sa position pour savoir où invoquer.)
     */
    default void onAttach(Cell cell){}

    /**
     * CallBack lorsque le composant se fait détacher d'une cellule.
     */
    default void onDetach(Cell cell){}
    
    // Réaction à des evenements

    /**
     * Réagis à l'entré d'une entité dans la case.
     * @param enterCellEvent
     * @param context
     */
    default void onEnter(EnterCellEvent enterCellEvent, CellEventContext context){}

    /**
     * Réagis à la sortie d'une entité de la case.
     * @param leaveCellEvent
     * @param context
     */
    default void onLeave(LeaveCellEvent leaveCellEvent, CellEventContext context){}

}
