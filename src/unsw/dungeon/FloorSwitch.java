package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class FloorSwitch extends Entity implements BoulderObserver, Goal {
    
    private CollisionBehaviour c = new NoCollision();
    private List<GoalObserver> goalObservers = new ArrayList<GoalObserver>();
    private boolean isActive;

    public FloorSwitch(int x, int y) {
        super(x, y, ZLayer.FLOOR);
        super.setCollisionBehaviour(c);
        this.isActive = false;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(Boolean active) {
        this.isActive = active;
    }

    @Override
    public void update(BoulderSubject b) {
        
        if (b.getX() == getX() && b.getY() == getY()) {
            updateSwitchStatus();
        } else if (b.getLastX() == getX() && b.getLastY() == getY()) {
            updateSwitchStatus();
        }

    }

    public void updateSwitchStatus() {
        if (!(isActive())) {
            setActive(true);
        } else {
            setActive(false);
        }
        notifyGoalObservers();
    }

    @Override
    public void registerObserver(GoalObserver obs) {
        this.goalObservers.add(obs);

    }

    @Override
    public void removeObserver(GoalObserver obs) {
        this.goalObservers.remove(obs);
    }

    @Override
    public void notifyGoalObservers() {
        for (GoalObserver g: goalObservers) {
            if (g instanceof GoalObserverChild) {
                GoalObserverChild gChild = (GoalObserverChild) g;
                gChild.update(this);
            }
            
        }
    }


}