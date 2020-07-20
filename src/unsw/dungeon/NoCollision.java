package unsw.dungeon;

public class NoCollision implements CollisionBehaviour {

    private Boolean isEnterable;

    public NoCollision() {
        super();
        this.isEnterable = true;
    }

    public boolean isEnterable() {
        return isEnterable;
    }

    public void onCollide(Entity e) {
        // Can add an effect here in milestone 3.
    }

}