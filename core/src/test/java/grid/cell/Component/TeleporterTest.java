package grid.cell.Component;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blueDungeon.logic.common.Entity;
import blueDungeon.logic.common.grid.cell.Cell;
import blueDungeon.logic.common.grid.cell.CellType;
import blueDungeon.logic.common.grid.cell.event.CellEventContext;
import blueDungeon.logic.common.grid.cell.event.EnterCellEvent;
import blueDungeon.logic.common.grid.cell.component.CellComponent;
import blueDungeon.logic.common.grid.cell.component.TeleporterComponent;
import blueDungeon.logic.common.grid.cell.component.DoorComponent;

/**
 * Teste le composant TeleporterComponent.
 * 
 * @author Romain Vandooren
 */
public class TeleporterTest {

    private Cell cellA;
    private Cell cellB;
    private TeleporterComponent teleporterA;
    private Entity dummyEntity;
    private EnterTrackerComponent trackerB;

    private static class DummyEntity implements Entity {}

    // Composant espion pour valider les entrées effectives dans une cellule
    private static class EnterTrackerComponent implements CellComponent {
        public final List<Entity> enteredEntities = new ArrayList<>();

        @Override
        public void onEnter(EnterCellEvent event, CellEventContext context) {
            enteredEntities.add(event.getEntity());
        }
    }

    @BeforeEach
    void init() {
        cellA = new Cell(0, 0, CellType.GROUND);
        cellB = new Cell(1, 0, CellType.GROUND);

        teleporterA = new TeleporterComponent(cellB);
        cellA.addComponent(teleporterA);

        trackerB = new EnterTrackerComponent();
        cellB.addComponent(trackerB);

        dummyEntity = new DummyEntity();
    }

    @Test
    void testTeleportationBasique() {
        // L'entité tente d'entrer en A
        boolean result = cellA.enter(dummyEntity);

        // Le résultat de enter(A) doit être false (car l'entrée sur A a été annulée et redirigée vers B)
        assertFalse(result);

        // On vérifie que l'entité a bien atterri dans la cellule B
        assertEquals(1, trackerB.enteredEntities.size());
        assertEquals(dummyEntity, trackerB.enteredEntities.get(0));
    }

    @Test
    void testTeleporterInactif() {
        teleporterA.setActive(false);

        // L'entité tente d'entrer en A
        boolean result = cellA.enter(dummyEntity);

        // Le téléporteur étant inactif, l'entrée sur A réussit normalement
        assertTrue(result);

        // Aucune redirection vers B ne doit avoir eu lieu
        assertEquals(0, trackerB.enteredEntities.size());
    }

    @Test
    void testTeleportationCibleBloquee() {
        // On remplace le type de la cellule B par WALL pour bloquer l'entrée
        cellB.setCellType(CellType.WALL);

        // L'entité tente d'entrer en A
        boolean result = cellA.enter(dummyEntity);

        // Puisque B est bloqué, la redirection échoue, donc l'entrée sur A (qui est GROUND) réussit
        assertTrue(result);

        // L'entité n'a pas pu entrer en B
        assertEquals(0, trackerB.enteredEntities.size());
    }

    @Test
    void testTeleportationBoucleInfinie() {
        // On configure cellB avec un téléporteur qui renvoie vers cellA
        TeleporterComponent teleporterB = new TeleporterComponent(cellA);
        cellB.addComponent(teleporterB);

        // L'entité tente d'entrer en A (A -> B -> A -> B ...)
        // Si la protection contre les boucles infinies fonctionne, cela s'arrête sans StackOverflowError
        cellA.enter(dummyEntity);

        // Si nous arrivons ici sans StackOverflowError, le test est réussi
        assertTrue(true);
    }
}
