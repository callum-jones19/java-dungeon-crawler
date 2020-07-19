package unsw.dungeon;

public class TriggerCollision implements CollisionBehaviour {
    
    Triggerable parent;
    TriggerType triggerType;
    Boolean isEnterable;

    public TriggerCollision(Triggerable parent, TriggerType triggerType) {
        super();
        this.parent = parent;
        this.triggerType = triggerType;
        this.isEnterable = true;
    } 

    public boolean isEnterable() {
        return this.isEnterable;
    }

    public void onCollide(Entity e) {
        if (triggerType.performTriggerCheck(e)) this.parent.trigger();
    }

    public void setEnterability(Boolean enterability) {
        this.isEnterable = enterability;
    }

}