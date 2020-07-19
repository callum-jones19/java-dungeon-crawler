package unsw.dungeon;

public class FloorSwitch extends Entity implements BoulderObserver {
    
    private CollisionBehaviour c = new NoCollision();
    private boolean isActive;

    public FloorSwitch(int x, int y) {
        super(x, y);
        super.setCollisionBehaviour(c);
        this.isActive = false;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(Boolean active) {
        this.isActive = active;
    }

    @Override
    public void update(BoulderSubject b) {
        
        if (b.getX() == getX() && b.getY() == getY()) {
            updateSwitchStatus();
        } else if (b.getLastX() == getX() && b.getLastY() == getY()) {
            updateSwitchStatus();
        }

    }

    public void updateSwitchStatus() {
        if (!(isActive())) {
            setActive(true);
        } else {
            setActive(false);
        }
    }


}