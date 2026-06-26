package blueDungeon.logic.common;

/**
 * @author Galexis
 */
public class Rules {

    /** Taille d'une tuile en unité logic, 1 unité logic = 1 pixel avant la mise à l'échel par le gui*/
    private static final int TILE_SIZE = 64;

    /** Le nombre de tick qui s'écoule en 1 seconde.*/
    private static final float TICK_RATE = 60;

    public static int getTileSize(){
        return TILE_SIZE;
    }

    public static float getTickRate(){
        return TICK_RATE;
    }
    
}
