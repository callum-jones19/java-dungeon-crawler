package unsw.dungeon;

public class DamageCollision implements CollisionBehaviour{
    
    private Boolean isEnterable;
    private Entity parent;

    public DamageCollision(Entity parent) {
        this.isEnterable = false;
        this.parent = parent;
    }

    public boolean isEnterable() {
        return isEnterable;
    }

    // If an entity (player) collides with this, the colliding entity is killed.
    // i.e. This entity has spikes on it. Big ones. Like bigger than you could
    // ever imagine.
    public void onCollide(Entity e) {
        if (e instanceof IDamagable) {
            IDamagable d = (IDamagable) e;
            d.die();
        } else if (e instanceof Boulder) {
            if (parent instanceof Enemy) {
                Enemy enemy = (Enemy) parent;
                Boulder b = (Boulder) e;

                int newX = enemy.getX();
                int newY = enemy.getY();

                enemy.die();
                b.move(newX, newY);
            }
        }
    }

}