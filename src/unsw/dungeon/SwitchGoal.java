package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class SwitchGoal implements GoalObserver, GoalObserverChild {

    private List<FloorSwitch> switches = new ArrayList<FloorSwitch>();
    private GoalObserverParent parent;
    private boolean isComplete;

    public SwitchGoal() {
        super();
        this.isComplete = false;
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

    public List<Object> getSubjects() {
        List<Object> retList = new ArrayList<Object>();
        for (FloorSwitch f: switches) {
            retList.add((Object) f);
        }

        return retList;
    }
    
}