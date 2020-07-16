package unsw.dungeon;

public class CollectCollision implements CollisionBehaviour {
    
    private Entity parent;

    public CollectCollision(Entity parent) {
        super();
        this.parent = parent;
    }

    public void setParent(Entity p) {
        this.parent = p;
    }

    public void onCollide(Entity e){
        if (e instanceof Player) {
            Player p = (Player) e;
            p.pickup(parent);
            parent.destroy();
        }
        e.setPos(parent.getX(), parent.getY());
    }

}