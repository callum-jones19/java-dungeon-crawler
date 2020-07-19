package unsw.dungeon;

public interface Mediator {
    void notify(Entity subject, Entity object);
}