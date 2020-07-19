package unsw.dungeon;

public class VulnerableCollision implements CollisionBehaviour{
    
    Entity parent;
    Boolean isEnterable;

    public VulnerableCollision(Entity parent) {
        this.parent = parent;
        this.isEnterable = true;
    }

    public boolean isEnterable() {
        return isEnterable;
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

    @Override
    public void setEnterability(Boolean enterability) {
        this.isEnterable = enterability;

    }

}