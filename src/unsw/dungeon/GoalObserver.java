package unsw.dungeon;

import java.util.List;

public interface GoalObserver {
    void addGoalEntity(Entity e);
    boolean isComplete();
    void setParent(GoalObserverParent parent);
}