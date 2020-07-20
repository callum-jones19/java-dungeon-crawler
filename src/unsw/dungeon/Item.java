package unsw.dungeon;

public interface Item {
    boolean canUseAgain();
    boolean isUnique();
    boolean checkItemType(Item i);
    void pickup(Entity e);
}