package blueDungeon.logic.common.grid.cell.event;

import blueDungeon.logic.common.Entity;
import blueDungeon.logic.common.grid.cell.component.CellComponent;

/**
 * Représente la sortie d'une entité d'une case.
 * @author Romain Vandooren
 */
public class LeaveCellEvent implements CellEvent {

    private final Entity entity;

    /**
     * Crée un événement de sortie d'une case.
     * @param entity
     */
    public LeaveCellEvent(Entity entity){
        this.entity = entity;
    }

    public Entity getEntity(){
        return entity;
    }

    @Override
    public void dispatch(CellComponent cellComponent, CellEventContext context) {
        cellComponent.onLeave(this, context);
    }

}
