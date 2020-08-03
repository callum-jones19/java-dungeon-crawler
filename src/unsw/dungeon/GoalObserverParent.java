package unsw.dungeon;

public interface GoalObserverParent {
    void update();
    void addChildGoal(GoalObserver obs);
    void removeChildGoal(GoalObserver obs);
	boolean checkRemainingGoals();
    boolean isCompulsoryConjunction();
    void voidOtherGoals(GoalObserver child);
}