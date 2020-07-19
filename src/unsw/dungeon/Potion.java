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
}