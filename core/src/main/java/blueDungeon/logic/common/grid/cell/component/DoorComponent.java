package blueDungeon.logic.common.grid.cell.component;

import blueDungeon.logic.common.grid.cell.CellPriority;
import blueDungeon.logic.common.grid.cell.event.CellEventContext;
import blueDungeon.logic.common.grid.cell.event.EnterCellEvent;

/**
 * @author Galexis
 */
public class DoorComponent implements CellComponent, PressurePlateObserver {

    private boolean open = false;
    private static final CellPriority PRIORITY = CellPriority.IMPORTANT;

    public boolean isOpen(){
        return open;
    }

    public void setOpen(boolean open){
        this.open = open;
    }

    @Override
    public CellPriority getPriority() {
        return PRIORITY;
    }

    @Override
    public void onEnter(EnterCellEvent enterCellEvent, CellEventContext context){
        // Si la porte est fermé, alors l'entité ne peux pas entrer dans la case.
        if(! open){
            context.cancel();
        }
    }

    @Override
    public void onPressurePlatePressed(PressurePlateComponent pressurePlate) {
        setOpen(true);
    }

    @Override
    public void onPressurePlateReleased(PressurePlateComponent pressurePlate) {
        setOpen(false);
    }
    
}