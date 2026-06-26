package grid.cell;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blueDungeon.logic.common.grid.cell.Cell;
import blueDungeon.logic.common.grid.cell.CellType;

/**
 * @author Galexis
 */
public class CellTest {

    private Cell cell;

    @BeforeEach
    private void init(){
        cell = new Cell(0, 0, CellType.GROUND);
    }

    @Test
    /**
     * Test la methode isblocking à l'initialisation et lors de changement dynamique
     */
    void isBlockingTest(){
        
        assertFalse(cell.isBlocking(), "A l'initialisation d'une case de type ground, isBlockind doit retourné false.");

        cell.setIsBlocking(true);
        assertTrue(cell.isBlocking(), "Le changement dynamique doit changé le résultat de la methode isBlocking.");

        cell.setIsBlocking(false);
        assertFalse(cell.isBlocking());

        cell.setCellType(CellType.VOID);
        assertTrue(cell.isBlocking(), "Le type void emèche d'aller sur la case, isBlocking doit être true.");
    }

    @Test
    /**
     * Test l'entré dans la case lorsqu'il n'y a pas de composant.
     */
    void enterTest(){

        assertTrue(cell.enter(null));

        cell.setCellType(CellType.WALL);
        assertFalse(cell.enter(null));
    }
    
}
