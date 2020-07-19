package unsw.dungeon;

public class Sword extends Entity implements Item {
    
    private CollectCollision c = new CollectCollision(this);
    private int uses;

    public Sword(int x, int y, Entity user) {
        super(x, y);
        super.setCollisionBehaviour(c);
        this.uses = 5;
    }

    public void use(Entity target) {
        if (target instanceof IDamagable) {
            IDamagable newTarget = (IDamagable) target;
            newTarget.die();
            this.uses--;
            if (uses == 0) {
                destroy();
            }
        }
 
    }

    public boolean checkCanUse() {
        return (this.uses > 0);
    }

    public boolean checkItemType(Item i) {
        return (i instanceof Sword);
    }

    public boolean isWeapon() {
        return true;
    }

    public boolean isUnique() {
        return true;
    }

}