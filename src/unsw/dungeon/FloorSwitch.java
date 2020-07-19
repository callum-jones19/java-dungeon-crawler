package unsw.dungeon;

public class FloorSwitch extends Entity implements Triggerable {
    
    private CollisionBehaviour c = new TriggerCollision(this, new TriggerTypeBoulder());
    private boolean isActive;

    public FloorSwitch(int x, int y) {
        super(x, y);
        super.setCollisionBehaviour(c);
        this.isActive = false;
    }

    public void trigger() {
        this.isActive = true;
    }

    public boolean isActive() {
        return this.isActive;
    }
}