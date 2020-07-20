package unsw.dungeon;

public class TriggerTypeBoulder implements TriggerType {
    
    public TriggerTypeBoulder() {
        super();
    }

    public boolean performTriggerCheck(Entity e) {
        return (e instanceof Boulder);
    }

}