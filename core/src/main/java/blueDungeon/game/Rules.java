package blueDungeon.game;

/**
 * @author Galexis
 */
public class Rules {

    /** Taille d'une tuile en unité logic, 1 unité logic = 1 pixel avant la mise à l'échel par le gui*/
    private final int TILE_SIZE = 64;

    /** Le nombre de tick qui s'écoule en 1 seconde.*/
    private final float TICK_RATE = 60;

    /** Définit une longeur / hauteur maximal que peut avoir une grille */
    private final int MAX_GRID_SIZE = 256;

    public int getTileSize(){
        return TILE_SIZE;
    }

    public float getTickRate(){
        return TICK_RATE;
    }

    public int getMAX_GRID_SIZE(){
        return MAX_GRID_SIZE;
    }
    
}
