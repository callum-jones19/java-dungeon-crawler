package unsw.dungeon;

public class PushCollision implements CollisionBehaviour {

    private Entity parent;
    private Boolean isEnterable;

    public PushCollision(Entity parent) {
        this.parent = parent;
        this.isEnterable = false;
    }
    
    public boolean isEnterable() {
        return isEnterable;
    }

    public void onCollide(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;

            if (this.parent instanceof IMoveable) {
                IMoveable m = (IMoveable) this.parent;
                
                int targetX = this.parent.getX() - p.getX();
                int targetY = this.parent.getY() - p.getY();

                int originalX = this.parent.getX();
                int originalY = this.parent.getY();

                m.move(this.parent.getX() + targetX, this.parent.getY() + targetY);
                if (this.parent.getX() != originalX || this.parent.getY() != originalY) {
                    p.move(p.getX() + (this.parent.getX() - originalX), p.getY() + (this.parent.getY() - originalY));
                }
            }
        }
    }

    public void setEnterability(Boolean enterability) {
        this.isEnterable = enterability;
    }

}