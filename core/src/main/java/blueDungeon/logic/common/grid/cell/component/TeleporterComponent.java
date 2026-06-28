package blueDungeon.logic.common.grid.cell.component;

import blueDungeon.logic.common.grid.cell.Cell;
import blueDungeon.logic.common.grid.cell.CellPriority;
import blueDungeon.logic.common.grid.cell.event.CellEventContext;
import blueDungeon.logic.common.grid.cell.event.EnterCellEvent;

/**
 * Composant téléporteur.
 * Téléporte une entité vers une cellule cible lorsqu'elle entre sur cette case.
 * Équipé d'une protection contre les boucles infinies de téléportation.
 * 
 * @author Romain Vandooren
 */
public class TeleporterComponent implements CellComponent {

    private Cell targetCell;
    private boolean active = true;
    private static final CellPriority PRIORITY = CellPriority.DEFAULT;

    // Protection contre les boucles infinies de téléportation (ex: A -> B -> A)
    private static final ThreadLocal<Integer> teleportationDepth = ThreadLocal.withInitial(() -> 0);
    private static final int MAX_TELEPORTATION_DEPTH = 5;

    /**
     * Crée un téléporteur actif sans cible initiale.
     */
    public TeleporterComponent() {
        this.targetCell = null;
    }

    /**
     * Crée un téléporteur actif avec une cellule cible.
     * @param targetCell la cellule de destination
     */
    public TeleporterComponent(Cell targetCell) {
        this.targetCell = targetCell;
    }

    public Cell getTargetCell() {
        return targetCell;
    }

    public void setTargetCell(Cell targetCell) {
        this.targetCell = targetCell;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public CellPriority getPriority() {
        return PRIORITY;
    }

    @Override
    public void onEnter(EnterCellEvent enterCellEvent, CellEventContext context) {
        if (active && targetCell != null) {
            int depth = teleportationDepth.get();
            if (depth >= MAX_TELEPORTATION_DEPTH) {
                // Arrête la téléportation si la limite de récursion est atteinte
                return;
            }
            teleportationDepth.set(depth + 1);
            try {
                // Tente de faire entrer l'entité dans la cellule cible
                boolean teleportationSucceeded = targetCell.enter(enterCellEvent.getEntity());
                if (teleportationSucceeded) {
                    // Annule l'entrée dans la cellule du téléporteur car l'entité est redirigée vers la cible
                    context.cancel();
                }
            } finally {
                teleportationDepth.set(depth);
            }
        }
    }
}
