package blueDungeon.logic.common.entity;

import blueDungeon.engine.collision.hitboxes.Hitbox;
import blueDungeon.engine.entity.Direction;

/**
 * Represents an entity managed by the Logic.
 *
 * @author mimron
 */
public class LivingEntity extends blueDungeon.engine.entity.Entity implements CellEntity, Damageable {
    private int health;
    private int maxHealth;

    /**
     * Creates a Logic entity with its Engine data and initial values.
     *
     * @param x the initial x coordinate
     * @param y the initial y coordinate
     * @param maxHealth the initial and maximum health
     * @param direction the initial direction
     * @param hitbox the hitbox attached to the entity
     *
     * @throws IllegalArgumentException if maxHealth is not positive
     */
    public LivingEntity(float x,
                        float y,
                        int maxHealth,
                        Direction direction,
                        Hitbox hitbox) {
        super(x, y, direction, hitbox);
        validateMaxHealth(maxHealth);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * @param maxHealth entity's max health
     *
     * @throws IllegalArgumentException if {@code maxHealth <= 0}
     */
    public void setMaxHealth(int maxHealth) {
        validateMaxHealth(maxHealth);
        this.maxHealth = maxHealth;
        health = Math.min(health, maxHealth);
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    /**
     * Validates a maximum health value.
     *
     * @param maxHealth the maximum health to validate
     * @throws IllegalArgumentException if maxHealth is not positive
     */
    private static void validateMaxHealth(int maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException(
                "Max health must be greater than 0: " + maxHealth
            );
        }
    }

    /**
     * @return {@code true} if health is > 0, {@code false} otherwise
     */
    @Override
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Inflicts damage on this entity.
     *
     * @param damage the amount of damage
     * @throws IllegalArgumentException if damage is negative
     */
    @Override
    public void receiveDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException(
                "Damage cannot be negative: " + damage
            );
        }

        health = Math.max(0, health - damage);
    }
}
