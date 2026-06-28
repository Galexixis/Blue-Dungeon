package blueDungeon.engine.entity;

import blueDungeon.engine.collision.hitboxes.Hitbox;
import blueDungeon.engine.collision.hitboxes.RectangleHitbox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests position, movement and collision behavior of Engine entities.
 *
 * @author mimron
 */
public class EntityTest {
    private static final float FLOAT_DELTA = 0.0001f;

    /**
     * Checks that the constructor initializes the entity and synchronizes its hitbox.
     */
    @Test
    void initializesEntityAndHitbox() {
        RectangleHitbox hitbox = new RectangleHitbox(10, 20, 2, 3);
        TestEntity entity = new TestEntity(1.5f, -2.5f, Direction.NORTH, hitbox);

        assertEquals(1.5f, entity.getX(), FLOAT_DELTA);
        assertEquals(-2.5f, entity.getY(), FLOAT_DELTA);
        assertEquals(Direction.NORTH, entity.getDirection());
        assertEquals(0, entity.getVelocityX(), FLOAT_DELTA);
        assertEquals(0, entity.getVelocityY(), FLOAT_DELTA);
        assertSame(hitbox, entity.getHitbox());
        assertEquals(1.5f, hitbox.getX(), FLOAT_DELTA);
        assertEquals(-2.5f, hitbox.getY(), FLOAT_DELTA);
    }

    /**
     * Checks that setting the entity position also moves its hitbox.
     */
    @Test
    void synchronizesHitboxWhenSettingPosition() {
        RectangleHitbox hitbox = new RectangleHitbox(0, 0, 2, 3);
        TestEntity entity = new TestEntity(0, 0, Direction.SOUTH, hitbox);

        entity.setPosition(-3.25f, 4.75f);

        assertEquals(-3.25f, entity.getX(), FLOAT_DELTA);
        assertEquals(4.75f, entity.getY(), FLOAT_DELTA);
        assertEquals(-3.25f, hitbox.getX(), FLOAT_DELTA);
        assertEquals(4.75f, hitbox.getY(), FLOAT_DELTA);
    }

    /**
     * Checks that moving an entity adds offsets to its current position.
     */
    @Test
    void movesEntityAndHitbox() {
        RectangleHitbox hitbox = new RectangleHitbox(0, 0, 2, 3);
        TestEntity entity = new TestEntity(1.5f, -2.5f, Direction.EAST, hitbox);

        entity.move(2.25f, -1.5f);

        assertEquals(3.75f, entity.getX(), FLOAT_DELTA);
        assertEquals(-4, entity.getY(), FLOAT_DELTA);
        assertEquals(3.75f, hitbox.getX(), FLOAT_DELTA);
        assertEquals(-4, hitbox.getY(), FLOAT_DELTA);
    }

    /**
     * Checks that updates apply the current velocity on every call.
     */
    @Test
    void updatesPositionUsingVelocity() {
        RectangleHitbox hitbox = new RectangleHitbox(0, 0, 2, 3);
        TestEntity entity = new TestEntity(1.5f, -2.5f, Direction.WEST, hitbox);

        entity.setVelocity(0.5f, -1.25f);
        entity.update();
        entity.update();

        assertEquals(0.5f, entity.getVelocityX(), FLOAT_DELTA);
        assertEquals(-1.25f, entity.getVelocityY(), FLOAT_DELTA);
        assertEquals(2.5f, entity.getX(), FLOAT_DELTA);
        assertEquals(-5, entity.getY(), FLOAT_DELTA);
        assertEquals(2.5f, hitbox.getX(), FLOAT_DELTA);
        assertEquals(-5, hitbox.getY(), FLOAT_DELTA);
    }

    /**
     * Checks that entity collision tests use their synchronized hitboxes.
     */
    @Test
    void detectsCollisionsUsingHitboxes() {
        TestEntity firstEntity = new TestEntity(
            0,
            0,
            Direction.EAST,
            new RectangleHitbox(0, 0, 2, 2)
        );
        TestEntity secondEntity = new TestEntity(
            5,
            0,
            Direction.WEST,
            new RectangleHitbox(0, 0, 2, 2)
        );

        assertFalse(firstEntity.collidesWith(secondEntity));
        assertFalse(secondEntity.collidesWith(firstEntity));

        secondEntity.move(-3, 0);

        assertTrue(firstEntity.collidesWith(secondEntity));
        assertTrue(secondEntity.collidesWith(firstEntity));
    }

    /**
     * test entity
     */
    private static final class TestEntity extends Entity {

        /**
         * Creates an entity for a test.
         *
         * @param x the initial x coordinate
         * @param y the initial y coordinate
         * @param direction the initial direction
         * @param hitbox the entity hitbox
         */
        private TestEntity(float x, float y, Direction direction, Hitbox hitbox) {
            super(x, y, direction, hitbox);
        }
    }
}
