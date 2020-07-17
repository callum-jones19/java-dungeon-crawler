package unsw.dungeon;

public class CollectCollision implements CollisionBehaviour {
    
    // TODO
    // Maybe turn this into Item parent, 
    private Entity parent;

    public CollectCollision(Entity parent) {
        super();
        this.parent = parent;
    }

    public void setParent(Entity p) {
        this.parent = p;
    }

    public boolean isEnterable() {
        return true;
    }

    public void onCollide(Entity e){
        if (e instanceof Player) {
            Player player = (Player) e;
            if (this.parent instanceof Item) {
                Item i = (Item) this.parent;
                player.pickup(i);
                parent.destroy();
            }
        }
    }

}