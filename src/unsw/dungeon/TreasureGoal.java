package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class TreasureGoal implements GoalObserver, GoalObserverChild {

    private List<Treasure> treasure = new ArrayList<Treasure>();
    private GoalObserverParent parent;
    private boolean isComplete;
    private boolean isVoid;

    public TreasureGoal() {
        super();
        this.isComplete = false;
        this.isVoid = false;
    }

    @Override
    public void update(Goal g) {
        
        if (g instanceof Treasure) {
            treasure.remove((Treasure) g);
            if (treasure.isEmpty() && parent != null) {
                this.isComplete = true;
                parent.update();
                voidNonEssentialGoals();
            } else if (treasure.isEmpty()) {
                this.isComplete = true;
            }
        }

    }

    @Override
    public void addGoalEntity(Entity e) {
        if (e instanceof Treasure) {
            this.treasure.add((Treasure) e);
            Treasure t = (Treasure) e;
            t.registerObserver(this);
        }

    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void setParent(GoalObserverParent parent) {
        this.parent = parent;
    }

    @Override
    public List<Entity> getGoalEntities() {
        List<Entity> retList = new ArrayList<Entity>();
        for (Treasure t: treasure) {
            retList.add((Entity) t);
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