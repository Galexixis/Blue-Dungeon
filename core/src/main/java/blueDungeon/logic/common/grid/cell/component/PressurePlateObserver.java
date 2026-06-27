package blueDungeon.logic.common.grid.cell.component;

/**
 * Interface d'observation pour les plaques de pression.
 * 
 * @author Romain Vandooren
 */
public interface PressurePlateObserver {

    /**
     * Appelé lorsque la plaque de pression passe d'inactive à active.
     * @param pressurePlate la plaque de pression
     */
    void onPressurePlatePressed(PressurePlateComponent pressurePlate);

    /**
     * Appelé lorsque la plaque de pression passe d'active à inactive.
     * @param pressurePlate la plaque de pression
     */
    void onPressurePlateReleased(PressurePlateComponent pressurePlate);
}
