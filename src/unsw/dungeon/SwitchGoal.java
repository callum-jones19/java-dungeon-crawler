package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class SwitchGoal implements GoalObserver, GoalObserverChild {

    private List<FloorSwitch> switches = new ArrayList<FloorSwitch>();
    private GoalObserverParent parent;
    private boolean isComplete;
    private boolean isVoid;

    public SwitchGoal() {
        super();
        this.isComplete = false;
        this.isVoid = false;
    }

    @Override
    public void update(Goal g) {
        

        if (g instanceof FloorSwitch) {
            FloorSwitch f = (FloorSwitch) g;
            if (f.isActive()) {
                switches.remove(f);
            } else {
                switches.add(f);
            }

            if (isComplete() && parent != null) {
                this.isComplete = true;
                voidNonEssentialGoals();
                parent.update();
            } else if (isComplete()) {
                this.isComplete = true;
            }
        }


    }

    @Override
    public void addGoalEntity(Entity e) {
        
        if (e instanceof FloorSwitch) {
            switches.add((FloorSwitch) e);
            FloorSwitch f = (FloorSwitch) e;
            f.registerObserver(this);
        }

    }

    @Override
    public boolean isComplete() {
        return switches.isEmpty();
    }

    @Override
    public void setParent(GoalObserverParent parent) {
        this.parent = parent;

    }

    @Override
    public List<Entity> getGoalEntities() {
        List<Entity> retList = new ArrayList<Entity>();
        for (FloorSwitch f: switches) {
            retList.add((Entity) f);
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
}