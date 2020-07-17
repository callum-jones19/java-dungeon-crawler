package unsw.dungeon;

public class UpwardsOrientation implements PlayerOrientation {
    
    private Entity parent;

    public UpwardsOrientation(Entity p) {
        this.parent = p;
    }
    
    public void attack(Item tool, Enemy e) {
        int attackY = parent.getY() - 1;
        int attackX = parent.getX();
        if (tool.checkCanUse()) {
            tool.use(attackX, attackY, e);
        }
    }
}