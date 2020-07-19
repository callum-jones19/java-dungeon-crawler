package unsw.dungeon;

public class TransportCollision implements CollisionBehaviour {

    private boolean isEnterable;
    Portal exitPortal;

    public TransportCollision(Portal p) {
        super();
        isEnterable = true;
        this.exitPortal = p;
    }

    public boolean isEnterable() {
        return isEnterable;
    }

    public void setLocation(Portal p) {
        this.exitPortal = p;
    }

    public void setEnterability(Boolean enterability) {
        isEnterable = enterability;

    }

    public void onCollide(Entity e) {
        if (e instanceof IMoveable) {
            IMoveable moveable = (IMoveable) e;
            exitPortal.turnOff();
            moveable.move(exitPortal.getX(),exitPortal.getY());
            exitPortal.turnOn();
        }
    }

}