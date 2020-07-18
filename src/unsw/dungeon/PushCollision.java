package unsw.dungeon;


public class PushCollision implements CollisionBehaviour {

    private Entity parent;

    public PushCollision(Entity parent) {
        this.parent = parent;
    }
    
    public boolean isEnterable() {
        return false;
    }

    public void onCollide(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;

            if (this.parent instanceof IMoveable) {
                IMoveable m = (IMoveable) this.parent;
                
                int targetX = this.parent.getX() - p.getX();
                int targetY = this.parent.getY() - p.getY();

                m.move(this.parent.getX() + targetX, this.parent.getX() + targetY);
            }
        }
    }

}