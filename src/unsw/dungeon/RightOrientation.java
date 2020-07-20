package unsw.dungeon;

public class RightOrientation implements PlayerOrientation {
    
    private Entity parent;
    Dungeon dungeon;

    public RightOrientation(Entity p, Dungeon dungeon) {
        this.parent = p;
        this.dungeon = dungeon;
    }
    
    public void attack(Sword weapon) {
        int attackY = parent.getY();
        int attackX = parent.getX() + 1;
        if (weapon.canUseAgain()) {
            weapon.use(dungeon.getTopmostEntity(attackX, attackY));
        }
    }
}