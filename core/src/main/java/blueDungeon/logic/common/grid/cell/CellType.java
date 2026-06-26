package blueDungeon.logic.common.grid.cell;

/**
 * @author Galexis
 */
public enum CellType {

    /** Le type VOID représente une case or map sans interaction. */
    VOID(true),
    /** Le type WALL représente une case qui refuse la présence d'une enitité mais peut avoir des interactions. */
    WALL(true),
    /** Le type GROUND réprésente une case qui accepte la présence d'entités et peut avoir des interactions. */
    GROUND(false);

    /** Definie si la case rejette toute présence */
    private final boolean isBlocking;

    /** Constructeur de l'enum */
    private CellType(boolean isBlocking){
        this.isBlocking = isBlocking;
    }

    /**
     * @return boolean
     */
    public boolean isBlocking(){
        return this.isBlocking;
    }
}