package unsw.dungeon;

public class Portal extends Entity {
    
    private TransportCollision teleportCollision;
    private NoCollision deactivatedCollision;
    private int id;

    public Portal(int x, int y, int id) {
        super(x, y);
        this.teleportCollision = null;
        this.deactivatedCollision = new NoCollision();
        // A portal with no link is 'deactivated'
        setCollisionBehaviour(deactivatedCollision);
        this.id = id;
    }

    public void linkPortal(Portal p) {
        this.teleportCollision = new TransportCollision(p);
        setCollisionBehaviour(teleportCollision);
    }

    public int getID() {
        return this.id;
    }

    public void turnOff() {
        setCollisionBehaviour(deactivatedCollision);
    }

    public void turnOn() {
        setCollisionBehaviour(teleportCollision);
    }

    
}