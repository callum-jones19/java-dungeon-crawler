package unsw.dungeon;

import java.util.List;

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
    }

    public void makeHarmful() {
        setCollisionBehaviour(attackState);
    }

    public void chasePlayer() {
        Coordinates targetLoc = currentSearchStrat.pathSearch(dungeon.getPlayerX(), dungeon.getPlayerY(), getX(), getY());
        move(targetLoc.getX(), targetLoc.getY());
    }

    public void move(int x, int y) {
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

    


    public void die() {
        destroy();
    }

}