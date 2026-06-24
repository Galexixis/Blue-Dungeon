package blueDungeon.logic.common;

import blueDungeon.engine.WorldPosition;

/**
 * @author Galexis
 */
public class Grid {
    
    //Largeur de la grille
    public final int width;
    //Hauteur de largeur de la grille (différent de la hauteur d'une case)
    public final int height;
    //Double tableau de cases
    private final Cell[][] cells;

    /**
     * Contructeur basic d'une grille.
     * Toutes case sont initilalisées vides.
     * @param width largeur de la grille
     * @param height hauteur de la grille
     */
    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];

        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                cells[x][y] = Cell.createVoidCell(x, y);
            }
        }
    }

    /**
     * Peut aussi être obtenue sans passer par le getter (ex : gridInstance.width)
     * @return int
     */
    public int getWidth(){
        return width;
    }

    /**
     * Peut aussi être obtenue sans passer par le getter (ex : gridInstance.height)
     * @return int
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * Utilise des coordonnées discrète
     * @param x
     * @param y
     * @return Cell qui sera de type VOID si les coordonnées sont invalides
     */
    public Cell getCell(int x, int y){
        if(! isInGrid(x,y)){
            return Cell.createVoidCell(x, y);
        }
        return cells[x][y];
    }

    /**
     * Utilise des coordonnées continue
     * @param worldPosition
     * @return Cell
     */
    public Cell getCell(WorldPosition worldPosition){
        // TODO (Alexis) Faire cette methode losque worldPosition sera codée
        return getCell(0, 0);
    }

    /**
     * Permet de savoir si les coordonnées discrettes sont valides dans cette grille.
     * @param x
     * @param y
     * @return boolean
     */
    public boolean isInGrid(int x, int y){
        return (x >= 0 & y >= 0 & x < width & y < height);
    }

    /**
     * Permet de savoir si les coordonnées continues sont valides dans cette grille.
     * @param worldPosition
     * @return boolean
     */
    public boolean isInGrid(WorldPosition worldPosition){
        // TODO (Alexis) faire la conversion lorsque worldPosition sera créer
        // via une methode dans la classe grid à créée
        return isInGrid(0,0);
    }

}