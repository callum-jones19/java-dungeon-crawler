package unsw.dungeon;

public class StopCollision implements CollisionBehaviour {
    
    Boolean isEnterable;

    public StopCollision() {
        super();
        this.isEnterable = false;
    }

    public boolean isEnterable() {
        return isEnterable;
    }

    public void onCollide(Entity e) {
        // As of now, do nothing.
        // The collision implementation for stop should refuse to
        // pull anything into its space.
    }

    public void setEnterability(Boolean enterability) {
        this.isEnterable = enterability;
    }


}