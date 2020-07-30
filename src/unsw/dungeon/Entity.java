package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public abstract class Entity implements DestroySubject{

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private Coordinates coords;
    private CollisionBehaviour collisionBehaviour;

    private ZLayer layer;

    private List<DestroyObserver> observers;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y, ZLayer layer) {
        coords = new Coordinates(x, y);

        this.layer = layer;

        observers = new ArrayList<DestroyObserver>();
    }

    public ZLayer getLayer() {
        return this.layer;
    }

    public void registerObserver(DestroyObserver o) {
        if (! observers.contains(o)) {
            this.observers.add(o);
        }
    }

    public void removeObserver(DestroyObserver o) {
        this.observers.remove(o);
    }

    public void notifyObservers() {
        for (DestroyObserver o : observers) {
            o.update(this);
        }
    }

    public IntegerProperty x() {
        return coords.x();
    }

    public IntegerProperty y() {
        return coords.y();
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public void setY(int y) {
        y().set(y);
    }

    public void setX(int x) {
        x().set(x);
    }

    public void setPos(int x, int y) {
        setY(y);
        setX(x);
    }

    public void onCollide(Entity e) {
        this.collisionBehaviour.onCollide(e);
    }

    public boolean isEnterable() {
        return this.collisionBehaviour.isEnterable();
    }

    public void setCollisionBehaviour(CollisionBehaviour c) {
        this.collisionBehaviour = c;
    }

    public void destroy() {
        notifyObservers();
    }
}
