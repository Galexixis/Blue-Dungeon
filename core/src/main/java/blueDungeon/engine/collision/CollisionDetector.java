package blueDungeon.engine.collision;

import blueDungeon.engine.collision.hitboxes.RectangleHitbox;
import blueDungeon.engine.collision.hitboxes.CircleHitbox;

/**
 * Static class containing static functions to detect collisions between hitboxes
 *
 * @author mimron
 */
public class CollisionDetector {
    private CollisionDetector() {
    }

    /**
     * Checks if a circle hitbox collides with a rectangular hitbox.
     *
     * @param rectangle the rectangular hitbox to test
     * @param circle    the circle hitbox to test
     * @return {@code true} if the circle collides with the rectangle,
     * {@code false} otherwise
     */
    public static boolean collidesWith(RectangleHitbox rectangle, CircleHitbox circle) {
        return CollisionDetector.collidesWith(circle, rectangle);
    }

    /**
     * Checks if a circle hitbox collides with a rectangular hitbox.
     *
     * @param circle    the circle hitbox to test
     * @param rectangle the rectangular hitbox to test
     * @return {@code true} if the circle collides with the rectangle,
     * {@code false} otherwise
     */
    public static boolean collidesWith(CircleHitbox circle, RectangleHitbox rectangle) {
        int closestX = clamp(circle.getX(), rectangle.getX(), rectangle.getX() + rectangle.getWidth());
        int closestY = clamp(circle.getY(), rectangle.getY(), rectangle.getY() + rectangle.getHeight());

        int dx = circle.getX() - closestX;
        int dy = circle.getY() - closestY;

        return dx * dx + dy * dy <= circle.getRadius() * circle.getRadius();
    }

    /**
     * Checks if two circle hitboxes collide.
     *
     * @param circle1 the first circle hitbox
     * @param circle2 the second circle hitbox
     * @return {@code true} if the circles collide,
     * {@code false} otherwise
     */
    public static boolean collidesWith(CircleHitbox circle1, CircleHitbox circle2) {
        int dx = circle1.getX() - circle2.getX();
        int dy = circle1.getY() - circle2.getY();
        int radiusSum = circle1.getRadius() + circle2.getRadius();

        return dx * dx + dy * dy <= radiusSum * radiusSum;
    }

    /**
     * Checks whether two rectangular hitboxes collide.
     *
     * @param rectangle1 the first rectangular hitbox
     * @param rectangle2 the second rectangular hitbox
     * @return {@code true} if the rectangles collide,
     * {@code false} otherwise
     */
    public static boolean collidesWith(RectangleHitbox rectangle1, RectangleHitbox rectangle2) {
        return rectangle1.getX() <= rectangle2.getX() + rectangle2.getWidth()
            && rectangle1.getX() + rectangle1.getWidth() >= rectangle2.getX()
            && rectangle1.getY() <= rectangle2.getY() + rectangle2.getHeight()
            && rectangle1.getY() + rectangle1.getHeight() >= rectangle2.getY();
    }

    /**
     * returns the given value clamped between the specified min and max.
     *
     * @param value the value to clamp
     * @param min the minimum allowed value
     * @param max the maximum allowed value
     * @return {@code value} if it is between {@code min} and {@code max},
     *         {@code min} if {@code value} is less than {@code min},
     *         or {@code max} if {@code value} is greater than {@code max}
     */
    private static int clamp(int value, int min, int max) {
        // si (min <= value <= max)
        //      retourne value
        // Sinon si (value < min)
        //      retourne min
        // Sinon retourne max
        return Math.max(min, Math.min(max, value));
    }
}
