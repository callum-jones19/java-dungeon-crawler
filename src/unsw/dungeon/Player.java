package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements IMoveable, IDamagable {

    private Dungeon dungeon;
    private List<Item> inventory;
    
    PlayerOrientation upwardsOrientation;
    PlayerOrientation downwardsOrientation;
    PlayerOrientation leftOrientation;
    PlayerOrientation rightOrientation;

    public PlayerOrientation orientation = rightOrientation;


    private VulnerableCollision vulnerableStrategy;
    private DamageCollision invincibleStrategy;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        super.setCollisionBehaviour(new StopCollision());

        this.dungeon = dungeon;
        this.inventory = new ArrayList<Item>();

        upwardsOrientation = new UpwardsOrientation(this, dungeon);
        downwardsOrientation = new DownwardsOrientation(this, dungeon);
        leftOrientation = new LeftOrientation(this, dungeon);
        rightOrientation = new RightOrientation(this, dungeon);
        
        vulnerableStrategy = new VulnerableCollision(this);
        invincibleStrategy = new DamageCollision();
        
        setCollisionBehaviour(vulnerableStrategy);
        
        this.dungeon = dungeon;
        this.inventory = new ArrayList<Item>();

    }

    public void makeInvincible() {
        setCollisionBehaviour(invincibleStrategy);
    }

    public void makeVulnerable() {
        setCollisionBehaviour(vulnerableStrategy);
    }


    public void move(int x, int y) {
        // TODO add move check for boundaries?
        if (dungeon.tileIsEmpty(x, y)) {
            setPos(x, y);
        } else {
            //FIXME
            List<Entity> colliding = dungeon.getEntities(x,y);
            Entity top = colliding.get(0);
            for (Entity e : colliding) {
                if (colliding.size() == 1) {
                    top = e;
                } else {
                    if (e instanceof Boulder) {
                        top = e;
                    }
                }
            }
            /////////////////////////////////////////////
            if (top.isEnterable()) {
                setPos(x, y);
                top.onCollide(this);
            } else {
                top.onCollide(this);
            }
        }
    }

    public void moveUp() {
        if (getY() > 0) {
            move(getX(), getY() - 1);
            setOrientation(upwardsOrientation);
        }
    }
    
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
            move(getX(), getY() + 1);
            setOrientation(downwardsOrientation);
        }
    }

    public void moveLeft() {
        if (getX() > 0) {
            move(getX() - 1, getY());
            setOrientation(leftOrientation);
        }
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
            move(getX() + 1, getY());
            setOrientation(rightOrientation);
        }
    }

    public void pickup(Entity e) {
        if ((e instanceof Item) && (!(e instanceof UniqueItem))) {
            this.inventory.add((Item) e);
            if (e instanceof PickupActivateItem) {
                PickupActivateItem p = (PickupActivateItem) e;
                p.activate();
            }
            e.destroy();
        } else if ((e instanceof Item) && (e instanceof UniqueItem)) {
            for (Item i: inventory) {
                if ((i instanceof UniqueItem)) {
                    UniqueItem u = (UniqueItem) i;
                    if (!(u.checkSameItem(e))) {
                        this.inventory.add((Item) e);
                        if (e instanceof PickupActivateItem) {
                            PickupActivateItem p = (PickupActivateItem) e;
                            p.activate();
                        }
                        e.destroy();
                    }
                } 
            }
        }
    }

    public void die() {
        destroy();
    }

    public void setOrientation(PlayerOrientation o) {
        this.orientation = o;
    }

    public void scareEnemies() {
        dungeon.setEnemySearchPattern(new FleeSearch());
    }

    public boolean contains(Item i) {
        for (Item item: inventory) {
            if (item.equals(i)) {
                return true;
            }
        }

        return false;
    }

}
