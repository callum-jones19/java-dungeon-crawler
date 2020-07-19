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
        // TODO

        if (x < 1 || x > dungeon.getWidth()) return;
        if (y < 1 || y > dungeon.getHeight()) return;

        if (dungeon.tileIsEmpty(x, y)) {
            setPos(x, y);
            bsm.notifyBoulderPosChange(x, y);
        } else {
            if (dungeon.checkEnterableTile(x, y)) {
                setPos(x, y);
                bsm.notifyBoulderPosChange(x, y);
            }
        }
    }

    public void setMediator(BoulderSwitchMediator bsm) {
        this.bsm = bsm;
    }

}