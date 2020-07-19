package unsw.dungeon;

public class Portal extends Entity {
    
    private Portal matching;
    private TransportCollision teleportCollision;

    public Portal(int x, int y, Portal match) {
        super(x, y);
        teleportCollision = new TransportCollision(matching.getX(), matching.getY());
        setCollisionBehaviour(teleportCollision);
        this.matching = match;
    }

    
}