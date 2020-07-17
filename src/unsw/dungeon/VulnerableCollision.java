package unsw.dungeon;

public class VulnerableCollision implements CollisionBehaviour{
    
    Entity parent;

    public VulnerableCollision(Entity parent) {
        this.parent = parent;
    }

    public boolean isEnterable() {
        return true;
    }

    // If something collides with this instance's parent, that parent is killed.
    // i.e. This parent is vulnerable to collisions. They didn't drink their milk.
    // They have weak bones.
    public void onCollide(Entity e) {
        if (this.parent instanceof IDamagable) {
            IDamagable d = (IDamagable) this.parent;
            d.die();
        }
    }

}