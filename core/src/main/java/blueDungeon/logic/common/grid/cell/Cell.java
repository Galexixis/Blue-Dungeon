package blueDungeon.logic.common.grid.cell;

import java.util.ArrayList;
import java.util.List;

import blueDungeon.logic.common.entity.CellEntity;
import blueDungeon.logic.common.grid.cell.component.CellComponent;
import blueDungeon.logic.common.grid.cell.event.CellEvent;
import blueDungeon.logic.common.grid.cell.event.CellEventContext;
import blueDungeon.logic.common.grid.cell.event.EnterCellEvent;

/**
 * @author Galexis
 */
public class Cell {

    public final int x;
    public final int y;

    /** Défine si la case rejette les entités. Modifiable dynamiquement (ex : une porte) */
    private boolean isBlocking;
    private CellType cellType;

    private final List<CellComponent> componentsList = new ArrayList<>();

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
     * Ajoute un composant à la case et appèle son callback 'onAttach'
     * @param component
     */
    public void addComponent(CellComponent component){
        componentsList.add(component);
        //Trie la liste sur base des priorités
        componentsList.sort((a, b) ->
            Integer.compare(b.getPriority().getValue(), a.getPriority().getValue())
        );
        component.onAttach(this);
    }

    /**
     * Retire un composant de la case et appèle son callback 'onDetach'
     * @param component
     */
    public void removeComponent(CellComponent component){
        componentsList.remove(component);
        component.onDetach(this);
    }

    /**
     * Fait entré si possible l'entité dans la cellule.
     * @param cellEntity
     * @return un boollean indiquant si l'entité est rentré dans la cellule.
     */
    public boolean enter(CellEntity cellEntity){
        //Si la case refuse l'entré
        if(isBlocking()){
            return false;
        }
        //Envoit de l'evenement qui peut être annulé
        EnterCellEvent enterCellEvent = new EnterCellEvent(cellEntity);
        return sendEvent(enterCellEvent);
    }

    /**
     * Envoit un evenement à tous les composants dans l'ordre de priorité le tous dans un context.
     * @param event
     * @return true si l'événement n'a pas été annulé, false sinon.
     */
    public boolean sendEvent(CellEvent event){
        CellEventContext context = new CellEventContext();
        //Envoit récursivement l'evenement tant qu'il n'est pas annulé
        for(CellComponent component : componentsList){
            event.dispatch(component, context);
            if(context.isCancelled()){
                break;
            }
        }
        return ! context.isCancelled();
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
