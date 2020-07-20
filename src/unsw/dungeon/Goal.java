package unsw.dungeon;

public interface Goal {

    public void registerObserver(GoalObserver obs);

    public void removeObserver(GoalObserver obs);

    public void notifyGoalObservers();
}