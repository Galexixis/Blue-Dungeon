package blueDungeon.logic.common.grid.cell.component;

import java.util.ArrayList;
import java.util.List;

import blueDungeon.logic.common.Entity;
import blueDungeon.logic.common.grid.cell.Cell;
import blueDungeon.logic.common.grid.cell.CellPriority;
import blueDungeon.logic.common.grid.cell.event.CellEventContext;
import blueDungeon.logic.common.grid.cell.event.EnterCellEvent;
import blueDungeon.logic.common.grid.cell.event.LeaveCellEvent;

/**
 * Composant plaque de pression.
 * S'active lorsqu'une ou plusieurs entités sont dessus.
 * Se désactive quand toutes les entités sont parties.
 * Emet un signal positif/négatif sur son canal via la case, pour piloter
 * d'autres composants (porte, etc.) sans les connaitre directement.
 *
 * @author Romain Vandooren
 */
public class PressurePlateComponent implements CellComponent {

    private final List<Entity> entitiesOnPlate = new ArrayList<>();
    private static final CellPriority PRIORITY = CellPriority.DEFAULT;
    private final String channel;
    private Cell cell;

    /**
     * @param channel le canal sur lequel la plaque émet son signal.
     */
    public PressurePlateComponent(String channel){
        this.channel = channel;
    }

    public boolean isPressed() {
        return !entitiesOnPlate.isEmpty();
    }

    public int getEntityCount() {
        return entitiesOnPlate.size();
    }

    @Override
    public CellPriority getPriority() {
        return PRIORITY;
    }

    @Override
    public void onAttach(Cell cell){
        this.cell = cell;
    }

    @Override
    public void onEnter(EnterCellEvent enterCellEvent, CellEventContext context) {
        Entity entity = enterCellEvent.getEntity();
        if (entity != null && !entitiesOnPlate.contains(entity)) {
            boolean wasPressed = isPressed();
            entitiesOnPlate.add(entity);
            if (!wasPressed) {
                cell.emitSignal(channel, true);
            }
        }
    }

    @Override
    public void onLeave(LeaveCellEvent leaveCellEvent, CellEventContext context) {
        Entity entity = leaveCellEvent.getEntity();
        if (entity != null && entitiesOnPlate.contains(entity)) {
            entitiesOnPlate.remove(entity);
            if (!isPressed()) {
                cell.emitSignal(channel, false);
            }
        }
    }
}
