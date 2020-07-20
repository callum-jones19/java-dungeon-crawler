package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class ExitGoal implements GoalObserver, GoalObserverChild {

    private List<Exit> exits = new ArrayList<Exit>();
    private GoalObserverParent parent;
    private boolean isComplete;

    public ExitGoal() {
        super();
        this.isComplete = false;
    }

    @Override
    public void update(Goal g) {
        
        exits.remove((Exit) g);
        if (parent != null) {
            if (parent.checkRemainingGoals()) {
                isComplete = false;
            } else {
                if (exits.isEmpty()) isComplete = true;
            }
        } else {
            if (exits.isEmpty()) isComplete = true;
        }

    }

    @Override
    public void setParent(GoalObserverParent parent) {
        this.parent = parent;

    }

    @Override
    public void addGoalEntity(Entity e) {
        if (e instanceof Exit) {
            Exit exit = (Exit) e;
            exits.add(exit);
            exit.registerObserver(this);
        }
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public List<Object> getSubjects() {
        List<Object> retList = new ArrayList<Object>();
        for (Exit e: exits) {
            retList.add((Object) e);
        }

        return retList;
    }

    
}