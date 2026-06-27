package blueDungeon.logic.common.grid.cell.event;

import blueDungeon.logic.common.entity.CellEntity;
import blueDungeon.logic.common.grid.cell.component.CellComponent;

/**
 * Représente l'entré d'une entité dans une case.
 * @author Galexis
 */
public class EnterCellEvent implements CellEvent {

    private final CellEntity cellEntity;

    /**
     * Crée un evenement d'entrè dans une case.
     * @param cellEntity
     */
    public EnterCellEvent(CellEntity cellEntity){
        this.cellEntity = cellEntity;
    }

    public CellEntity getEntity(){
        return cellEntity;
    }

    @Override
    public void dispatch(CellComponent cellComponent, CellEventContext context) {
        cellComponent.onEnter(this, context);
    }

}
