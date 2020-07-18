package unsw.dungeon;

public class Potion extends Entity implements Item, PickupActivateItem {
    
    private CollisionBehaviour collisionBehaviour;

    public Potion(int x, int y) {
        super(x, y);
        this.collisionBehaviour = new CollectionCollision(this);
    }

    public void activate(Entity e) {
        e.setCollisionBehaviour(new DamageCollision());
        if (e instanceof Player) {
            e.scareEnemies();
        }
    }

    public void use(Entity e) {

    }

    public void activate() {
        // FIX
    }
}