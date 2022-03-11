package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements IMoveable, IDamagable, IUpdateable {

    private Dungeon dungeon;
    private List<Item> inventory;
    
    private PlayerOrientation upwardsOrientation;
    private PlayerOrientation downwardsOrientation;
    private PlayerOrientation leftOrientation;
    private PlayerOrientation rightOrientation;

    private PlayerOrientation orientation = downwardsOrientation;


    private VulnerableCollision vulnerableStrategy;
    private DamageCollision invincibleStrategy;
    private BooleanProperty isInvincible;
    private double invincTimeLeft;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y, ZLayer.MOVEABLE);
        super.setCollisionBehaviour(new StopCollision());

        this.dungeon = dungeon;
        this.inventory = new ArrayList<Item>();

        upwardsOrientation = new UpwardsOrientation(this, dungeon);
        downwardsOrientation = new DownwardsOrientation(this, dungeon);
        leftOrientation = new LeftOrientation(this, dungeon);
        rightOrientation = new RightOrientation(this, dungeon);
        
        vulnerableStrategy = new VulnerableCollision(this);
        invincibleStrategy = new DamageCollision(this);
        
        setCollisionBehaviour(vulnerableStrategy);
        
        this.isInvincible = new SimpleBooleanProperty(false);

        this.dungeon = dungeon;
        this.inventory = new ArrayList<Item>();

        this.invincTimeLeft = 0.0;
    }

    public void update(double deltaTime) {
        if (isInvincible()) {
            if (invincTimeLeft >= 0) {
                invincTimeLeft -= deltaTime;
            } else {
                makeVulnerable();
            }
        }
    }

    public BooleanProperty invincibilityProperty() {
        return this.isInvincible;
    }

    // TODO maybe make observer for player invinc.
    public void makeInvincible() {
        setCollisionBehaviour(invincibleStrategy);
        this.isInvincible.setValue(true);
        this.invincTimeLeft = 10.0;
        dungeon.scareEnemies();
    }

    public void makeVulnerable() {
        setCollisionBehaviour(vulnerableStrategy);
        this.isInvincible.setValue(false);;
        dungeon.unScareEnemies();
    }

    public boolean isInvincible() {
        return isInvincible.getValue();
    }

    public double getInvincTimeLeft() {
        return this.invincTimeLeft;
    }


    public void move(int x, int y) {
        if (!dungeon.areCoordinatesValid(x, y)) {
            return;
        }
        if (dungeon.tileIsEmpty(x, y)) {
            setPos(x, y);
        } else {
            // Trying to move into an occupied space
            if (dungeon.isTileEnterable(x, y)) {
                setPos(x, y);
            }
            dungeon.processCollision(this, x, y);
        }
    }

    public void moveUp() {
        move(getX(), getY() - 1);
        setOrientation(upwardsOrientation);
    }
    
    public void moveDown() {
        move(getX(), getY() + 1);
        setOrientation(downwardsOrientation);
    }

    public void moveLeft() {
        move(getX() - 1, getY());
        setOrientation(leftOrientation);
    }

    public void moveRight() {
        move(getX() + 1, getY());
        setOrientation(rightOrientation);
    }

    public void attemptAddToInventory(Item i) {
        if (!(i.isUnique())) {
            this.inventory.add(i);
        } else {
            // check if we already have an instance of this type, given it
            // should be unique.
            if (!(hasItemOfType(i))) {
                this.inventory.add(i);
            }
        }
    }

    public void die() {
        destroy();
    }

    public void setOrientation(PlayerOrientation o) {
        this.orientation = o;
    }

    public void attack() {
        if (hasWeapon()) {
            Weapon s = getWeapon();
            orientation.attack(s);
        }

    }

    public boolean hasWeapon() {
        for (Item i : inventory) {
            if (i instanceof Weapon) {
                return true;
            }
        }
        
        return false;
    }

    public Weapon getWeapon() {
        for (Item i : getInventory()) {
            if (i instanceof Weapon) {
                return (Weapon) i;
            }
        }
        return null;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void removeItem(Item item) {

        List<Item> newInventory = new ArrayList<Item>();

        for (Item i: inventory) {
            if (!(i.checkItemType(item))) {
                newInventory.add(i);
            }
        }

        this.inventory = newInventory;
    }

    public boolean isHoldingInstance(Item i) {
        if (inventory.contains(i)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasItemOfType(Item i) {
        for (Item it : inventory) {
            if (it.getClass() == i.getClass()) {
                return true;
            }
        }

        return false;
    }
}
