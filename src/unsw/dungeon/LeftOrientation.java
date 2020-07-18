package unsw.dungeon;

public class LeftOrientation implements PlayerOrientation {
    
    private Entity parent;
    Dungeon dungeon;

    public LeftOrientation(Entity p, Dungeon dungeon) {
        this.parent = p;
        this.dungeon = dungeon;
    }
    
    public void attack(Item tool) {
        int attackY = parent.getY();
        int attackX = parent.getX() + 1;
        if (tool.checkCanUse()) {
            tool.use(dungeon.getTopmostEntity(attackX, attackY));
        }
    }
}