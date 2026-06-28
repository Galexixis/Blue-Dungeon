package blueDungeon.logic.common.grid.cell.component;

import blueDungeon.logic.common.grid.cell.Cell;
import blueDungeon.logic.common.grid.cell.CellPriority;
import blueDungeon.logic.common.grid.cell.event.CellEventContext;
import blueDungeon.logic.common.grid.cell.event.EnterCellEvent;

/**
 * Porte qui s'ouvre/se ferme au gré des signaux reçus sur son canal
 * (plaque de pression, levier, bouton...), sans connaitre la source du signal.
 *
 * @author Galexis
 */
public class DoorComponent implements CellComponent {

    private boolean open = false;
    private static final CellPriority PRIORITY = CellPriority.IMPORTANT;
    private final String channel;

    /**
     * @param channel le canal écouté pour piloter l'ouverture/fermeture de la porte.
     */
    public DoorComponent(String channel){
        this.channel = channel;
    }

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
    public void onAttach(Cell cell){
        cell.listenSignal(channel, this::setOpen);
    }

    @Override
    public void onEnter(EnterCellEvent enterCellEvent, CellEventContext context){
        // Si la porte est fermé, alors l'entité ne peux pas entrer dans la case.
        if(! open){
            context.cancel();
        }
    }

}