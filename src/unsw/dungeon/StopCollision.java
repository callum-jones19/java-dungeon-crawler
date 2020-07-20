package unsw.dungeon;

public class StopCollision implements CollisionBehaviour {
    
    private Boolean isEnterable;

    public StopCollision() {
        super();
        this.isEnterable = false;
    }

    public boolean isEnterable() {
        return isEnterable;
    }

    public void onCollide(Entity e) {
        // As of now, do nothing.
    }

    public void setEnterability(Boolean enterability) {
        this.isEnterable = enterability;
    }


}