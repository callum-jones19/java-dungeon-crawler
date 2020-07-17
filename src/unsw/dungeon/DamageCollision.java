package unsw.dungeon;

public class DamageCollision implements CollisionBehaviour{
    
    public DamageCollision() {

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
}