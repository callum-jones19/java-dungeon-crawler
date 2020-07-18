package unsw.dungeon;

public class DownwardsOrientation implements PlayerOrientation {
    
    private Entity parent;
    Dungeon dungeon;

    public DownwardsOrientation(Entity p, Dungeon d) {
        this.parent = p;
        this.dungeon = d;
    }
    
    public void attack(Item tool) {
        int attackY = parent.getY() + 1;
        int attackX = parent.getX();
        if (tool.checkCanUse()) {
            tool.use(dungeon.getTopmostEntity(attackX, attackY));
        }
    }
}