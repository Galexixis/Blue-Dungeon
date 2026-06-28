package blueDungeon.logic.common.grid.cell;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import blueDungeon.game.eventSystem.EventBus;
import blueDungeon.game.eventSystem.GameEventListener;
import blueDungeon.game.eventSystem.gameEvent.SignalEvent;
import blueDungeon.logic.common.Entity;
import blueDungeon.logic.common.grid.cell.component.CellComponent;
import blueDungeon.logic.common.grid.cell.event.CellEvent;
import blueDungeon.logic.common.grid.cell.event.CellEventContext;
import blueDungeon.logic.common.grid.cell.event.EnterCellEvent;
import blueDungeon.logic.common.grid.cell.event.LeaveCellEvent;

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

    /** Bus utilisé par les composants pour émettre/écouter des signaux sur un canal, sans se connaitre entre eux. */
    private final EventBus eventBus;

    /**
     * Construit une case en spécifiant tout les paramètres.
     * @param x
     * @param y
     * @param cellType
     * @param isBlocking
     * @param eventBus bus utilisé pour relayer les signaux entre composants.
     */
    public Cell(int x, int y, CellType cellType, boolean isBlocking, EventBus eventBus){
        this.x = x;
        this.y = y;
        this.cellType = cellType;
        this.isBlocking = isBlocking;
        this.eventBus = eventBus;
    }

    /**
     * Construit une case en spécifiant tout les paramètres, avec un bus de signaux dédié.
     * @param x
     * @param y
     * @param cellType
     * @param isBlocking
     */
    public Cell(int x, int y, CellType cellType, boolean isBlocking){
        this(x, y, cellType, isBlocking, new EventBus());
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
     * @param entity
     * @return un boollean indiquant si l'entité est rentré dans la cellule.
     */
    public boolean enter(Entity entity){
        //Si la case refuse l'entré
        if(isBlocking()){
            return false;
        }
        //Envoit de l'evenement qui peut être annulé
        EnterCellEvent enterCellEvent = new EnterCellEvent(entity);
        return sendEvent(enterCellEvent);
    }

    /**
     * Fait sortir si possible l'entité de la cellule.
     * @param entity
     * @return un boolean indiquant si l'entité est sortie de la cellule.
     */
    public boolean leave(Entity entity){
        //Envoit de l'evenement qui peut être annulé
        LeaveCellEvent leaveCellEvent = new LeaveCellEvent(entity);
        return sendEvent(leaveCellEvent);
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
     * Emet un signal sur un canal. Permet à un composant (ex : une plaque de pression)
     * de communiquer avec d'autres composants sans les connaitre.
     * @param channel le canal sur lequel émettre.
     * @param value l'état du signal (true = positif, false = négatif).
     */
    public void emitSignal(String channel, boolean value){
        eventBus.raiseEvent(new SignalEvent(channel, value));
    }

    /**
     * Permet à un composant (ex : une porte) d'écouter les signaux émis sur un canal,
     * sans connaitre la source qui les émet.
     * @param channel le canal à écouter.
     * @param listener appelé avec la valeur du signal lorsqu'un signal est émis sur ce canal.
     */
    public void listenSignal(String channel, Consumer<Boolean> listener){
        GameEventListener gameEventListener = event -> {
            SignalEvent signalEvent = (SignalEvent) event;
            if(signalEvent.getChannel().equals(channel)){
                listener.accept(signalEvent.getValue());
            }
        };
        eventBus.register(SignalEvent.class, gameEventListener);
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

    /**
     * Crée une case vide partageant le bus de signaux fourni.
     * @param x
     * @param y
     * @param eventBus
     * @return Cell
     */
    public static Cell createVoidCell(int x, int y, EventBus eventBus){
        return new Cell(x, y, CellType.VOID, false, eventBus);
    }

    @Override
    public String toString() {
        return "Cell("+ x +";"+y+")["+cellType+"]";
    }
    
}