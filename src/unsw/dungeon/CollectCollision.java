package unsw.dungeon;

public class CollectCollision implements CollisionBehaviour {
    
    // TODO
    // Maybe turn this into Item parent, 
    private Entity parent;
    Boolean isEnterable;

    public CollectCollision(Entity parent) {
        super();
        this.parent = parent;
        this.isEnterable = true;
    }

    public void setParent(Entity p) {
        this.parent = p;
    }

    public boolean isEnterable() {
        return isEnterable;
    }

    public void setEnterability(Boolean enterability) {
        this.isEnterable = enterability;
    }

    public void onCollide(Entity e){
        if (e instanceof Player) {
            Player player = (Player) e;
            if (this.parent instanceof Item) {
                player.pickup(this.parent);
                System.out.println("Picked up by player");
            }
        }
    }

}