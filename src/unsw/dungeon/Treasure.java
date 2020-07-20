package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Treasure extends Entity implements Item, Goal {

    private CollisionBehaviour collectionStrategy;
    private List<GoalObserver> goalObservers = new ArrayList<GoalObserver>();

    public Treasure(int x, int y) {
        super(x, y);
        this.collectionStrategy = new CollectCollision(this);
        super.setCollisionBehaviour(collectionStrategy);
    }


    public boolean canUseAgain() {
        return false;
    }


    public boolean isUnique() {
        return false;
    }


    public boolean checkItemType(Item i) {
        return (i instanceof Treasure);
    }


    @Override
    public void registerObserver(GoalObserver obs) {
        this.goalObservers.add(obs);

    }

    @Override
    public void removeObserver(GoalObserver obs) {
        this.goalObservers.remove(obs);

    }

    public void pickup(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;
            p.addToInventory(this);
            if (p.isHoldingInstance(this)) {
                notifyGoalObservers();
                destroy();
            }
               
        }
    }

    public void notifyGoalObservers() {
        for (GoalObserver g: goalObservers) {
            GoalObserverChild gChild = (GoalObserverChild) g;
            gChild.update(this);
        }
    }
    
}