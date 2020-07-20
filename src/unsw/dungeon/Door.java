package unsw.dungeon;

public class Door extends Entity implements Triggerable {


    CollisionBehaviour stopCollision;
    CollisionBehaviour triggerCollision;
    CollisionBehaviour noCollision;
    Key key;

    public Door(int x, int y, Key k) {
        super(x, y);
        this.key = k;
        this.key.setDoor(this);
        this.stopCollision = new StopCollision();
        this.triggerCollision = new TriggerCollision(this, new TriggerTypePlayerInventory(key));
        this.noCollision = new NoCollision();
        super.setCollisionBehaviour(stopCollision);
    }


    public void trigger() {
        super.setCollisionBehaviour(noCollision);
        this.key.destroy();
    }

    public void markAvailable() {
        super.setCollisionBehaviour(triggerCollision);
        triggerCollision.setEnterability(false);
    }

    @Override 
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Door d = (Door) o;
        return (d.getX() == this.getX() && d.getY() == this.getY());
    }

}