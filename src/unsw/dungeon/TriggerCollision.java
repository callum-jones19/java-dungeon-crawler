package unsw.dungeon;

public class TriggerCollision implements CollisionBehaviour {
    
    Triggerable parent;

    public TriggerCollision(Triggerable parent) {
        super();
        this.parent = parent;
    } 

    public void onCollide(Entity e) {
        if (e instanceof Trigger) {
            parent.trigger();
        }
    }

}