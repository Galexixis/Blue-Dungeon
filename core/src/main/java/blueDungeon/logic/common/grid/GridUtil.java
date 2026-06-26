package blueDungeon.logic.common.grid;

/**
 * @author Galexis
 */
public class GridUtil {
    
    /**
     * Retourne le plus grand entier inférieur à la valeur donné en argument.
     * (ex : 3.9 -> 3, -0.1 -> -1)
     * @param pos
     * @return int
     */
    public static int floor(float pos){
        return (int)Math.floor(pos);
    }
}
