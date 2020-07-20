package unsw.dungeon;

import java.util.List;

public interface GoalObserver {
    void addGoalEntity(Entity e);
    boolean isComplete();
    public List<Object> getSubjects();
}