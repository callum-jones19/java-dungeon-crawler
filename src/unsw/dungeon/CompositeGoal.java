package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class CompositeGoal implements GoalObserver, GoalObserverParent {
    
    private List<GoalObserver> childGoals = new ArrayList<GoalObserver>();
    private boolean isComplete;
    private boolean isCompulsoryConjunction;
    private boolean isVoid;
    private GoalObserverParent parent;

    public CompositeGoal(Boolean isCompulsoryConjunction) {
        super();
        this.isComplete = false;
        this.isVoid = false;
        this.isCompulsoryConjunction = isCompulsoryConjunction;
    }

    @Override
    public boolean isVoid() {
        return this.isVoid;
    }

    @Override 
    public void markVoid() {
        this.isVoid = true;
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

    @Override
    public List<Entity> getGoalEntities() {
        List<Entity> retList = new ArrayList<Entity>();

        for (GoalObserver child: childGoals) {
            for (Entity e: child.getGoalEntities()) {
                retList.add(e);
            }
        }

        System.out.println(retList.size());
        return retList;
    }

    @Override
    public List<GoalObserver> getGoal() {
        List<GoalObserver> retList = new ArrayList<GoalObserver>();
        for (GoalObserver child: childGoals) {
            retList.addAll(child.getGoal());
        }

        return retList;
    }

    @Override
    public Boolean hasParent() {
        return this.parent == null;
    }

    @Override
    public void voidOtherGoals(GoalObserver child) {

        if (isCompulsoryConjunction) return;

        for (GoalObserver childGoal: childGoals) {
            if (!childGoal.equals(child) && !childGoal.isComplete()) {
                childGoal.markVoid();
            }
        }
    }

    @Override
    public String getGoalString() {
        String retString = "";
        for (int i = 0; i < childGoals.size(); i++) {
            retString += childGoals.get(i).getGoalString();
            if (i + 1 == childGoals.size()) break;
            retString += (isCompulsoryConjunction() ? " AND " : " OR ");
        }

        return retString;
    }


}