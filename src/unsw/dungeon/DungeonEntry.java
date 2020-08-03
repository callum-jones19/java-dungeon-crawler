package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class DungeonEntry extends Entity implements Triggerable, EntrySubject {

    private String targetDungeonFile;
    private TriggerCollision triggerStrat;

    private List<EntryObserver> observers;

    public DungeonEntry(int x, int y, String targetDungeonFile) {
        super(x, y, ZLayer.FLOOR);
        this.targetDungeonFile = targetDungeonFile;
        this.triggerStrat = new TriggerCollision(this, new TriggerTypePlayer());
        super.setCollisionBehaviour(triggerStrat);
        this.observers = new ArrayList<EntryObserver>();
    }

    @Override
    public void trigger() {
        notifyEntryObservers();
    }

    @Override
    public void addEntryObserver(EntryObserver e) {
        if (! this.observers.contains(e)) {
            this.observers.add(e);
        }
    }

    @Override
    public void removeEntryObserver(EntryObserver e) {
        this.observers.remove(e);
    }

    @Override
    public void notifyEntryObservers() {
        for (EntryObserver o : observers) {
            o.updateEntry(this);
        }
    }
    
    public String getTargetFile() {
        return this.targetDungeonFile;
    }

    public String getTitleUpper() {
        String tmp = this.targetDungeonFile;
        tmp.replace('_', ' ');
        return tmp.split("\\.")[0].toUpperCase();
    }
}