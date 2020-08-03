package unsw.dungeon;

public class Potion extends Entity implements Item {
    
    private CollisionBehaviour collectStrat;

    public Potion(int x, int y) {
        super(x, y, ZLayer.ITEM);
        this.collectStrat = new CollectCollision(this);
        setCollisionBehaviour(collectStrat);
    }

    
    public void activate(Player p) {
        p.setCollisionBehaviour(new DamageCollision(p));
        p.makeInvincible();
        if (!canUseAgain()) {
            p.removeItem(this);
        }
    }

    public boolean canUseAgain() {
        return false;
    }

    public boolean isUnique() {
        return false;
    }

    public boolean checkItemType(Item i) {
        return (i instanceof Potion);
    }

    public void pickup(Player p) {
        p.attemptAddToInventory(this);
        if (p.isHoldingInstance(this)) {
            destroy();
            activate(p);
        }
    }

    
}