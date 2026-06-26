package grid.cell.Component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blueDungeon.logic.common.grid.cell.Cell;
import blueDungeon.logic.common.grid.cell.CellPriority;
import blueDungeon.logic.common.grid.cell.CellType;
import blueDungeon.logic.common.grid.cell.component.CellComponent;
import blueDungeon.logic.common.grid.cell.event.CellEvent;
import blueDungeon.logic.common.grid.cell.event.CellEventContext;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Galexis
 */
public class ComponentTest {

    private Cell cell;

    /**
     * Composant de test qui sert juste à tracer l'ordre des events
     */
    private class TestComponent implements CellComponent{

        private final CellPriority priority;

        public TestComponent(CellPriority priority){
            this.priority=priority;
        }

        @Override
        public CellPriority getPriority() {
            return this.priority;
        }
        
    }

    /**
     * Evenement qui sert à récupéré l'ordre des composant par le quel il passe
     */
    private class TraceCellEvent implements CellEvent{

        private ArrayList<CellComponent> componentOrder = new ArrayList<>();

        @Override
        public void dispatch(CellComponent cellComponent, CellEventContext cellEventContext) {
            componentOrder.add(cellComponent);
        }

        public ArrayList<CellComponent> getComponentOrder() {
            return componentOrder;
        }
    }

    /**
     * Evenement qui s'annule lorsqu'il passe sur un composant de la priorité importante
     */
    private class CancelCellEvent implements CellEvent{

        private ArrayList<CellComponent> componentOrder = new ArrayList<>();

        @Override
        public void dispatch(CellComponent cellComponent, CellEventContext cellEventContext) {
            componentOrder.add(cellComponent);
            if(cellComponent.getPriority() == CellPriority.IMPORTANT){
                cellEventContext.cancel();
            }
        }

        public ArrayList<CellComponent> getComponentOrder() {
            return componentOrder;
        }
    }

    @BeforeEach
    private void init(){
        this.cell = new Cell(0, 0, CellType.GROUND);
    }

    @Test
    /**
     * Test si les composants sont dans l'ordre prioritaire
     */
    void orderTest(){
        ArrayList<CellPriority> testedOrder = new ArrayList<>();

        cell.addComponent(new TestComponent(CellPriority.DEFAULT));
        cell.addComponent(new TestComponent(CellPriority.ABSOLUTE));
        cell.addComponent(new TestComponent(CellPriority.LAST));
        cell.addComponent(new TestComponent(CellPriority.IMPORTANT));

        TraceCellEvent traceEvent = new TraceCellEvent();
        cell.sendEvent(traceEvent);

        for(CellComponent component : traceEvent.getComponentOrder()){
            testedOrder.add(component.getPriority());
        }

        List<CellPriority> expectedOrder = List.of(
            CellPriority.ABSOLUTE,
            CellPriority.IMPORTANT,
            CellPriority.DEFAULT,
            CellPriority.LAST
        );
        assertEquals(expectedOrder, testedOrder);
    }

    @Test
    /**
     * Test l'annulation dans les contexts.
     */
    void cancelTest(){
        ArrayList<CellPriority> testedOrder = new ArrayList<>();

        cell.addComponent(new TestComponent(CellPriority.ABSOLUTE));
        cell.addComponent(new TestComponent(CellPriority.IMPORTANT));
        cell.addComponent(new TestComponent(CellPriority.DEFAULT));

        CancelCellEvent cancelEnvent = new CancelCellEvent();
        boolean valideEnvent = cell.sendEvent(cancelEnvent);

        assertFalse(valideEnvent);

        for(CellComponent component : cancelEnvent.getComponentOrder()){
            testedOrder.add(component.getPriority());
        }

        List<CellPriority> expectedOrder = List.of(
            CellPriority.ABSOLUTE,
            CellPriority.IMPORTANT
        );

        assertEquals(expectedOrder, testedOrder);

    }
}