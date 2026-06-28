package blueDungeon.logic.common.entity;

import blueDungeon.engine.collision.hitboxes.RectangleHitbox;
import blueDungeon.engine.entity.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests health management and capabilities of Logic entities.
 *
 * @author mimron
 */
public class EntityTest {

    /**
     * Checks that an entity starts with its maximum health.
     */
    @Test
    void initializesHealth() {
        TestEntity entity = new TestEntity(100);

        assertEquals(100, entity.getHealth());
        assertEquals(100, entity.getMaxHealth());
        assertTrue(entity.isAlive());
    }

    /**
     * Checks that an entity exposes its required capabilities.
     */
    @Test
    void implementsRequiredCapabilities() {
        TestEntity entity = new TestEntity(100);

        assertInstanceOf(CellEntity.class, entity);
        assertInstanceOf(Damageable.class, entity);
    }

    /**
     * Checks that the constructor rejects invalid maximum health values.
     */
    @Test
    void constructorRejectsInvalidMaxHealth() {
        IllegalArgumentException zeroHealthException = assertThrows(
            IllegalArgumentException.class,
            () -> new TestEntity(0)
        );
        IllegalArgumentException negativeHealthException = assertThrows(
            IllegalArgumentException.class,
            () -> new TestEntity(-1)
        );

        assertEquals(
            "Max health must be greater than 0: 0",
            zeroHealthException.getMessage()
        );
        assertEquals(
            "Max health must be greater than 0: -1",
            negativeHealthException.getMessage()
        );
    }

    /**
     * Checks that health remains between zero and maximum health.
     */
    @Test
    void limitsHealthToValidRange() {
        TestEntity entity = new TestEntity(100);

        entity.setHealth(60);
        assertEquals(60, entity.getHealth());

        entity.setHealth(150);
        assertEquals(100, entity.getHealth());

        entity.setHealth(-10);
        assertEquals(0, entity.getHealth());
        assertFalse(entity.isAlive());
    }

    /**
     * Checks that changing maximum health preserves valid current health.
     */
    @Test
    void updatesMaxHealth() {
        TestEntity entity = new TestEntity(100);
        entity.setHealth(80);

        entity.setMaxHealth(50);

        assertEquals(50, entity.getMaxHealth());
        assertEquals(50, entity.getHealth());

        entity.setMaxHealth(120);

        assertEquals(120, entity.getMaxHealth());
        assertEquals(50, entity.getHealth());
    }

    /**
     * Checks that invalid maximum health updates are rejected.
     */
    @Test
    void rejectsInvalidMaxHealthUpdate() {
        TestEntity entity = new TestEntity(100);

        IllegalArgumentException zeroHealthException = assertThrows(
            IllegalArgumentException.class,
            () -> entity.setMaxHealth(0)
        );
        IllegalArgumentException negativeHealthException = assertThrows(
            IllegalArgumentException.class,
            () -> entity.setMaxHealth(-1)
        );

        assertEquals(
            "Max health must be greater than 0: 0",
            zeroHealthException.getMessage()
        );
        assertEquals(
            "Max health must be greater than 0: -1",
            negativeHealthException.getMessage()
        );
        assertEquals(100, entity.getMaxHealth());
    }

    /**
     * Checks that damage reduces health without making it negative.
     */
    @Test
    void receivesDamage() {
        TestEntity entity = new TestEntity(100);

        entity.receiveDamage(30);

        assertEquals(70, entity.getHealth());
        assertTrue(entity.isAlive());

        entity.receiveDamage(100);

        assertEquals(0, entity.getHealth());
        assertFalse(entity.isAlive());
    }

    /**
     * Checks zero and negative damage handling.
     */
    @Test
    void validatesDamage() {
        TestEntity entity = new TestEntity(100);

        entity.receiveDamage(0);
        assertEquals(100, entity.getHealth());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> entity.receiveDamage(-1)
        );

        assertEquals("Damage cannot be negative: -1", exception.getMessage());
        assertEquals(100, entity.getHealth());
    }

    /**
     * Concrete entity used to test the abstract Logic entity.
     *
     * @author mimron
     */
    private static final class TestEntity extends Entity {

        /**
         * Creates a test entity with the specified maximum health.
         *
         * @param maxHealth the entity maximum health
         */
        private TestEntity(int maxHealth) {
            super(
                1.5f,
                -2.5f,
                maxHealth,
                Direction.NORTH,
                new RectangleHitbox(0, 0, 1, 1)
            );
        }
    }
}
