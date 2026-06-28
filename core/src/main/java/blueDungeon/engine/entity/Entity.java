package blueDungeon.engine.entity;

import blueDungeon.engine.collision.hitboxes.Hitbox;

/**
 * Represents an entity managed by the engine.
 *
 * @author mimron
 */
public abstract class Entity {
    private float x;
    private float y;

    private Direction direction;

    private float velocityX;
    private float velocityY;

    private final Hitbox hitbox;

    /**
     * Creates an entity with the specified position, direction and hitbox.
     *
     * @param x the initial x-coordinate
     * @param y the initial y-coordinate
     * @param direction the initial direction
     * @param hitbox the hitbox attached to the entity
     */
    public Entity(float x, float y, Direction direction, Hitbox hitbox) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.hitbox = hitbox;

        syncHitboxPosition();
    }

    /**
     * Updates the entity position using its current velocity.
     */
    public void update() {
        move(velocityX, velocityY);
        syncHitboxPosition();
    }

    /**
     * Moves the entity by the specified offsets.
     *
     * @param deltaX the horizontal offset
     * @param deltaY the vertical offset
     */
    public void move(float deltaX, float deltaY) {
        setPosition(x + deltaX, y + deltaY);
    }

    /**
     * Checks whether this entity collides with another entity.
     *
     * @param other the entity to test against
     * @return {@code true} if the entity hitboxes collide, {@code false} otherwise
     */
    public boolean collidesWith(Entity other) {
        return hitbox.collidesWith(other.hitbox);
    }

    /**
     * Synchronizes the hitbox coordinates with the entity position.
     */
    private void syncHitboxPosition() {
        hitbox.setX(x);
        hitbox.setY(y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        syncHitboxPosition();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setVelocity(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
}
