package blueDungeon.engine.collision.hitboxes;

import blueDungeon.engine.collision.CollisionDetector;

import java.util.Objects;

/**
 * Represents a rectangular hitbox.
 * The {@code x} and {@code y} coordinates represent the top-left corner of the
 * rectangle.
 *
 * @author mimron
 */
public class RectangleHitbox extends Hitbox {
    private final float width;
    private final float height;

    /**
     * Creates a rectangular hitbox.
     *
     * @param x      the x-coordinate of the rectangle's top-left corner
     * @param y      the y-coordinate of the rectangle's top-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     *
     * @throws IllegalArgumentException if width or height is not positive
     */
    public RectangleHitbox(float x, float y, float width, float height) {
        super(x, y);

        if (width <= 0) {
            throw new IllegalArgumentException(
                "Rectangle hitbox's width must be greater than 0: " + width
            );
        }

        if (height <= 0) {
            throw new IllegalArgumentException(
                "Rectangle hitbox's height must be greater than 0: " + height
            );
        }

        this.width = width;
        this.height = height;
    }

    /**
     * @return the x coordinate of the center
     */
    public float getCenterX() {
        return this.getX() + this.getWidth() / 2;
    }

    /**
     * @return the y coordinate of the center
     */
    public float getCenterY() {
        return this.getY() + this.getHeight() / 2;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
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
    @Override
    public boolean collidesWith(Hitbox other) {
        Objects.requireNonNull(other, "Other hitbox cannot be null");
        return other.collidesWithRectangle(this);
    }

    /**
     * Checks if the specified circle hitbox collides with this rectangular hitbox.
     *
     * @param circle the circle hitbox to test against
     * @return {@code true} if the hitboxes collide, {@code false} otherwise
     */
    @Override
    protected boolean collidesWithCircle(CircleHitbox circle) {
        return CollisionDetector.collidesWith(circle, this);
    }

    /**
     * Checks if the specified rectangular hitbox collides with this rectangular hitbox.
     *
     * @param rectangle the rectangular hitbox to test against
     * @return {@code true} if the hitboxes collide, {@code false} otherwise
     */
    @Override
    protected boolean collidesWithRectangle(RectangleHitbox rectangle) {
        return CollisionDetector.collidesWith(this, rectangle);
    }
}
