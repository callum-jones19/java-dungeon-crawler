package unsw.dungeon;

public class UpwardsOrientation implements PlayerOrientation {
    
    private Entity parent;
    Dungeon dungeon;

    public UpwardsOrientation(Entity p, Dungeon dungeon) {
        this.parent = p;
        this.dungeon = dungeon;
    }
    
    public void attack(Item tool) {
        int attackY = parent.getY() - 1;
        int attackX = parent.getX();
        if (tool.checkCanUse()) {
            tool.use(dungeon.getTopmostEntity(attackX, attackY));
        }
    }
}