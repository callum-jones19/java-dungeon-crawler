package unsw.dungeon;

public class CollectCollision implements CollisionBehaviour {

    private Entity parent;
    private Boolean isEnterable;

    public CollectCollision(Entity parent) {
        super();
        this.parent = parent;
        this.isEnterable = true;
    }

    public boolean isEnterable() {
        return isEnterable;
    }

    public void onCollide(Entity e){
        if (e instanceof Player) {
            Player player = (Player) e;
            if (this.parent instanceof Item) {
                Item i = (Item) parent;
                i.pickup(player);
                parent.destroy();
            }
        }
    }

}