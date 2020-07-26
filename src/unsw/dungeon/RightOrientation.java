package unsw.dungeon;

public class RightOrientation implements PlayerOrientation {
    
    private Entity parent;
    private Dungeon dungeon;

    public RightOrientation(Entity p, Dungeon dungeon) {
        this.parent = p;
        this.dungeon = dungeon;
    }
    
    public void attack(Weapon weapon) {
        int attackY = parent.getY();
        int attackX = parent.getX() + 1;
        weapon.use(dungeon.getTopmostEntity(attackX, attackY));
    }
}