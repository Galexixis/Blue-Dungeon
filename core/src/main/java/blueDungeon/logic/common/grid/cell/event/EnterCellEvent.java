package blueDungeon.logic.common.grid.cell.event;

import blueDungeon.logic.common.Entity;
import blueDungeon.logic.common.grid.cell.component.CellComponent;

/**
 * Représente l'entré d'une entité dans une case.
 * @author Galexis
 */
public class EnterCellEvent implements CellEvent {

    private final Entity entity;

    /**
     * Crée un evenement d'entrè dans une case.
     * @param entity
     */
    public EnterCellEvent(Entity entity){
        this.entity = entity;
    }

    public Entity getEntity(){
        return entity;
    }

    @Override
    public void dispatch(CellComponent cellComponent, CellEventContext context) {
        cellComponent.onEnter(this, context);
    }

}