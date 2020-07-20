package unsw.dungeon;

public class UpwardsOrientation implements PlayerOrientation {
    
    private Entity parent;
    Dungeon dungeon;

    public UpwardsOrientation(Entity p, Dungeon dungeon) {
        this.parent = p;
        this.dungeon = dungeon;
    }
    
    public void attack(Sword weapon) {
        int attackY = parent.getY() - 1;
        int attackX = parent.getX();
        if (weapon.canUseAgain()) {
            weapon.use(dungeon.getTopmostEntity(attackX, attackY));
        }
    }
}