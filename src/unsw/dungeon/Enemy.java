package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Entity implements IMoveable, IDamagable, Goal {
    
    private Dungeon dungeon;
    
    private VulnerableCollision vulnState;
    private DamageCollision attackState;

    private SearchStyle currentSearchStrat;

    private List<GoalObserverChild> goalObservers = new ArrayList<GoalObserverChild>();

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

    @Override
    public void registerObserver(GoalObserver obs) {
        if (obs instanceof GoalObserverChild) {
            this.goalObservers.add((GoalObserverChild) obs);
        }
        

    }

    @Override
    public void removeObserver(GoalObserver obs) {

        if (obs instanceof GoalObserverChild) {
            this.goalObservers.remove((GoalObserverChild) obs);
        }

    }

    @Override
    public void notifyGoalObservers() {
        for (GoalObserverChild g: goalObservers) {
            g.update(this);
        }

    }

    @Override
    public void destroy() {
        super.notifyObservers();
        notifyGoalObservers();
    }

}