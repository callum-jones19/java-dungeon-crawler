package unsw.dungeon;

public class TransportCollision implements CollisionBehaviour {

    private boolean isEnterable;
    Coordinates teleportLocation;

    public TransportCollision(int x, int y) {
        super();
        isEnterable = true;
        teleportLocation = new Coordinates(x, y);
    }

    public boolean isEnterable() {
        return isEnterable;
    }

    public void setLocation(Coordinates c) {
        teleportLocation = c;
    }

    public void setEnterability(Boolean enterability) {
        isEnterable = enterability;

    }

    public void onCollide(Entity e) {
        if (e instanceof IMoveable) {
            IMoveable moveable = (IMoveable) e;
            moveable.move(teleportLocation.getX(), teleportLocation.getY());
        }
    }

}