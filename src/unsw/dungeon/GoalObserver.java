package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public interface GoalObserver {
    void addGoalEntity(Entity e);
    boolean isComplete();
    void setParent(GoalObserverParent parent);
    List<Entity> getGoalEntities();
    
    default public List<GoalObserver> getGoal() {
        List<GoalObserver> retList = new ArrayList<GoalObserver>();
        retList.add(this);
        return retList;
    }
}