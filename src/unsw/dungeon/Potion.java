package unsw.dungeon;

public class Potion extends Entity implements Item, PickupActivateItem {
    
    private CollisionBehaviour collectStrat;

    public Potion(int x, int y) {
        super(x, y);
        this.collectStrat = new CollectCollision(this);
        setCollisionBehaviour(collectStrat);
    }

    public void activate(Entity e) {
        e.setCollisionBehaviour(new DamageCollision());
        if (e instanceof Player) {
            Player p = (Player) e;
            p.makeInvincible();
        }
    }

    public void use(Entity e) {

    }

    public void activate() {
        // FIX
    }

    public boolean checkCanUse() {
        return false;
    }

    public boolean isWeapon() {
        return false;
    }

    public boolean isUnique() {
        return false;
    }

    public boolean checkItemType(Item i) {
        return (i instanceof Potion);
    }

    public void pickup(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;
            p.pickup(this);
            if (p.exactContains(this)) {
                destroy();
            }
               
        }
    }

    
}