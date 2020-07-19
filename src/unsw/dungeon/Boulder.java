package unsw.dungeon;

public class Boulder extends Entity implements IMoveable {
    
    Dungeon dungeon;
    CollisionBehaviour collisionBehaviour = new PushCollision(this);
    BoulderSwitchMediator bsm;

    public Boulder(Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
        super.setCollisionBehaviour(collisionBehaviour);
    }

    public void move(int x, int y) {
        if (dungeon.tileIsEmpty(x, y)) {
            setPos(x, y);
            bsm.notifyBoulderPosChange(x, y);
        } else {
            // Trying to move into an occupied space
            if (dungeon.isTileEnterable(x, y)) {
                setPos(x, y);
                bsm.notifyBoulderPosChange(x, y);
            }
            dungeon.processCollision(this, x, y);
        }
    }

    public void setMediator(BoulderSwitchMediator bsm) {
        this.bsm = bsm;
    }

}