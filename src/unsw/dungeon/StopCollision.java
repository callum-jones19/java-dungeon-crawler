package unsw.dungeon;

public class StopCollision implements CollisionBehaviour {
    

    public StopCollision() {
        super();
    }

    public boolean isEnterable() {
        return false;
    }

    public void onCollide(Entity e) {
        // As of now, do nothing.
        // The collision implementation for stop should refuse to
        // pull anything into its space.
    }


}