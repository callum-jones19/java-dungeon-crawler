package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class EnemyGoal implements GoalObserver, GoalObserverChild {

    private List<Enemy> enemies = new ArrayList<Enemy>();
    private GoalObserverParent parent;
    private boolean isComplete;
    private boolean isVoid;

    public EnemyGoal() {
        super();
        this.isComplete = false;
        this.isVoid = false;
    }


    @Override
    public void update(Goal g) {
        
        if (g instanceof Enemy) {
            this.enemies.remove((Enemy) g);
            if (enemies.isEmpty() && parent != null) {
                this.isComplete = true;
                parent.update();
                voidNonEssentialGoals();
            } else if (enemies.isEmpty()) {
                this.isComplete = true;
            }
        }

    }

    @Override
    public void addGoalEntity(Entity e) {
        if (e instanceof Enemy) {
            Enemy enemy = (Enemy) e;
            enemies.add(enemy);
            enemy.registerObserver(this);
        }

    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void setParent(GoalObserverParent parent) {
        this.parent = parent;

    }

    @Override
    public List<Entity> getGoalEntities() {
        List<Entity> retList = new ArrayList<Entity>();
        for (Enemy e: enemies) {
            retList.add((Entity) e);
        }

        return retList;
    }

    @Override
    public void voidNonEssentialGoals() {
        if (this.parent == null) return;

        if (!(this.parent.isCompulsoryConjunction())) {
            this.parent.voidOtherGoals(this);
        }

    }

    @Override
    public Boolean hasParent() {
        return this.parent == null;
    }

    @Override
    public boolean isVoid() {
        return this.isVoid;
    }

    @Override 
    public void markVoid() {
        this.isVoid = true;
    }

    @Override
    public String getGoalString() {
        return "Slay all enemies";
    }

    @Override
    public int getGoalEntitySize() {
        return getGoalEntities().size();
    }
    
}