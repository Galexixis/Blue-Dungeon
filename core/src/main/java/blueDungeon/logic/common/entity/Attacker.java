package blueDungeon.logic.common.entity;

/**
 * Represents an entity that can attack other damageable entities.
 *
 * @author mimron
 */
public interface Attacker {

    /**
     * Inflicts damage on the specified target.
     *
     * @param target the entity to attack
     * @throws NullPointerException if target is null
     */
    void attack(Damageable target);

}
