package unsw.dungeon;

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
    PlayerState normal;
    PlayerState attack;
    PlayerState potion;

    PlayerState state = normal;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        super.setCollisionBehaviour(new StopCollision());
        
        normal = new NormalPlayerState(this);
        attack = new AttackPlayerState(this);
        potion = new PotionPlayerState(this);
        
        this.dungeon = dungeon;
        this.inventory = new ArrayList<Item>();
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
        }
    }
    
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1)
            move(getX(), getY() + 1);
    }

    public void moveLeft() {
        if (getX() > 0)
            move(getX() - 1, getY());
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1)
            move(getX() + 1, getY());
    }

    // This still breaks LOD - fix!
    public void collide (Entity e) {
        if (e instanceof Enemy) {
            state.collide(e);
        } else {
            e.onCollide(this);
        }
    }

    public void pickup(Entity e) {
        if (e instanceof Item) {
            this.inventory.add((Item) e);
        }
    }

    public void setState(PlayerState p) {
        this.state = p;
    }

    public PlayerState getNormalPlayerState() {
        return normal;
    }

    public PlayerState getAttackPlayerState() {
        return attack;
    }

    public PlayerState getPotionPlayerState() {
        return potion;
    }

}
