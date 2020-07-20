package unsw.dungeon;

public interface GoalObserver {
    void addGoalEntity(Entity e);
    boolean isComplete();
    void setParent(GoalObserverParent parent);
}