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
    public void removeChildGoal(GoalObserver g) {
        if (childGoals.contains(g)) childGoals.remove(g);
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
        return isCompulsoryConjunction;
    }

    @Override
    public List<Object> getSubjects() {
        List<Object> retList = new ArrayList<Object>();
        for (GoalObserver g: childGoals) {
            retList.add((Object) g);
        }

        return retList;
    }

    public void addChildGoal(GoalObserver obs) {
        childGoals.add(obs);
    }

    @Override
    public boolean checkRemainingGoals() {
        
        for (GoalObserver g: childGoals) {
            if (!g.isComplete()) {
                return true;
            }
        }

        return false;
    }

}