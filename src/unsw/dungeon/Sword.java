package unsw.dungeon;

public class Sword extends Entity implements Item, UniqueItem {
    
    private CollectCollision c = new CollectCollision(this);
    private int uses;

    public Sword(int x, int y, Entity user) {
        super(x, y);
        super.setCollisionBehaviour(c);
        this.uses = 5;
    }

    public void use(Entity target) {
        if (target instanceof IDamageable) {
            target.die();
            this.uses--;
            if (uses == 0) {
                destroy();
            }
        }
 
    }

    public boolean checkCanUse() {
        return (this.uses > 0);
    }

    public boolean checkSameItem(Entity e) {
        return (e instanceof Sword);
    }

}