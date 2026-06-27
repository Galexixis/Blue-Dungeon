package blueDungeon.logic.common.grid.cell.component;

import java.util.ArrayList;
import java.util.List;

import blueDungeon.logic.common.Entity;
import blueDungeon.logic.common.grid.cell.CellPriority;
import blueDungeon.logic.common.grid.cell.event.CellEventContext;
import blueDungeon.logic.common.grid.cell.event.EnterCellEvent;
import blueDungeon.logic.common.grid.cell.event.LeaveCellEvent;
import blueDungeon.utils.Observable;
import blueDungeon.utils.ObserverManager;

/**
 * Composant plaque de pression.
 * S'active lorsqu'une ou plusieurs entités sont dessus.
 * Se désactive quand toutes les entités sont parties.
 * Est observable.
 * 
 * @author Romain Vandooren
 */
public class PressurePlateComponent implements CellComponent, Observable<PressurePlateObserver> {

    private final ObserverManager<PressurePlateObserver> observerManager = new ObserverManager<>();
    private final List<Entity> entitiesOnPlate = new ArrayList<>();
    private static final CellPriority PRIORITY = CellPriority.DEFAULT;

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
    public void onEnter(EnterCellEvent enterCellEvent, CellEventContext context) {
        Entity entity = enterCellEvent.getEntity();
        if (entity != null && !entitiesOnPlate.contains(entity)) {
            boolean wasPressed = isPressed();
            entitiesOnPlate.add(entity);
            if (!wasPressed) {
                notifyObservers(observer -> observer.onPressurePlatePressed(this));
            }
        }
    }

    @Override
    public void onLeave(LeaveCellEvent leaveCellEvent, CellEventContext context) {
        Entity entity = leaveCellEvent.getEntity();
        if (entity != null && entitiesOnPlate.contains(entity)) {
            entitiesOnPlate.remove(entity);
            if (!isPressed()) {
                notifyObservers(observer -> observer.onPressurePlateReleased(this));
            }
        }
    }

    @Override
    public ObserverManager<PressurePlateObserver> getObserverManager() {
        return observerManager;
    }
}
