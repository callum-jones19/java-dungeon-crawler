package unsw.dungeon;

public class Exit extends Entity implements Triggerable, Goal {
    
    private TriggerCollision trigStrat;
    private boolean isActive;
    


    public Exit(int x, int y) {
        super(x, y);
        trigStrat = new TriggerCollision(this, new TriggerTypePlayer());
        isActive = false;
    }

    public void trigger() {
        isActive = true;
    }

    @Override
    public void registerObserver(GoalObserver obs) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeObserver(GoalObserver obs) {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyGoalObservers() {
        // TODO Auto-generated method stub

    }


}