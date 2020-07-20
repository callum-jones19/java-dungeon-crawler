package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class CompositeGoal implements GoalObserver, GoalObserverParent {
    
    private List<GoalObserver> childGoals = new ArrayList<GoalObserver>();
    private boolean isComplete;
    private boolean isCompulsoryConjunction;
    private GoalObserverParent parent;

    public CompositeGoal(Boolean isCompulsoryConjunction) {
        super();
        this.isComplete = false;
        this.isCompulsoryConjunction = isCompulsoryConjunction;
    }

    public void update() {
        
        if (parent == null) {
            if (isComplete()) {
                this.isComplete = true;
            }
        } else {
            if (isComplete()) {
                this.isComplete = true;
                parent.update();
            }
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


    public void addChildGoal(GoalObserver obs) {
        childGoals.add(obs);
        obs.setParent(this);
    }

    @Override
    public boolean checkRemainingGoals() {
        
        int count = 0;
        for (GoalObserver g: childGoals) {
            if (!g.isComplete()) {
                count++;
            }
        }

        return count > 1 ? true: false;
    }

	@Override
	public void setParent(GoalObserverParent parent) {
        this.parent = parent;
    }
    
    @Override
    public boolean isCompulsoryConjunction() {
        return isCompulsoryConjunction;
    }


}