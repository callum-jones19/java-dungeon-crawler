package unsw.dungeon;

public class DownwardsOrientation implements PlayerOrientation {
    
    private Entity parent;
    Dungeon dungeon;

    public DownwardsOrientation(Entity p, Dungeon d) {
        this.parent = p;
        this.dungeon = d;
    }
    
    public void attack(Weapon weapon) {
        int attackY = parent.getY() + 1;
        int attackX = parent.getX();
        weapon.use(dungeon.getTopmostEntity(attackX, attackY));
    }
}