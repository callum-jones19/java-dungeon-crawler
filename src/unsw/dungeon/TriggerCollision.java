package unsw.dungeon;

public class TriggerCollision implements CollisionBehaviour {
    
    Triggerable parent;
    TriggerType triggerType;

    public TriggerCollision(Triggerable parent, TriggerType triggerType) {
        super();
        this.parent = parent;
        this.triggerType = triggerType;
    } 

    public boolean isEnterable() {
        return true;
    }

    public void onCollide(Entity e) {
        if (triggerType.performTriggerCheck(e)) this.parent.trigger();
    }

}