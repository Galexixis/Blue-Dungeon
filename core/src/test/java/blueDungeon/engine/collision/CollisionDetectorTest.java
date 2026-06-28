package blueDungeon.engine.collision;

import blueDungeon.engine.collision.hitboxes.CircleHitbox;
import blueDungeon.engine.collision.hitboxes.RectangleHitbox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests collision
 *
 * @author mimron
 */
public class CollisionDetectorTest {

    /**
     * Checks overlapping, touching and separated circles.
     */
    @Test
    void detectsCircleCollisions() {
        CircleHitbox circle = new CircleHitbox(0, 0, 5);
        CircleHitbox overlappingCircle = new CircleHitbox(6, 0, 2);
        CircleHitbox touchingCircle = new CircleHitbox(8, 0, 3);
        CircleHitbox separatedCircle = new CircleHitbox(9, 0, 3);

        assertTrue(circle.collidesWith(overlappingCircle));
        assertTrue(circle.collidesWith(touchingCircle));
        assertFalse(circle.collidesWith(separatedCircle));
        assertTrue(overlappingCircle.collidesWith(circle));
        assertTrue(touchingCircle.collidesWith(circle));
        assertFalse(separatedCircle.collidesWith(circle));
    }

    /**
     * Checks overlapping, touching and separated rectangles.
     */
    @Test
    void detectsRectangleCollisions() {
        RectangleHitbox rectangle = new RectangleHitbox(0, 0, 10, 10);
        RectangleHitbox overlappingRectangle = new RectangleHitbox(5, 5, 3, 3);
        RectangleHitbox touchingRectangle = new RectangleHitbox(10, 2, 4, 4);
        RectangleHitbox separatedRectangle = new RectangleHitbox(11, 2, 4, 4);

        assertTrue(rectangle.collidesWith(overlappingRectangle));
        assertTrue(rectangle.collidesWith(touchingRectangle));
        assertFalse(rectangle.collidesWith(separatedRectangle));
        assertTrue(overlappingRectangle.collidesWith(rectangle));
        assertTrue(touchingRectangle.collidesWith(rectangle));
        assertFalse(separatedRectangle.collidesWith(rectangle));
    }

    /**
     * Checks collisions between circles and rectangles from both directions.
     */
    @Test
    void detectsCircleRectangleCollisions() {
        RectangleHitbox rectangle = new RectangleHitbox(0, 0, 10, 10);
        CircleHitbox containedCircle = new CircleHitbox(5, 5, 1);
        CircleHitbox touchingSideCircle = new CircleHitbox(15, 5, 5);
        CircleHitbox touchingCornerCircle = new CircleHitbox(13, 14, 5);
        CircleHitbox separatedCircle = new CircleHitbox(16, 5, 5);

        assertTrue(containedCircle.collidesWith(rectangle));
        assertTrue(touchingSideCircle.collidesWith(rectangle));
        assertTrue(touchingCornerCircle.collidesWith(rectangle));
        assertFalse(separatedCircle.collidesWith(rectangle));
        assertTrue(rectangle.collidesWith(containedCircle));
        assertTrue(rectangle.collidesWith(touchingSideCircle));
        assertTrue(rectangle.collidesWith(touchingCornerCircle));
        assertFalse(rectangle.collidesWith(separatedCircle));
    }

    /**
     * Checks collisions when hitboxes use negative coordinates.
     */
    @Test
    void detectsCollisionsAtNegativeCoordinates() {
        CircleHitbox circle = new CircleHitbox(-5, -5, 2);
        RectangleHitbox collidingRectangle = new RectangleHitbox(-4, -4, 3, 3);
        RectangleHitbox separatedRectangle = new RectangleHitbox(0, 0, 3, 3);

        assertTrue(circle.collidesWith(collidingRectangle));
        assertTrue(collidingRectangle.collidesWith(circle));
        assertFalse(circle.collidesWith(separatedRectangle));
        assertFalse(separatedRectangle.collidesWith(circle));
    }

    /**
     * Checks that updated coordinates are used for collision detection.
     */
    @Test
    void usesUpdatedHitboxCoordinates() {
        CircleHitbox circle = new CircleHitbox(0, 0, 2);
        RectangleHitbox rectangle = new RectangleHitbox(10, 10, 2, 2);

        assertFalse(circle.collidesWith(rectangle));

        circle.setX(10);
        circle.setY(10);

        assertTrue(circle.collidesWith(rectangle));
    }
}
