package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class EnemyGoal implements GoalObserver, GoalObserverChild {

    private List<Enemy> enemies = new ArrayList<Enemy>();
    private GoalObserverParent parent;
    private boolean isComplete;

    public EnemyGoal() {
        super();
        this.isComplete = false;
    }


    @Override
    public void update(Goal g) {
        
        if (g instanceof Enemy) {
            this.enemies.remove((Enemy) g);
            if (isComplete() && parent != null) {
                parent.update();
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

    
}