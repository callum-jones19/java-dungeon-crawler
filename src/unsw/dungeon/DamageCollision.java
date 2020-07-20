package unsw.dungeon;

public class DamageCollision implements CollisionBehaviour{
    
    Boolean isEnterable;

    public DamageCollision() {
        this.isEnterable = false;
    }

    public boolean isEnterable() {
        return false;
    }

    // If an entity (player) collides with this, the colliding entity is killed.
    // i.e. This entity has spikes on it. Big ones. Like bigger than you could
    // ever imagine.
    public void onCollide(Entity e) {
        if (e instanceof IDamagable) {
            IDamagable d = (IDamagable) e;
            d.die();
        }
    }

    public void setEnterability(Boolean enterability) {
        this.isEnterable = enterability;
    }
}