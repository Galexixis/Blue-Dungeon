package blueDungeon.engine.collision.hitboxes;

/**
 * Base class for all hitboxes
 *
 * @author mimron
 */
public abstract class Hitbox {
    private int x;
    private int y;

    /**
     * Creates a hitbox at the specified coordinates.
     *
     * @param x the x-coordinate of this hitbox
     * @param y the y-coordinate of this hitbox
     */
    public Hitbox(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Checks if this hitbox collides with the specified hitbox.
     *
     * @param other the hitbox to test against
     * @return {@code true} if this hitbox collides with the specified hitbox,
     * {@code false} otherwise
     *
     * @throws NullPointerException if other is null
     */
    public abstract boolean collidesWith(Hitbox other);

    /**
     * Checks if this hitbox collides with the specified circular hitbox.
     *
     * @param circle the circular hitbox to test against
     * @return {@code true} if this hitbox collides with the specified circular hitbox,
     * {@code false} otherwise
     */
    protected abstract boolean collidesWithCircle(CircleHitbox circle);

    /**
     * Checks if this hitbox collides with the specified rectangular hitbox.
     *
     * @param rectangle the rectangular hitbox to test against
     * @return {@code true} if this hitbox collides with the specified rectangular hitbox,
     * {@code false} otherwise
     */
    protected abstract boolean collidesWithRectangle(RectangleHitbox rectangle);
}
