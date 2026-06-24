package blueDungeon.logic.common;

/**
 * @author Galexis
 */
public class Cell {

    public final int x;
    public final int y;

    /** Défine si la case rejette les entités. Modifiable dynamiquement (ex : une porte) */
    private boolean isBlocking;
    private CellType cellType;

    /**
     * Construit une case en spécifiant tout les paramètres.
     * @param x
     * @param y
     * @param cellType
     * @param isBlocking
     */
    public Cell(int x, int y, CellType cellType, boolean isBlocking){
        this.x = x;
        this.y = y;
        this.cellType = cellType;
        this.isBlocking = isBlocking;
    }

    /**
     * Construit une case non bloquante par défaut.
     * @param x
     * @param y
     * @param cellType
     */
    public Cell(int x, int y, CellType cellType){
        this(x, y, cellType, false);
    }

    public CellType getCellType(){
        return cellType;
    }

    /**
     * Permet de savoir si la case rejette la présence d'entités.
     * @return boolean
     */
    public boolean isBlocking(){
        return this.cellType.isBlocking() || this.isBlocking;
    }

    public void setCellType(CellType cellType){
        this.cellType = cellType;
    }
    
    /**
     * Permet de (dé)bloquer la traversée des entités (n'écrase pas le blocage par les types de case).
     * @param isBlocking
     */
    public void setIsBlocking(boolean isBlocking){
        this.isBlocking = isBlocking;
    }

    /**
     * Crée une case vide.
     * @param x
     * @param y
     * @return Cell
     */
    public static Cell createVoidCell(int x, int y){
        return new Cell(x, y, CellType.VOID);
    }

    @Override
    public String toString() {
        return "Cell("+ x +";"+y+")["+cellType+"]";
    }
    
}