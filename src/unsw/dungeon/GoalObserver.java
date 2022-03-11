package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public interface GoalObserver {
    void addGoalEntity(Entity e);
    boolean isComplete();
    void setParent(GoalObserverParent parent);
    List<Entity> getGoalEntities();
    int getGoalEntitySize();
    String getGoalString();
    Boolean hasParent();
    void markVoid();
    boolean isVoid();
    
    default public List<GoalObserver> getGoal() {
        List<GoalObserver> retList = new ArrayList<GoalObserver>();
        retList.add(this);
        return retList;
    }
}