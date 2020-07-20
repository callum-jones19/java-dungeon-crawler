package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class CompositeGoal implements GoalObserver, GoalObserverParent {
    
    private List<GoalObserver> childGoals = new ArrayList<GoalObserver>();
    private boolean isComplete;
    private boolean isCompulsoryConjunction;

    public CompositeGoal(Boolean isCompulsoryConjunction) {
        super();
        this.isComplete = false;
        this.isCompulsoryConjunction = isCompulsoryConjunction;
    }

    public void update() {
        
        if (isComplete()) {
            this.isComplete = true;
        }

    }

    @Override
    public void addGoalEntity(Entity e) {
        for (GoalObserver child: childGoals) {
            child.addGoalEntity(e);
        }
    }

    @Override
    public boolean isComplete() {
        for (GoalObserver goal: childGoals) {
            if (!goal.isComplete() && isCompulsoryConjunction) {
                return false;
            } else if (goal.isComplete() && !isCompulsoryConjunction) {
                return true;
            }
        }
        return true;
    }

    @Override
    public List<Object> getSubjects() {
        return null;
    }

    public void addChildGoal(GoalObserver obs) {
        childGoals.add(obs);
    }

}