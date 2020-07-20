package unsw.dungeon;

public class Sword extends Entity implements Item {
    
    private CollectCollision c = new CollectCollision(this);
    private int uses;
    Player user;

    public Sword(int x, int y, Player user) {
        super(x, y);
        super.setCollisionBehaviour(c);
        this.uses = 5;
        this.user = user;
    }

    public void use(Entity target) {
        if (target instanceof IDamagable) {
            IDamagable newTarget = (IDamagable) target;
            newTarget.die();
            this.uses--;
            if (uses == 0) {
                user.removeItem(this);
                destroy();
            }
        }
 
    }

    public boolean canUseAgain() {
        return (this.uses > 0);
    }

    public boolean checkItemType(Item i) {
        return (i instanceof Sword);
    }

    public boolean isUnique() {
        return true;
    }

}