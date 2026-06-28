package blueDungeon.logic.common.entity;

/**
 * Represents an entity that can receive damage.
 *
 * @author mimron
 */
public interface Damageable {

    /**
     * Inflicts damage on this entity.
     *
     * @param damage the amount of damage
     * @throws IllegalArgumentException if damage is negative
     */
    void receiveDamage(int damage);

    /**
     * @return {@code true} if entity's health is > 0,
     * {@code false} otherwise
     */
    boolean isAlive();
}
