package unsw.dungeon;

public class Enemy extends Entity implements IMoveable, IDamagable {
    
    private Dungeon dungeon;
    
    private VulnerableCollision vulnState;
    private DamageCollision attackState;

    private SearchStyle currentSearchStrat;

    public Enemy (int x, int y, Dungeon d) {
        super(x,y);
        this.dungeon = d;

        vulnState = new VulnerableCollision(this);
        attackState = new DamageCollision();

        currentSearchStrat = new DirectSearch();

        setCollisionBehaviour(attackState);
    }

    public void makeVulnerable() {
        setCollisionBehaviour(vulnState);
        currentSearchStrat = new FleeSearch();
    }

    public void makeHarmful() {
        setCollisionBehaviour(attackState);
        currentSearchStrat = new DirectSearch();
    }

    public void chasePlayer() {
        Coordinates targetLoc = currentSearchStrat.pathSearch(dungeon.getPlayerX(), dungeon.getPlayerY(), getX(), getY());
        move(targetLoc.getX(), targetLoc.getY());
    }

    public void move(int x, int y) {
        if (dungeon.tileIsEmpty(x, y)) {
            setPos(x, y);
        } else {
            Entity top = dungeon.getTopmostEntity(x, y);
            /////////////////////////////////////////////
            if (top.isEnterable()) {
                setPos(x, y);
                top.onCollide(this);
            } else {
                top.onCollide(this);
            }
        }
    } 

    public void die() {
        destroy();
    }

}