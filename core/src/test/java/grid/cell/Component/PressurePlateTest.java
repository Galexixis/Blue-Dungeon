package grid.cell.Component;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blueDungeon.game.eventSystem.EventBus;
import blueDungeon.logic.common.Entity;
import blueDungeon.logic.common.grid.cell.Cell;
import blueDungeon.logic.common.grid.cell.CellType;
import blueDungeon.logic.common.grid.cell.component.DoorComponent;
import blueDungeon.logic.common.grid.cell.component.PressurePlateComponent;

/**
 * Teste le composant PressurePlateComponent et son intégration avec DoorComponent
 * via un signal relayé par le bus d'événements (les deux cases partagent le même bus).
 *
 * @author Romain Vandooren
 */
public class PressurePlateTest {

    private static final String CHANNEL = "door-1";

    private Cell plateCell;
    private Cell doorCell;
    private PressurePlateComponent pressurePlate;
    private DoorComponent door;
    private Entity dummyEntity1;
    private Entity dummyEntity2;

    private static class DummyEntity implements Entity {}

    @BeforeEach
    void init() {
        EventBus eventBus = new EventBus();
        plateCell = new Cell(1, 1, CellType.GROUND, false, eventBus);
        doorCell = new Cell(1, 2, CellType.GROUND, false, eventBus);

        pressurePlate = new PressurePlateComponent(CHANNEL);
        door = new DoorComponent(CHANNEL);

        plateCell.addComponent(pressurePlate);
        doorCell.addComponent(door);

        dummyEntity1 = new DummyEntity();
        dummyEntity2 = new DummyEntity();
    }

    @Test
    void testPressurePlatePressAndRelease() {
        assertFalse(pressurePlate.isPressed());
        assertEquals(0, pressurePlate.getEntityCount());

        // L'entité 1 entre
        assertTrue(plateCell.enter(dummyEntity1));
        assertTrue(pressurePlate.isPressed());
        assertEquals(1, pressurePlate.getEntityCount());

        // L'entité 2 entre
        assertTrue(plateCell.enter(dummyEntity2));
        assertTrue(pressurePlate.isPressed());
        assertEquals(2, pressurePlate.getEntityCount());

        // L'entité 1 sort
        assertTrue(plateCell.leave(dummyEntity1));
        assertTrue(pressurePlate.isPressed());
        assertEquals(1, pressurePlate.getEntityCount());

        // L'entité 2 sort
        assertTrue(plateCell.leave(dummyEntity2));
        assertFalse(pressurePlate.isPressed());
        assertEquals(0, pressurePlate.getEntityCount());
    }

    @Test
    void testPressurePlateDoorConnection() {
        // La porte est fermée au départ
        assertFalse(door.isOpen());
        assertFalse(doorCell.enter(dummyEntity1)); // Impossible d'entrer dans la cellule de la porte fermée

        // L'entité marche sur la plaque
        assertTrue(plateCell.enter(dummyEntity1));
        assertTrue(door.isOpen()); // La porte doit s'ouvrir
        assertTrue(doorCell.enter(dummyEntity2)); // DummyEntity2 peut maintenant entrer dans la cellule de la porte

        // L'entité quitte la plaque
        assertTrue(plateCell.leave(dummyEntity1));
        assertFalse(door.isOpen()); // La porte doit se fermer
        assertFalse(doorCell.enter(dummyEntity2)); // Impossible d'entrer de nouveau
    }

    @Test
    void testCustomSignalListener() {
        final boolean[] state = {false};
        doorCell.listenSignal(CHANNEL, value -> state[0] = value);

        assertFalse(state[0]);
        plateCell.enter(dummyEntity1);
        assertTrue(state[0]);
        plateCell.leave(dummyEntity1);
        assertFalse(state[0]);
    }
}
