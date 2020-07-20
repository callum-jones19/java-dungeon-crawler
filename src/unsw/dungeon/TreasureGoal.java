package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class TreasureGoal implements GoalObserver, GoalObserverChild {

    private List<Treasure> treasure = new ArrayList<Treasure>();
    private GoalObserverParent parent;
    private boolean isComplete;

    public TreasureGoal() {
        super();
        this.isComplete = false;
    }

    @Override
    public void update(Goal g) {
        
        if (g instanceof Treasure) {
            treasure.remove((Treasure) g);
            if (isComplete() && parent != null) {
                parent.update();
            } else if (treasure.isEmpty()) {
                this.isComplete = true;
            }
        }

    }

    @Override
    public void addGoalEntity(Entity e) {
        if (e instanceof Treasure) {
            this.treasure.add((Treasure) e);
            Treasure t = (Treasure) e;
            t.registerObserver(this);
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