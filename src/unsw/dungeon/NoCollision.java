package unsw.dungeon;

public class NoCollision implements CollisionBehaviour {

    Entity parent;
    Boolean isEnterable;

    public NoCollision(Entity p) {
        super();
        this.parent = p;
        this.isEnterable = true;
    }

    public boolean isEnterable() {
        return isEnterable;
    }

    public void onCollide(Entity e) {
        // bring e into the parent's space
        e.setX(parent.getX());
        e.setY(parent.getY());
    }

    public void setEnterability(Boolean enterability) {
        this.isEnterable = enterability;
    }
}