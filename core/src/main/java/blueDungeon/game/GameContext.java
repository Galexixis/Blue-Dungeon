package blueDungeon.game;

import blueDungeon.engine.timer.PhysiqueTimer;
import blueDungeon.game.eventSystem.EventBus;
import blueDungeon.logic.common.grid.Grid;
import blueDungeon.logic.common.time.TickTimer;

/**
 * Class qui initialise les élément du jeu.
 * @author Galexis
 */
public class GameContext {

    // Element neutre
    public final EventBus eventBus;
    public final Rules rules;

    // Element engine
    public final PhysiqueTimer physiqueTimer;

    // Element logic
    public final TickTimer tickTimer;
    public final Grid grid;

    public GameContext(){

        // Element neutre
        this.eventBus = new EventBus();
        this.rules = new Rules();

        // Element engine
        this.physiqueTimer = new PhysiqueTimer();

        // Element logic
        this.tickTimer = new TickTimer(this.rules.getTickRate());

        // Lisaison de données
        this.physiqueTimer.addObserver(tickTimer);

    }
    
}
