package unsw.dungeon;

public interface Goal {

    public void markComplete();

    public void markIncomplete();

    public boolean isCompleted();

    public void registerObserver(GoalObserver obs);

    public void removeObserver(GoalObserver obs);

    public void notifyObservers();
}