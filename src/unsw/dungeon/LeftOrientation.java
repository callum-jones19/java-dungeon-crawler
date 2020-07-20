package unsw.dungeon;

public class LeftOrientation implements PlayerOrientation {
    
    private Entity parent;
    Dungeon dungeon;

    public LeftOrientation(Entity p, Dungeon dungeon) {
        this.parent = p;
        this.dungeon = dungeon;
    }
    
    public void attack(Weapon weapon) {
        int attackY = parent.getY();
        int attackX = parent.getX() - 1;
        weapon.use(dungeon.getTopmostEntity(attackX, attackY));
    }
}