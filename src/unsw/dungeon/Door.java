package unsw.dungeon;

public class Door extends Entity implements Triggerable {

    CollisionBehaviour stopCollision = new StopCollision();
    CollisionBehaviour triggerCollision = new TriggerCollision(this);
    CollisionBehaviour noCollision = new NoCollision(this);

    Key key;

    public Door(int x, int y, Key k) {
        super(x, y);
        super.setCollisionBehaviour(stopCollision);
        this.key = k;
    }


    public void trigger() {
        super.setCollisionBehaviour(noCollision);
    }

    public void markAvailable() {
        super.setCollisionBehaviour(triggerCollision);
    }

}