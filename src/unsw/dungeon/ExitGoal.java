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
        System.out.println("Exit Goal observer receiving updates...");
        if (parent != null) {
            System.out.println(parent.checkRemainingGoals());
            if (parent.checkRemainingGoals() && parent.isCompulsoryConjunction()) {
                System.out.println("We here");
                isComplete = false;
            } else {
                exits.remove((Exit) g);
                isComplete = true;
            }
        } else {
            exits.remove((Exit) g);
            isComplete = true;
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

    
}