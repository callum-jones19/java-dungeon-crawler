package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Entity implements Triggerable {


    private StopCollision stopCollision;
    private TriggerCollision triggerCollision;
    private NoCollision noCollision;
    private Key key;
    private int id;

    private BooleanProperty isOpen;
    private BooleanProperty isAvailable;

    public Door(int x, int y, int id) {
        super(x, y, ZLayer.OBSTACLE);
        this.key = null;
        this.stopCollision = new StopCollision();
        // Need to wait until a key has been linked to assign
        this.triggerCollision = null;
        this.noCollision = new NoCollision();
        this.id = id;
        super.setCollisionBehaviour(stopCollision);
        
        this.isAvailable = new SimpleBooleanProperty(false);
        this.isOpen = new SimpleBooleanProperty(false);
    }

    public void linkKey(Key k) {
        this.key = k;
        this.triggerCollision = new TriggerCollision(this, new TriggerTypePlayerInventory(key));
    }

    public int getID() {
        return this.id;
    }

    public void trigger() {
        super.setCollisionBehaviour(noCollision);
        this.key.destroy();
        this.isAvailable.setValue(false);
        this.isOpen.setValue(true);
    }

    public BooleanProperty isOpenProperty() {
        return this.isOpen;
    }

    public BooleanProperty isAvaialableProperty() {
        return this.isAvailable;
    }

    public void markAvailable() {
        super.setCollisionBehaviour(triggerCollision);
        triggerCollision.setEnterability(false);
        this.isAvailable.setValue(true);
    }

    @Override 
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Door d = (Door) o;
        return (d.getX() == this.getX() && d.getY() == this.getY());
    }

}