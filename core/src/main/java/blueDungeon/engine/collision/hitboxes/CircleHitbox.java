package blueDungeon.engine.collision.hitboxes;

import blueDungeon.engine.collision.CollisionDetector;

import java.util.Objects;

/**
 * Represents a circle hitbox
 * The {@code x} and {@code y} coordinates represent the center of the circle.
 *
 * @author mimron
 */
public class CircleHitbox extends Hitbox {
    private final int radius;

    /**
     * Creates a circle hitbox.
     *
     * @param x the x coordinate of the circle's center
     * @param y the y coordinate of the circle's center
     * @param radius the radius of the circle
     *
     * @throws IllegalArgumentException if radius is not positive
     */
    public CircleHitbox(int x, int y, int radius) {
        super(x, y);

        if (radius <= 0) {
            throw new IllegalArgumentException(
                "Circle hitbox's radius must be greater than 0: " + radius
            );
        }

        this.radius = radius;
    }

    public int getRadius() {
        return radius;
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
        return other.collidesWithCircle(this);
    }

    /**
     * Checks if the specified circle hitbox collides with this circle hitbox.
     *
     * @param circle the circle hitbox to test against
     * @return {@code true} if the hitboxes collide, {@code false} otherwise
     */
    @Override
    protected boolean collidesWithCircle(CircleHitbox circle) {
        return CollisionDetector.collidesWith(this, circle);
    }

    /**
     * Checks whether the specified rectangular hitbox collides with this circle hitbox.
     *
     * @param rectangle the rectangular hitbox to test against
     * @return {@code true} if the hitboxes collide, {@code false} otherwise
     */
    @Override
    protected boolean collidesWithRectangle(RectangleHitbox rectangle) {
        return CollisionDetector.collidesWith(this, rectangle);
    }
}
