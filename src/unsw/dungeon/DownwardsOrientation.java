package unsw.dungeon;

public class DownwardsOrientation implements PlayerOrientation {
    
    private Entity parent;

    public DownwardsOrientation(Entity p) {
        this.parent = p;
    }
    
    public void attack() {
        int attackY = parent.getY() + 1;
        int attackX = parent.getX();
        if (tool.checkCanUse()) {
            e.die();
        }
    }
}