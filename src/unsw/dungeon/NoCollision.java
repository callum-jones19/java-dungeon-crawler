package unsw.dungeon;

public class NoCollision implements CollisionBehaviour {

    Boolean isEnterable;

    public NoCollision() {
        super();
        this.isEnterable = true;
    }

    public boolean isEnterable() {
        return isEnterable;
    }

    public void onCollide(Entity e) {
        // TODO might have to think about these empty ones.
    }

    public void setEnterability(Boolean enterability) {
        this.isEnterable = enterability;
    }
}