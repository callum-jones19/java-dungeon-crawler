package unsw.dungeon;

public class Portal extends Entity implements Triggerable {
    
    private Portal matching;
    CollisionBehaviour c = new TriggerCollision(this);

    public Portal(int x, int y, Portal match) {
        super(x, y);
        super.setCollisionBehaviour(c);
        this.matching = match;
    }

    public void trigger() {
        
    }
}