package unsw.dungeon;

public class TriggerTypePlayer implements TriggerType {

    @Override
    public boolean performTriggerCheck(Entity e) {
        return (e instanceof Player);
    }
    
}