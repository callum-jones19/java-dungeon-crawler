package unsw.dungeon;

public class LeftOrientation implements PlayerOrientation {
    
    private Entity parent;

    public RightOrientation(Entity p) {
        this.parent = p;
    }
    
    public void attack() {
        int attackY = parent.getY();
        int attackX = parent.getX() + 1;
        if (tool.checkCanUse()) {
            e.die();
        }
    }
}