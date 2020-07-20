package unsw.dungeon;

// This is plugged into a triggercollision - it tells that collision what type
// it should be triggered by. If there is a type, only that entity colliding will
// make something happen
public interface TriggerType {
    boolean performTriggerCheck(Entity e);
}