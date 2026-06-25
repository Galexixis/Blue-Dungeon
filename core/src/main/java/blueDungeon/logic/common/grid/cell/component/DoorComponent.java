package blueDungeon.logic.common.grid.cell.component;

import blueDungeon.logic.common.grid.cell.event.EnterCellEvent;

/**
 * @author Galexis
 */
public class DoorComponent implements CellComponent {

    private boolean open = false;
    private static final CellPriority PRIORITY = CellPriority.IMPORTANT;

    public boolean isOpen(){
        return open;
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
    
}