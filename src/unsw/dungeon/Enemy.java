package unsw.dungeon;

public class Enemy extends Entity implements IMoveable, IDamagable, IUpdateable {
    
    private Dungeon dungeon;
    
    private VulnerableCollision vulnState;
    private DamageCollision attackState;

    private SearchStyle currentSearchStrat;

    private double timeUntilNextMove;

    public Enemy (int x, int y, Dungeon d) {
        super(x,y);
        this.dungeon = d;

        vulnState = new VulnerableCollision(this);
        attackState = new DamageCollision();

        currentSearchStrat = new DirectSearch();

        setCollisionBehaviour(attackState);

        timeUntilNextMove = 1.0;
    }

    public void update(double deltaTime) {
        if (timeUntilNextMove <= 0) {
            chasePlayer();
            timeUntilNextMove = 1.0;
        } else {
            timeUntilNextMove -= deltaTime;
        }
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
        dungeon.printDungeon();
    }

    public void move(int x, int y) {
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

    public void die() {
        destroy();
    }

}