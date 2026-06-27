package hitboxes;

import blueDungeon.engine.collision.hitboxes.CircleHitbox;
import blueDungeon.engine.collision.hitboxes.RectangleHitbox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the validation and common behavior of hitboxes.
 *
 * @author mimron
 */
public class HitboxTest {

    /**
     * Checks that a circle radius must be strictly positive.
     */
    @Test
    void circleRejectsInvalidRadius() {
        IllegalArgumentException zeroRadiusException = assertThrows(
            IllegalArgumentException.class,
            () -> new CircleHitbox(0, 0, 0)
        );
        IllegalArgumentException negativeRadiusException = assertThrows(
            IllegalArgumentException.class,
            () -> new CircleHitbox(0, 0, -1)
        );

        assertEquals(
            "Circle hitbox's radius must be greater than 0: 0",
            zeroRadiusException.getMessage()
        );
        assertEquals(
            "Circle hitbox's radius must be greater than 0: -1",
            negativeRadiusException.getMessage()
        );
    }

    /**
     * Checks that a rectangle width must be strictly positive.
     */
    @Test
    void rectangleRejectsInvalidWidth() {
        IllegalArgumentException zeroWidthException = assertThrows(
            IllegalArgumentException.class,
            () -> new RectangleHitbox(0, 0, 0, 10)
        );
        IllegalArgumentException negativeWidthException = assertThrows(
            IllegalArgumentException.class,
            () -> new RectangleHitbox(0, 0, -1, 10)
        );

        assertEquals(
            "Rectangle hitbox's width must be greater than 0: 0",
            zeroWidthException.getMessage()
        );
        assertEquals(
            "Rectangle hitbox's width must be greater than 0: -1",
            negativeWidthException.getMessage()
        );
    }

    /**
     * Checks that a rectangle height must be strictly positive.
     */
    @Test
    void rectangleRejectsInvalidHeight() {
        IllegalArgumentException zeroHeightException = assertThrows(
            IllegalArgumentException.class,
            () -> new RectangleHitbox(0, 0, 10, 0)
        );
        IllegalArgumentException negativeHeightException = assertThrows(
            IllegalArgumentException.class,
            () -> new RectangleHitbox(0, 0, 10, -1)
        );

        assertEquals(
            "Rectangle hitbox's height must be greater than 0: 0",
            zeroHeightException.getMessage()
        );
        assertEquals(
            "Rectangle hitbox's height must be greater than 0: -1",
            negativeHeightException.getMessage()
        );
    }

    /**
     * Checks that hitbox coordinates can be updated.
     */
    @Test
    void positionCanBeUpdated() {
        CircleHitbox circle = new CircleHitbox(2, 3, 4);

        circle.setX(-5);
        circle.setY(8);

        assertEquals(-5, circle.getX());
        assertEquals(8, circle.getY());
    }

    /**
     * Checks that a collision cannot be tested against a null hitbox.
     */
    @Test
    void collisionRejectsNullHitbox() {
        CircleHitbox circle = new CircleHitbox(0, 0, 1);
        RectangleHitbox rectangle = new RectangleHitbox(0, 0, 1, 1);

        NullPointerException circleException = assertThrows(
            NullPointerException.class,
            () -> circle.collidesWith(null)
        );
        NullPointerException rectangleException = assertThrows(
            NullPointerException.class,
            () -> rectangle.collidesWith(null)
        );

        assertEquals("Other hitbox cannot be null", circleException.getMessage());
        assertEquals("Other hitbox cannot be null", rectangleException.getMessage());
    }
}
