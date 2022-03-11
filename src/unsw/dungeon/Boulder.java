package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Boulder extends Entity implements IMoveable, BoulderSubject {
    
    private Dungeon dungeon;
    private CollisionBehaviour collisionBehaviour = new PushCollision(this);
    private List<BoulderObserver> observers = new ArrayList<BoulderObserver>();
    private int lastX;
    private int lastY;

    public Boulder(Dungeon dungeon, int x, int y) {
        super(x,y, ZLayer.MOVEABLE);
        this.dungeon = dungeon;
        super.setCollisionBehaviour(collisionBehaviour);
    }

    public void move(int x, int y) {
        if (!dungeon.areCoordinatesValid(x, y)) {
            return;
        }
        if (dungeon.tileIsEmpty(x, y)) {
            lastX = getX();
            lastY = getY();
            setPos(x, y);
            notifyBoulderObservers();
        } else {
            // Trying to move into an occupied space
            if (dungeon.isTileEnterable(x, y)) {
                lastX = getX();
                lastY = getY();
                setPos(x, y);
                notifyBoulderObservers();
            }
            dungeon.processCollision(this, x, y);
        }
    }

    public void registerObserver(BoulderObserver o) {
        observers.add(o);
    }

    public void notifyBoulderObservers() {
        for (BoulderObserver o: observers) {
            o.update(this);
        }
    }

    @Override
    public int getLastX() {
        return lastX;
    }

    @Override
    public int getLastY() {
        return lastY;
    }

}