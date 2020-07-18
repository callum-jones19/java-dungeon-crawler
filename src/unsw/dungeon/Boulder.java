package unsw.dungeon;

public class Boulder extends Entity implements IMoveable {
    
    Dungeon dungeon;
    CollisionBehaviour collisionBehaviour = new PushCollision(this);

    public Boulder(Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
        super.setCollisionBehaviour(collisionBehaviour);
    }

    public void move(int x, int y) {
        // TODO
        if (dungeon.tileIsEmpty(x, y)) {
            setPos(x, y);
        }
    }

}