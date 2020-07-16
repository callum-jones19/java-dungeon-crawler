package unsw.dungeon;

public class CollectCollision implements CollisionBehaviour {
    
    private Entity parent;

    public CollectCollision(Entity e) {
        this.parent = e;
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