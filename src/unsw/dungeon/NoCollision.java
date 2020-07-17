package unsw.dungeon;

public class NoCollision implements CollisionBehaviour {

    Entity parent;
    
    public NoCollision(Entity p) {
        super();
        this.parent = p;
    }

    public boolean isEnterable() {
        return true;
    }

    public void onCollide(Entity e) {
        // bring e into the parent's space
        e.setX(parent.getX());
        e.setY(parent.getY());
    }
}