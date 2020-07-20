package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Treasure extends Entity implements Item, Goal {

    private CollisionBehaviour collectionStrategy;
    List<GoalObserverChild> goalObservers = new ArrayList<GoalObserverChild>();

    public Treasure(int x, int y) {
        super(x, y);
        this.collectionStrategy = new CollectCollision(this);
        super.setCollisionBehaviour(collectionStrategy);
    }

    public void use(Entity e) {
        // FIXME
    }


    public boolean checkCanUse() {
        return false;
    }


    public boolean isWeapon() {
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
        this.goalObservers.add((GoalObserverChild) obs);

    }

    @Override
    public void removeObserver(GoalObserver obs) {
        this.goalObservers.remove((GoalObserverChild) obs);

    }

    public void pickup(Entity e) {
        if (e instanceof Player) {
            System.out.println("We here my guys");
            Player p = (Player) e;
            p.pickup(this);
            if (p.exactContains(this)) {
                notifyGoalObservers();
                destroy();
            }
               
        }
    }

    public void notifyGoalObservers() {
        for (GoalObserverChild g: goalObservers) {
            g.update(this);
        }
    }
    
}