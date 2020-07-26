package unsw.dungeon;

public class Sword extends Entity implements Item, Weapon {
    
    private CollectCollision c = new CollectCollision(this);
    private int uses;
    private Player user;

    public Sword(int x, int y) {
        super(x, y);
        super.setCollisionBehaviour(c);
        this.uses = 5;
    }

    public void use(Entity target) {
        if (target instanceof IDamagable && user != null) {
            IDamagable newTarget = (IDamagable) target;
            newTarget.die();
            this.uses--;
            if (!canUseAgain()) {
                user.removeItem(this);
                destroy();
            }
        }
 
    }

    public void setUser(Player player) {
        this.user = player;
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

    public void pickup(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;
            p.addToInventory(this);
            if (p.isHoldingInstance(this)) {
                destroy();
            }
               
        }
    }

}