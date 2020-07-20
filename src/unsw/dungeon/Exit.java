package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Exit extends Entity implements Triggerable, Goal {
    
    private TriggerCollision trigStrat;
    private boolean isActive;
    private List<GoalObserver> goalObservers = new ArrayList<GoalObserver>();

    public Exit(int x, int y) {
        super(x, y);
        this.trigStrat = new TriggerCollision(this, new TriggerTypePlayer());
        super.setCollisionBehaviour(trigStrat);
        isActive = false;
    }

    public void trigger() {
        isActive = true;
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
        System.out.println("Notifying goal observers...");
        for (GoalObserver goal: goalObservers) {
            if (goal instanceof GoalObserverChild) {
                GoalObserverChild goalChild = (GoalObserverChild) goal;
                goalChild.update(this);
            }
        }

    }


}