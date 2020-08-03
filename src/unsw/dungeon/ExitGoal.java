package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class ExitGoal implements GoalObserver, GoalObserverChild {

    private List<Exit> exits = new ArrayList<Exit>();
    private GoalObserverParent parent;
    private boolean isComplete;
    private boolean isVoid;

    public ExitGoal() {
        super();
        this.isComplete = false;
        this.isVoid = false;
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
                if (hasParent()) {
                    voidNonEssentialGoals();
                }
            }
        } else {
            exits.remove((Exit) g);
            isComplete = true;
            if (hasParent()) {
                voidNonEssentialGoals();
            }
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
    public List<Entity> getGoalEntities() {
        List<Entity> retList = new ArrayList<Entity>();
        for (Exit e: exits) {
            retList.add((Entity) e);
        }

        return retList;
    }

    @Override
    public void voidNonEssentialGoals() {
        if (this.parent == null) return;

        if (!(this.parent.isCompulsoryConjunction())) {
            this.parent.voidOtherGoals(this);
        }

    }

    @Override
    public Boolean hasParent() {
        return this.parent == null;
    }

    @Override
    public boolean isVoid() {
        return this.isVoid;
    }

    @Override 
    public void markVoid() {
        this.isVoid = true;
    }

    @Override
    public String getGoalString() {
        return "Reach an exit";
    }

    @Override
    public int getGoalEntitySize() {
        return getGoalEntities().size();
    }

    
}